import math, os, sys
import numpy as np
import pandas as pd
from scipy.stats import norm

# sys.path.append(os.path.abspath(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))


# 소환사 아이디 넣으면 주라인 2가지의 각 라인별 정보를 받아오는 API
player_data = {
    'rank': 'diamond',
    'positions': {
        'lane1': 'top',
        'lane2': 'jungle'
        },
    'lane1': { 
        0: { 
            'visionScore': 0,
            'csPerMin': 0,
            'kills': 0,
            'deaths': 0,
            'assist': 0,
            'gameDuration': 0,
            'totalDamageDealtToChampions': 0,
            'totalDamageTaken': 0,
            },
        1: { 
            'visionScore': 1,
            'csPerMin': 1,
            'kills': 1,
            'deaths': 1,
            'assist': 1,
            'gameDuration': 1,
            'totalDamageDealtToChampions': 1,
            'totalDamageTaken': 1,
            },
        2: { 
            'visionScore': 2,
            'csPerMin': 2,
            'kills': 2,
            'deaths': 2,
            'assist': 2,
            'gameDuration': 2,
            'totalDamageDealtToChampions': 2,
            'totalDamageTaken': 2,
            },
        3: { 
            'visionScore': 3,
            'csPerMin': 3,
            'kills': 3,
            'deaths': 3,
            'assist': 3,
            'gameDuration': 3,
            'totalDamageDealtToChampions': 3,
            'totalDamageTaken': 3,
            },
    },
    'lane2': {
        0: { 
            'visionScore': 0,
            'csPerMin': 0,
            'kills': 0,
            'deaths': 0,
            'assist': 0,
            'gameDuration': 0,
            'totalDamageDealtToChampions': 0,
            'totalDamageTaken': 0,
            },
        1: { 
            'visionScore': 1,
            'csPerMin': 1,
            'kills': 1,
            'deaths': 1,
            'assist': 1,
            'gameDuration': 1,
            'totalDamageDealtToChampions': 1,
            'totalDamageTaken': 1,
            },
        2: { 
            'visionScore': 2,
            'csPerMin': 2,
            'kills': 2,
            'deaths': 2,
            'assist': 2,
            'gameDuration': 2,
            'totalDamageDealtToChampions': 2,
            'totalDamageTaken': 2,
            },
        3: { 
            'visionScore': 3,
            'csPerMin': 3,
            'kills': 3,
            'deaths': 3,
            'assist': 3,
            'gameDuration': 3,
            'totalDamageDealtToChampions': 3,
            'totalDamageTaken': 3,
            },
    }
}


def change_to_p_value(z):
    # ret = ( 1 / (2 * math.pi)**0.5 ) * math.exp(-0.5*z**2)
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std


def get_player_lane_value(player_data):
    ret = {
        'positions': player_data.get('positions'),
        'lane1': {
            '공격성': 0,
            '안정성': 0,
            '영향력': 0,
        },
        'lane2': {
            '공격성': 0,
            '안정성': 0,
            '영향력': 0,
        }
    }
    rank = player_data.get('rank')
    for lane, position in player_data.get('positions').items():
        # lane = 'lane1', 'lane2'
        # position = 'top', 'jungle'

        data = pd.DataFrame(player_data.get(lane)).T

        using_data = pd.DataFrame()
        # 안정성
        using_data['visionScore'] = data['visionScore']
        using_data['csPerMin'] = data['totalMinionsKilled'] / (data['playtime'] / 60)
        ## 낮을수록 좋기때문에 음수로 바꿔서 사용해야함
        using_data['deathsRatio'] = data['deathsRatio']

        # 공격성
        using_data['killAssistPerMin'] = (data['kills'] + data['assists']) / (data['playtime'] / 60)
        using_data['damageDealtPerMin'] = data['totalDamageDealtToChampions'] / (data['playtime'] / 60)
        using_data['damageTakenPerMin'] = data['totalDamageTaken'] / (data['playtime'] / 60)

        # 영향력
        using_data['killsRatio'] = data['killsRatio']

        print(player_df)
        ret[lane]['공격성'] = 0

        stats = pd.read_csv('./csv/2008/%s/2008_stastics.csv' % rank)    
        lane_stats = stats[stats['position'] == lane].reset_index(drop=True)

# print(player_data)
get_player_lane_value(player_data)



# rank = data['rank']
# statics_data = pd.read_csv('./csv/200731/%s/200731_%s.csv' % (rank, rank))
# print(player_data.get('lane1').get(0))
# print(pd.DataFrame(player_data.get('lane1')).T)

rank = 'diamond'
lane = 'support'
stats = pd.read_csv('./csv/2008/%s/2008_stastics.csv' % rank)
# print(stats)
lane_stats = stats[stats['position'] == lane].reset_index(drop=True)
# print(lane_stats)