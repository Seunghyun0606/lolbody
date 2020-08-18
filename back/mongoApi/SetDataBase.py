import pymongo, sys, time, os
s = time.time()
import pandas as pd
from scipy.stats import norm
# from multiprocessing import Pool
s2 = time.time()

connection = pymongo.MongoClient('mongodb://lolbody:fhfqkelchlrhdi3%232%401!@13.125.220.135:27017/test?authSource=test&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false')

now = '20.08'

spells = [1, 11, 12, 13, 14, 21, 3, 30, 31, 32, 39, 4, 6, 7]
save_stats_list = [
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
        # "totalUnitsHealed"              # 회복시켜준 유저수
        'totalDamageTakenPerMin',             # 받은 피해량
        "damageDealtToObjectivesPerMin",       # 오브젝트에게 준 피해량
        # 'goldEarned',                   # 총 골드
        'totalMinionsKilledPerMin',           # cs
        'killsRatio',                   # 킬관여율
        'deathsRatio',                  # 데스관여울
        'killAssistPerMin',
    ]

badges_450 = [
        'killsPerMin',                        # kill
        'deathsPerMin',                       # deaths
        'assistsPerMin',                      # assists
        # 'totalHealPerMin',                    # 총 회복량
        'damageSelfMitigatedPerMin',          # 감소시킨 피해량(방어막?)
        "damageDealtToTurretsPerMin",         # 타워에 준 피해량
        'timeCCingOthersPerMin',              # cc기에 맞은 총 시간
        'totalTimeCrowdControlDealtPerMin',   # cc기를 맞춘 총 시간
        ]

badges_420 = [
        'visionScorePerMin',                  # 시야점수
        'visionWardsBoughtInGamePerMin',      # 핑와 구매 개수
        'neutralMinionsKilledPerMin',         # 중립몹 킬수
        'neutralMinionsKilledEnemyJunglePerMin', # 상대 정글몹 킬수
        'wardsPlacedPerMin',                  # 와드 설치수
        'wardsKilledPerMin',                  # 와드 파괴수
    ]

badge_names = {
    'totalDamageDealtToChampionsPerMin': '폭행범',  # 챔피언에게 입힌 피해량
    'totalDamageTakenPerMin': '샌드백',             # 받은 피해량
    "damageDealtToObjectivesPerMin": '몬스터헌터',       # 오브젝트에게 준 피해량
    'totalMinionsKilledPerMin': '농부',           # cs
    'killsRatio': '오지라퍼',                   # 킬관여율
    'deathsRatio': '민폐甲',                  # 데스관여울
    'killAssistPerMin': '쌈닭',
    'killsPerMin': '학살자',                        # kill
    'deathsPerMin': '불사신',                       # deaths
    'assistsPerMin': '수발러',                      # assists
    # 'totalHealPerMin': '어머니',                    # 총 회복량
    'damageSelfMitigatedPerMin': '방어막',          # 감소시킨 피해량(방어막?)
    "damageDealtToTurretsPerMin": '철거반',         # 타워에 준 피해량
    'timeCCingOthersPerMin': '명타겟',              # cc기에 맞은 총 시간
    'totalTimeCrowdControlDealtPerMin': '명사수',   # cc기를 맞춘 총 시간
    'visionScorePerMin': '옵저버',                  # 시야점수
    'visionWardsBoughtInGamePerMin': '취미:비싼꽃꽂이',      # 핑와 구매 개수
    'neutralMinionsKilledPerMin': '사냥꾼',         # 중립몹 킬수
    'neutralMinionsKilledEnemyJunglePerMin': '대도둑', # 상대 정글몹 킬수
    'wardsPlacedPerMin': '취미:꽃꽂이',                  # 와드 설치수
    'wardsKilledPerMin': '나무꾼',     
}

def change_to_p_value(z):
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std



# mongodb에 match_id를 받아서 라인구분, match_grade 저장
db_root = connection.test
def get_stats(queue):
    now = '20.08'
    return pd.read_csv('/csv/%s/stastics/stastics_%s.csv' % (now, queue))
    # return pd.read_csv('./csv/%s/stastics/stastics_%s.csv' % (now, queue))

def update_match_data(profile_id, left, right, tier):
    match_list = list(filter(lambda i: i.get('timestamp') >= 1578596400000 and i.get('queue') != 2000 and i.get('queue') != 2010 and i.get('queue') != 2020, db_root.matchlists.find_one({'_id': profile_id}).get('matches')))[left:right]
    match_id_list = [i.get('gameId') for i in match_list if i.get('queue') == 420 or i.get('queue') == 430 or i.get('queue') == 440 or i.get('queue') == 450]

    for match_id in match_id_list:
        matches_collection = db_root.matches
        match_data = matches_collection.find_one({'_id': match_id})

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
            # 레이더 차트 그리기 위한 플레이어 데이터
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

            badges_450_data = {
                'killsPerMin': player_data.get('kills') / duration,                        # kill
                'deathsPerMin': player_data.get('deaths') / duration,                       # deaths
                'assistsPerMin': player_data.get('assists') / duration,                      # assists
                # 'totalHealPerMin': player_data.get('totalHeal') / duration\
                #     if player_data.get('totalUnitsHealed') > 1 else 0,                    # 총 회복량
                'damageSelfMitigatedPerMin': player_data.get('damageSelfMitigated') / duration,          # 감소시킨 피해량(방어막?)
                "damageDealtToTurretsPerMin": player_data.get('damageDealtToTurrets') / duration,         # 타워에 준 피해량
                'timeCCingOthersPerMin': player_data.get('timeCCingOthers') / duration,              # cc기에 맞은 총 시간
                'neutralMinionsKilledPerMin': player_data.get('neutralMinionsKilled') / duration,         # 중립몹 킬수
                'totalTimeCrowdControlDealtPerMin': player_data.get('totalTimeCrowdControlDealt') / duration,   # cc기를 맞춘 총 시간
                'visionWardsBoughtInGamePerMin': player_data.get('visionWardsBoughtInGame') / duration,      # 핑와 구매 개수
            }
            if queue == 420:
                badges_420_data = {
                    'neutralMinionsKilledEnemyJunglePerMin': player_data.get('neutralMinionsKilledEnemyJungle') / duration, # 상대 정글몹 킬수
                    'wardsPlacedPerMin': player_data.get('wardsPlaced') / duration,                  # 와드 설치수
                    'wardsKilledPerMin': player_data.get('wardsKilled') / duration,                  # 와드 파괴수
                }

            # 라인 판별 로직
            ##################################################################3
            
            data = participant_data
            min_distance = float('inf')
            tmp_position = ''
            player_p_value = dict()
            badges_data = []
            if queue == 420:
                data.update(badges_420_data)
                data.update(badges_450_data)
                cols = save_stats_list + badges_420 + badges_450
                for position in ['TOP', 'JUNGLE', 'MID', 'BOTTOM', 'SUPPORT']:
                    if position == 'JUNGLE' and participant.get('spell1Id') != 11 and participant.get('spell2Id') != 11: continue
                    tier_lane_stats = stats[(stats['position'] == position) & (stats['tier'] == tier)].reset_index(drop=True)
                    # print(list(tier_lane_stats))

                    tmp_distance = 0
                    # 라인별로 데이터 분석 x
                    # tmp_player_p_value = dict()
                    for spell in spells:
                        d = 1 if (participant.get('spell1Id') == spell or participant.get('spell2Id') == spell) else 0
                        tmp_distance += (tier_lane_stats['spell_%s' % spell][0] - d)**2
                    for col in cols:
                        mean = col + 'Mean'
                        std = col + 'Std'
                        # 라인별로 데이터 분석 x
                        # tmp_player_p_value[col] = change_to_p_value(z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
                        player_z_value = z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0])
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
                    if tmp_distance < min_distance:
                        min_distance = tmp_distance
                        tmp_position = position
                        # 라인별로 데이터 분석 x
                        # player_p_value = tmp_player_p_value
                # 여기서 부터 player_p_value 작성(TOTAL기준)
                tmp_player_p_value = dict()
                total_stats = stats[(stats['position'] == 'TOTAL') & (stats['tier'] == tier)].reset_index(drop=True)
                for col in cols:
                    mean = col + 'Mean'
                    std = col + 'Std'
                    p_value = change_to_p_value(z_value(data[col], total_stats[mean][0], total_stats[std][0]))
                    if col == 'deathsRatio' or col == 'timeCCingOthersPerMin' or col == 'deathsPerMin':
                        p_value = 1 - p_value
                    tmp_player_p_value[col] = p_value
                    # 여기는 뱃지 붙이는 곳
                    if p_value >= 0.9:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 0})
                    elif p_value >= 0.8:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 1})
                    elif p_value >= 0.7:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 2})
                player_p_value = tmp_player_p_value
            else:
                data.update(badges_450_data)
                cols = save_stats_list + badges_450
                tier_lane_stats = stats[(stats['tier'] == tier)].reset_index(drop=True)
                tmp_position = 'None'
                tmp_player_p_value = dict()
                for col in cols:
                    mean = col + 'Mean'
                    std = col + 'Std'
                    p_value = change_to_p_value(z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
                    if col == 'deathsRatio' or col == 'timeCCingOthersPerMin' or col == 'deathsPerMin':
                        p_value = 1 - p_value
                    tmp_player_p_value[col] = p_value
                    # 여기는 뱃지 붙이는 곳
                    if p_value >= 0.9:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 0})
                    elif p_value >= 0.8:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 1})
                    elif p_value >= 0.7:
                        badges_data.append({'name': badge_names[col], 'stats': col, 'p_value': p_value, 'tier': 2})
                player_p_value = tmp_player_p_value

            participant['line'] = tmp_position
            participant['analysis'] = player_p_value
            participant['badges'] = badges_data
            #####################################################################33

            # 판별된 라인 기준으로 matchGrade 계산
            #####################################################################333
            total = 0
            if queue == 420:
                # 서포터인경우 cs대신 와드 설치, 삭제로 stability 판별
                if tmp_position == 'SUPPORT':
                    for k in save_stats_list:
                        if k == 'totalDamageTakenPerMin':
                            total += (1 - player_p_value.get(k))
                        else:
                            total += player_p_value.get(k)
                            participant['matchGrade'] = total / 8
                            participant['radar'] = {
                                'aggressiveness': (player_p_value.get('killAssistPerMin') + player_p_value.get('totalDamageDealtToChampionsPerMin') + player_p_value.get('totalDamageTakenPerMin')) / 3,
                                'stability': (player_p_value.get('visionScorePerMin') + ((player_p_value.get('wardsKilledPerMin') + player_p_value.get('wardsPlacedPerMin')) / 2) + player_p_value.get('deathsRatio')) / 3,
                                'influence': (player_p_value.get('visionScorePerMin') + player_p_value.get('killsRatio') + player_p_value.get('damageDealtToObjectivesPerMin')) / 3,
                            }
                else:
                    for k in save_stats_list:
                        if k == 'totalDamageTakenPerMin':
                            total += (1 - player_p_value.get(k))
                        else:
                            total += player_p_value.get(k)
                    participant['matchGrade'] = total / 8
                    participant['radar'] = {
                        'aggressiveness': (player_p_value.get('killAssistPerMin') + player_p_value.get('totalDamageDealtToChampionsPerMin') + player_p_value.get('totalDamageTakenPerMin')) / 3,
                        'stability': (player_p_value.get('visionScorePerMin') + player_p_value.get('totalMinionsKilledPerMin') + player_p_value.get('deathsRatio')) / 3,
                        'influence': (player_p_value.get('visionScorePerMin') + player_p_value.get('killsRatio') + player_p_value.get('damageDealtToObjectivesPerMin')) / 3,
                    }
            else:
                for k in save_stats_list:
                    if k == 'deathsRatio' or k == 'totalDamageTakenPerMin':
                        total += (1 - player_p_value.get(k))
                    elif k == 'visionScorePerMin':
                        total += (1 - player_p_value.get('damageDealtToTurretsPerMin'))
                    else:
                        total += player_p_value.get(k)
                participant['matchGrade'] = total / 8
                participant['radar'] = {
                    'aggressiveness': (player_p_value.get('killAssistPerMin') + player_p_value.get('totalDamageDealtToChampionsPerMin') + player_p_value.get('totalDamageTakenPerMin')) / 3,
                    'stability': (player_p_value.get('damageDealtToTurretsPerMin') + player_p_value.get('totalMinionsKilledPerMin') + player_p_value.get('deathsRatio')) / 3,
                    'influence': (player_p_value.get('damageDealtToTurretsPerMin') + player_p_value.get('killsRatio') + player_p_value.get('damageDealtToObjectivesPerMin')) / 3,
                }
            ######################################################################333
            tmp_collection.update({'_id': match_id}, { '$set': {'participants': participants_data}})
        tmp_collection.update({'_id': match_id}, { '$set': {'flag': True}})
        print(match_id)

if __name__ == '__main__':
    update_match_data(sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])
    print(time.time() - s)
    print(time.time() - s2)