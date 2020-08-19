import math, os, sys, json
import numpy as np
import pandas as pd
from scipy.stats import norm

# sys.path.append(os.path.abspath(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))

def change_to_p_value(z):
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std


def get_player_match_grade(player_data):
    data = {
        'visionScorePerMin' : player_data.get('visionScore') / (player_data.get('duration') / 60),
        'totalMinionsKilledPerMin' : player_data.get('csPerMin'),
        'deathsRatio' : player_data.get('deathRatio') / 100,
        'killAssistPerMin' : (player_data.get('kill') + player_data.get('assist')) / (player_data.get('duration') / 60)\
            if player_data.get('kill') + player_data.get('assist') !=0 else 0,
        'totalDamageDealtToChampionsPerMin' : player_data.get('damageDealt') / (player_data.get('duration') / 60),
        'totalDamageTakenPerMin' : player_data.get('damageTaken') / (player_data.get('duration') / 60),
        'killsRatio' : player_data.get('killRatio') / 100,
        'deathsRatio': player_data.get('deathRatio') /100,
        # kill : player_data.get('kill')
        # assist : player_data.get('assist')
        # death : player_data.get('death')
        # duration : player_data.get('duration')
        # kda : player_data.get('kda') if player_data.get('kda') >=0 else float('inf')
        # gold : player_data.get('gold')
        # cs : player_data.get('cs')
    }
    tier = player_data.get('tier') if player_data.get('tier') != 'null' else 'iron'
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
    # cols2 = ['visionScore', 'csPerMin', 'damageDealtPerMin', 'damageTakenPerMin']
    print(data)

    stats = pd.read_csv('./csv/20.08/stastics/stastics.csv')

    # 포지션 유사도 판별로 정해볼 예정
    # 데이터 정규화 후에 유클리디안 거리를 구한것 == 마할라노비스 거리를 구한 것
    # 정규화 => 평균 0, 분산 1을 갖는 표준정규분포로 전환한 것이기 때문에
    min_distance = float('inf')
    tmp_position = ''
    player_p_value = dict()
    for position in ['TOP', 'JUNGLE', 'MID', 'BOTTOM', 'SUPPORT']:
        tier_lane_stats = stats[(stats['position'] == position) & (stats['tier'] == tier)].reset_index(drop=True)

        tmp_distance = 0
        tmp_player_p_value = dict()
        for col in cols:
            mean = col + 'Mean'
            std = col + 'Std'
            tmp_player_p_value[col] = change_to_p_value(z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
            player_z_value = z_value(data[col], tier_lane_stats[mean][0], tier_lane_stats[std][0])
            if col == 'deathsRatio':
                player_z_value = 1 - player_z_value
            tmp_distance += player_z_value**2
        # print(tmp_player_p_value)
        tmp_distance = tmp_distance**0.5
        # print(position, tmp_distance)
        if tmp_distance < min_distance:
            min_distance = tmp_distance
            tmp_position = position
            player_p_value = tmp_player_p_value
    #     print(position, tmp_distance)
    #     print()
    # print(tmp_position)

    total = 0
    for k, v in player_p_value.items():
        if k == 'deathsRatio':
            total += (1 - v)
        else:
            total += v
    return total/7

if __name__ == '__main__':
    # player_data = json.loads(sys.argv[1].replace("'", '"'))
    # print(get_player_match_grade(player_data))

    # d = "{'death':11,'kda':0.7272727272727273,'deathRatio':32.35294117647059,'kill':8,'csPerMin':3.3412322274881516,'duration':1688,'gold':9846,'cs':94,'tier':'BRONZE','assist':0,'killRatio':53.333333333333336,'damageDealt':12712,'damageTaken':25843,'visionScore':15}"
    d = "{'death':5,'visionScore':28,'kda':5.4,'deathRatio':16.666666666666668,'kill':1,'csPerMin':0.976491862567812,'duration':1659,'gold':8030,'cs':27,'tier':'SILVER','assist':26,'killRatio':77.14285714285714,'damageDealt':9923,'damageTaken':16545}"
    player_data = json.loads(d.replace("'", '"'))
    print(get_player_match_grade(player_data))