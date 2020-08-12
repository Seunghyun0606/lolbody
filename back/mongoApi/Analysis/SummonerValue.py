import math, os, sys, json
import numpy as np
import pandas as pd
from scipy.stats import norm

# sys.path.append(os.path.abspath(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))

def change_to_p_value(z):
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std


def get_badges(player_p_value):
    badge_items = {
        'totalDamageDealtToChampionsPerMin': '폭행범',
        'visionScorePerMin': '옵저버',
        'totalDamageTakenPerMin': '샌드백',
        'totalMinionsKilledPerMin': '농부',
        'killsRatio': '오지라퍼',
        'deathsRatio': '민폐',
        'killAssistPerMin': '분노조절장애',
    }
    badges = []
    for k, v in player_p_value.items():
        if v >= 0.85:
            badges.append({
                'name': badge_items.get(k),
                'p_value' : v,
                })
    return badges


def get_player_lane_value(player_data):
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
    tier = player_data.get('tier')
    ret = {
        'lane1' : {
            'positon': None,
            'aggressiveness': 0,
            'stability': 0,
            'influence': 0
        },
        'lane2' : {
            'positon': None,
            'aggressiveness': 0,
            'stability': 0,
            'influence': 0
        },
        'badges': [],
    }
    for l_name, lane in player_data.items():
        if type(lane) != type(['list']): continue
        if not len(lane): continue
        position = lane[0].get('position')  ## TOP, JUNGLE...
      
        player_df = pd.DataFrame(columns=cols)
        for l in lane:
            tmp_data = {
                'visionScorePerMin': l.get('visionScore') / (l.get('gameDuration') / 60),
                'totalMinionsKilledPerMin': l.get('csPerMin'),
                'deathsRatio': l.get('deaths') / l.get('teamDeaths'),

                'killAssistPerMin': (l.get('kills') + l.get('assist')) / (l.get('gameDuration') / 60),
                'totalDamageDealtToChampionsPerMin': l.get('totalDamageDealtToChampions') / (l.get('gameDuration') / 60),
                'totalDamageTakenPerMin': l.get('totalDamageTaken') / (l.get('gameDuration') / 60),

                'killsRatio': l.get('kills') /l.get('teamKills')
            }
            player_df = player_df.append(tmp_data, ignore_index=True)

        # print(player_df)
        player_mean = player_df.mean()
        # player_std = player_df.std()

        stats = pd.read_csv('./Analysis/csv/%s/stastics/stastics.csv' % now)
        tier_lane_stats = stats[(stats['position'] == position) & (stats['tier'] == tier)].reset_index(drop=True)
        # print(tier_lane_stats)
        player_p_value = dict()
        for col in cols:
            mean = col + 'Mean'
            std = col + 'Std'
            player_p_value[col] = change_to_p_value(z_value(player_mean[col], tier_lane_stats[mean][0], tier_lane_stats[std][0]))
        # print(player_p_value)
        lane_data = {
            'positon': position,
            'aggressiveness': (player_p_value.get('killAssistPerMin') + player_p_value.get('totalDamageDealtToChampionsPerMin') + player_p_value.get('totalDamageTakenPerMin')) / 3,
            'stability': (player_p_value.get('visionScorePerMin') + player_p_value.get('totalMinionsKilledPerMin') + (1 - player_p_value.get('deathsRatio'))) / 3,
            'influence': (player_p_value.get('visionScorePerMin') + player_p_value.get('killsRatio')) / 2,
        }
        ret[l_name] = lane_data
        ret['badges'] = get_badges(player_p_value)
    return ret

if __name__ == '__main__':
    now = '20.08'
    player_data = json.loads(sys.argv[1].replace("'", '"'))
    print(player_data)
    print(get_player_lane_value(player_data))