import pymongo, sys, time
# s = time.time()
import pandas as pd
from scipy.stats import norm
# s2 = time.time()

connection = pymongo.MongoClient('mongodb://lolbody:fhfqkelchlrhdi3%232%401!@13.125.220.135:27017/test?authSource=test&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false')

now = '20.08'

cols = [
        # 'kills',                        # kill
        # 'deaths',                       # deaths
        # 'assists',                      # assists
        # "largestKillingSpree": 7,
        # "largestMultiKill": 1,
        # "killingSprees": 1,
        # "longestTimeSpentLiving": 856,
        # "doubleKills": 0,
        # "tripleKills": 0,
        # "quadraKills": 0,
        # "pentaKills": 0,
        # "unrealKills": 0,
        'totalDamageDealtToChampionsPerMin',  # 챔피언에게 입힌 피해량
        # 'totalHeal',                    # 총 회복량
        # "totalUnitsHealed"              # 회복시켜준 유저수
        # 'damageSelfMitigated',          # 감소시킨 피해량(방어막?)
        # "damageDealtToObjectives"       # 오브젝트에게 준 피해량
        # "damageDealtToTurrets",         # 타워에 준 피해량
        'visionScorePerMin',                  # 시야점수
        # 'timeCCingOthers',              # cc기에 맞은 총 시간
        'totalDamageTakenPerMin',             # 받은 피해량
        # 'goldEarned',                   # 총 골드
        'totalMinionsKilledPerMin',           # cs
        # 'neutralMinionsKilled',         # 중립몹 킬수
        # 'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        # 'totalTimeCrowdControlDealt',   # cc기를 맞춘 총 시간
        # 'visionWardsBoughtInGame',      # 핑와 구매 개수
        # 'wardsPlaced',                  # 와드 설치수
        # 'wardsKilled',                  # 와드 파괴수
        'killsRatio',                   # 킬관여율
        'deathsRatio',                  # 데스관여울
        'killAssistPerMin',
    ]
spells = [1, 11, 12, 13, 14, 21, 3, 30, 31, 32, 39, 4, 6, 7]


def change_to_p_value(z):
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std



# mongodb에 match_id를 받아서 라인구분, match_grade 저장
db_root = connection.test
def get_stats(queue):
    return pd.read_csv('./csv/%s/stastics/stastics_%s.csv' % (now, queue))

def update_match_data(profile_id, left, right, tier):
    match_list = list(filter(lambda i: i.get('timestamp') >= 1578596400000 and i.get('queue') != 2000 and i.get('queue') != 2010 and i.get('queue') != 2020, db_root.matchlists.find_one({'_id': profile_id}).get('matches')))[left:right]
    # print(match_list)
    match_id_list = [i.get('gameId') for i in match_list if i.get('queue') == 420 or i.get('queue') == 430 or i.get('queue') == 440 or i.get('queue') == 450]

    for match_id in match_id_list:
        tmp_collection = db_root.tmp
        match_data = tmp_collection.find_one({'_id': match_id})
        if match_data is None: continue

        # 다시하기는 넘어감
        if match_data.get('gameDuration') <= 300: continue

        # 이미 매치데이터 갱신 했으면 넘어감
        if match_data.get('flag'): continue

        # 공통데이터
        # 플레이 시간
        queue = 450 if int(match_data.get('queueId')) == 450 else 420
        stats = get_stats(queue)
        duration = match_data.get('gameDuration') / 60

        # team kill, death, assist
        team = {
            100: {
                'kills': 0,
                'deaths': 0,
                'assists': 0
            },
            200: {
                'kills': 0,
                'deaths': 0,
                'assists': 0
            }
        }

        participants_data = match_data.get('participants')
        for p in range(10):
            team[participants_data[p].get('teamId')]['kills'] += participants_data[p].get('stats').get('kills')
            team[participants_data[p].get('teamId')]['deaths'] += participants_data[p].get('stats').get('deaths')
            team[participants_data[p].get('teamId')]['assists'] += participants_data[p].get('stats').get('assists')

        for participant in participants_data:
            player_data = participant.get('stats')
            participant_data = {
                'visionScorePerMin': player_data.get('visionScore') / duration,
                'totalMinionsKilledPerMin': player_data.get('totalMinionsKilled') / duration,
                'deathsRatio': player_data.get('deaths') / team.get(participant.get('teamId')).get('deaths')\
                    if team.get(participant.get('teamId')).get('deaths') != 0 else 0,
                'killAssistPerMin': (player_data.get('kills') + player_data.get('assists')) / duration\
                    if player_data.get('kills') + player_data.get('assists') != 0 else 0,
                'totalDamageDealtToChampionsPerMin' : player_data.get('totalDamageDealtToChampions') / duration,
                'totalDamageTakenPerMin' : player_data.get('totalDamageTaken') / duration,
                'killsRatio' : (player_data.get('kills') + player_data.get('assists')) / team.get(participant.get('teamId')).get('kills')\
                    if team.get(participant.get('teamId')).get('kills') != 0 else 0,
                'damageDealtToObjectivesPerMin' : player_data.get('damageDealtToObjectives') / duration,
            }
            # 라인 판별 로직
            ##################################################################3
            data = participant_data
            min_distance = float('inf')
            tmp_position = ''
            player_p_value = dict()
            if queue == 420:
                for position in ['TOP', 'JUNGLE', 'MID', 'BOTTOM', 'SUPPORT']:
                    if position == 'JUNGLE' and participant.get('spell1Id') != 11 and participant.get('spell2Id') != 11: continue
                    tier_lane_stats = stats[(stats['position'] == position) & (stats['tier'] == tier)].reset_index(drop=True)
                    # print(list(tier_lane_stats))

                    tmp_distance = 0
                    tmp_player_p_value = dict()
                    for spell in spells:
                        d = 1 if (participant.get('spell1Id') == spell or participant.get('spell2Id') == spell) else 0
                        # print(spell, tier_lane_stats['spell_%s' % spell][0], d)
                        tmp_distance += (tier_lane_stats['spell_%s' % spell][0] - d)**2
                    for col in cols:
                        mean = col + 'Mean'
                        std = col + 'Std'
                        # print(col)
                        tmp_player_p_value[col] = change_to_p_value(z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
                        player_z_value = z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0])
                        if col == 'deathsRatio':
                            player_z_value = 1 - player_z_value
                        if col == 'totalMinionsKilledPerMin':
                            player_z_value *= 1.5
                        tmp_distance += player_z_value**2
                    for t in [('xcor_2', 'x'), ('ycor_2', 'y')]:
                        col, p = t
                        mean = col + 'Mean'
                        std = col + 'Std'
                        d = z_value(participant.get(p), tier_lane_stats[mean][0], tier_lane_stats[std][0])
                        # print(d)
                        # 게임 시작 2분후 위치에는 가중치 부여
                        tmp_distance += d**2 * 2
                    tmp_distance = tmp_distance**0.5
                    # print(position, tmp_distance)
                    if tmp_distance < min_distance:
                        min_distance = tmp_distance
                        tmp_position = position
                        player_p_value = tmp_player_p_value
                # print(tmp_position)
                participant['line'] = tmp_position
                participant['analysis'] = player_p_value
            else:
                tier_lane_stats = stats[(stats['tier'] == tier)].reset_index(drop=True)
                tmp_player_p_value = dict()
                for col in cols:
                    mean = col + 'Mean'
                    std = col + 'Std'
                    tmp_player_p_value[col] = change_to_p_value(z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
                player_p_value = tmp_player_p_value
            #####################################################################33

            # 판별된 라인 기준으로 matchGrade 계산
            #####################################################################333
            total = 0
            for k, v in player_p_value.items():
                if k == 'deathsRatio' or k == 'totalDamageTakenPerMin':
                    total += (1 - v)
                else:
                    total += v
                # print(k, v)
            participant['matchGrade'] = total / 8
            # print(participant['matchGrade'], participant['lane'])
            ######################################################################333
            # print(participants_data)
        #     tmp_collection.update({'_id': match_id}, { '$set': {'participants': participants_data}})
        # tmp_collection.update({'_id': match_id}, { '$set': {'flag': True}})
        # print(participants_data)
        

if __name__ == '__main__':
    update_match_data(sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])
    # print(time.time() - s)
    # print(time.time() - s2)