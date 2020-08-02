import math
import numpy as np
import pandas as pd
from scipy.stats import norm

# 소환사 아이디 넣으면 주라인 2가지의 각 라인별 정보를 받아오는 API
data = {
    'rank': 'diamond',
    'position': {
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
rank = data['rank']
statics_data = pd.read_csv('./csv/200731/%s/200731_%s.csv' % (rank, rank))

for lane in data.keys():
    if lane == 'position' or lane == 'rank': continue
    # print(lane)
    lane_df = pd.DataFrame(data[lane]).T
    print(lane_df.mean())
