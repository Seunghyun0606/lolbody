import math, os, sys
import numpy as np
import pandas as pd
from scipy.stats import norm

# sys.path.append(os.path.abspath(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))


# 소환사 아이디 넣으면 주라인 2가지의 각 라인별 정보를 받아오는 API
# player_data = {
#     'rank': 'diamond',
#     'positions': {
#         'lane1': 'top',
#         'lane2': 'jungle'
#         },
#     'lane1': { 
#         0: { 
#             'visionScore': 0,
#             'csPerMin': 0,
#             'kills': 0,
#             'deaths': 0,
#             'assist': 0,
#             'gameDuration': 0,
#             'totalDamageDealtToChampions': 0,
#             'totalDamageTaken': 0,
#             },
#         1: { 
#             'visionScore': 1,
#             'csPerMin': 1,
#             'kills': 1,
#             'deaths': 1,
#             'assist': 1,
#             'gameDuration': 1,
#             'totalDamageDealtToChampions': 1,
#             'totalDamageTaken': 1,
#             },
#         2: { 
#             'visionScore': 2,
#             'csPerMin': 2,
#             'kills': 2,
#             'deaths': 2,
#             'assist': 2,
#             'gameDuration': 2,
#             'totalDamageDealtToChampions': 2,
#             'totalDamageTaken': 2,
#             },
#         3: { 
#             'visionScore': 3,
#             'csPerMin': 3,
#             'kills': 3,
#             'deaths': 3,
#             'assist': 3,
#             'gameDuration': 3,
#             'totalDamageDealtToChampions': 3,
#             'totalDamageTaken': 3,
#             },
#     },
#     'lane2': {
#         0: { 
#             'visionScore': 0,
#             'csPerMin': 0,
#             'kills': 0,
#             'deaths': 0,
#             'assist': 0,
#             'gameDuration': 0,
#             'totalDamageDealtToChampions': 0,
#             'totalDamageTaken': 0,
#             },
#         1: { 
#             'visionScore': 1,
#             'csPerMin': 1,
#             'kills': 1,
#             'deaths': 1,
#             'assist': 1,
#             'gameDuration': 1,
#             'totalDamageDealtToChampions': 1,
#             'totalDamageTaken': 1,
#             },
#         2: { 
#             'visionScore': 2,
#             'csPerMin': 2,
#             'kills': 2,
#             'deaths': 2,
#             'assist': 2,
#             'gameDuration': 2,
#             'totalDamageDealtToChampions': 2,
#             'totalDamageTaken': 2,
#             },
#         3: { 
#             'visionScore': 3,
#             'csPerMin': 3,
#             'kills': 3,
#             'deaths': 3,
#             'assist': 3,
#             'gameDuration': 3,
#             'totalDamageDealtToChampions': 3,
#             'totalDamageTaken': 3,
#             },
#     }
# }

# player_data = {
#     "lane1":[{"position":"JUNGLE","visionScore":41,"csPerMin":6.2,"kills":3,"deaths":5,"assist":9,"gameDuration":2574,"totalDamageDealtToChampions":20342,"totalDamageTaken":43658,"teamKills":20,"teamDeaths":29},{"position":"JUNGLE","visionScore":16,"csPerMin":6.7,"kills":17,"deaths":10,"assist":13,"gameDuration":2038,"totalDamageDealtToChampions":36045,"totalDamageTaken":22542,"teamKills":46,"teamDeaths":34},{"position":"JUNGLE","visionScore":17,"csPerMin":5.9,"kills":6,"deaths":1,"assist":8,"gameDuration":1504,"totalDamageDealtToChampions":8810,"totalDamageTaken":26046,"teamKills":32,"teamDeaths":13},{"position":"JUNGLE","visionScore":33,"csPerMin":5.9,"kills":7,"deaths":2,"assist":11,"gameDuration":1845,"totalDamageDealtToChampions":13030,"totalDamageTaken":37526,"teamKills":28,"teamDeaths":15},{"position":"JUNGLE","visionScore":21,"csPerMin":6.4,"kills":1,"deaths":4,"assist":4,"gameDuration":1869,"totalDamageDealtToChampions":7163,"totalDamageTaken":30248,"teamKills":14,"teamDeaths":15},{"position":"JUNGLE","visionScore":26,"csPerMin":4.9,"kills":7,"deaths":6,"assist":4,"gameDuration":1794,"totalDamageDealtToChampions":14782,"totalDamageTaken":23931,"teamKills":20,"teamDeaths":41},{"position":"JUNGLE","visionScore":17,"csPerMin":5.1,"kills":4,"deaths":6,"assist":9,"gameDuration":1629,"totalDamageDealtToChampions":11371,"totalDamageTaken":37307,"teamKills":18,"teamDeaths":40},{"position":"JUNGLE","visionScore":32,"csPerMin":6.6,"kills":9,"deaths":6,"assist":4,"gameDuration":1868,"totalDamageDealtToChampions":22182,"totalDamageTaken":21001,"teamKills":24,"teamDeaths":41},{"position":"JUNGLE","visionScore":27,"csPerMin":5.7,"kills":4,"deaths":4,"assist":7,"gameDuration":1680,"totalDamageDealtToChampions":12174,"totalDamageTaken":28535,"teamKills":26,"teamDeaths":10},{"position":"JUNGLE","visionScore":14,"csPerMin":5.5,"kills":6,"deaths":3,"assist":3,"gameDuration":1324,"totalDamageDealtToChampions":6715,"totalDamageTaken":21414,"teamKills":15,"teamDeaths":32},{"position":"JUNGLE","visionScore":27,"csPerMin":4.7,"kills":4,"deaths":7,"assist":5,"gameDuration":1818,"totalDamageDealtToChampions":8579,"totalDamageTaken":28560,"teamKills":21,"teamDeaths":28},{"position":"JUNGLE","visionScore":27,"csPerMin":6.5,"kills":5,"deaths":5,"assist":10,"gameDuration":1685,"totalDamageDealtToChampions":12580,"totalDamageTaken":32612,"teamKills":34,"teamDeaths":20},{"position":"JUNGLE","visionScore":27,"csPerMin":7.4,"kills":5,"deaths":2,"assist":4,"gameDuration":1206,"totalDamageDealtToChampions":5557,"totalDamageTaken":11560,"teamKills":18,"teamDeaths":5},{"position":"JUNGLE","visionScore":36,"csPerMin":4.9,"kills":10,"deaths":4,"assist":7,"gameDuration":1934,"totalDamageDealtToChampions":27219,"totalDamageTaken":25145,"teamKills":29,"teamDeaths":31},{"position":"JUNGLE","visionScore":39,"csPerMin":4.4,"kills":2,"deaths":9,"assist":8,"gameDuration":2278,"totalDamageDealtToChampions":13358,"totalDamageTaken":37572,"teamKills":24,"teamDeaths":34},{"position":"JUNGLE","visionScore":35,"csPerMin":5.8,"kills":8,"deaths":2,"assist":14,"gameDuration":2181,"totalDamageDealtToChampions":17004,"totalDamageTaken":39165,"teamKills":33,"teamDeaths":18},{"position":"JUNGLE","visionScore":30,"csPerMin":7.1,"kills":11,"deaths":10,"assist":10,"gameDuration":2135,"totalDamageDealtToChampions":31009,"totalDamageTaken":39569,"teamKills":32,"teamDeaths":48},{"position":"JUNGLE","visionScore":36,"csPerMin":4.3,"kills":12,"deaths":5,"assist":10,"gameDuration":1860,"totalDamageDealtToChampions":24481,"totalDamageTaken":28358,"teamKills":33,"teamDeaths":28},{"position":"JUNGLE","visionScore":39,"csPerMin":5.6,"kills":7,"deaths":5,"assist":16,"gameDuration":1937,"totalDamageDealtToChampions":15360,"totalDamageTaken":30277,"teamKills":34,"teamDeaths":24},{"position":"JUNGLE","visionScore":56,"csPerMin":4.8,"kills":9,"deaths":10,"assist":18,"gameDuration":2227,"totalDamageDealtToChampions":24925,"totalDamageTaken":46686,"teamKills":43,"teamDeaths":38}],
#     "lane2":[{"position":"BOTTOM","visionScore":17,"csPerMin":5.9,"kills":2,"deaths":7,"assist":10,"gameDuration":1840,"totalDamageDealtToChampions":20540,"totalDamageTaken":21962,"teamKills":28,"teamDeaths":33},{"position":"BOTTOM","visionScore":34,"csPerMin":7.5,"kills":5,"deaths":1,"assist":3,"gameDuration":1834,"totalDamageDealtToChampions":15236,"totalDamageTaken":15234,"teamKills":21,"teamDeaths":17},{"position":"BOTTOM","visionScore":16,"csPerMin":6.6,"kills":1,"deaths":8,"assist":7,"gameDuration":1620,"totalDamageDealtToChampions":13487,"totalDamageTaken":16534,"teamKills":16,"teamDeaths":34},{"position":"BOTTOM","visionScore":25,"csPerMin":6.7,"kills":8,"deaths":3,"assist":11,"gameDuration":1471,"totalDamageDealtToChampions":20796,"totalDamageTaken":13717,"teamKills":28,"teamDeaths":18},{"position":"BOTTOM","visionScore":11,"csPerMin":7.4,"kills":6,"deaths":4,"assist":6,"gameDuration":1498,"totalDamageDealtToChampions":21208,"totalDamageTaken":12080,"teamKills":27,"teamDeaths":14},{"position":"BOTTOM","visionScore":15,"csPerMin":6.7,"kills":4,"deaths":2,"assist":8,"gameDuration":1682,"totalDamageDealtToChampions":20821,"totalDamageTaken":14928,"teamKills":25,"teamDeaths":17},{"position":"BOTTOM","visionScore":10,"csPerMin":5.8,"kills":6,"deaths":7,"assist":11,"gameDuration":1510,"totalDamageDealtToChampions":12731,"totalDamageTaken":13933,"teamKills":38,"teamDeaths":23},{"position":"BOTTOM","visionScore":50,"csPerMin":1.6,"kills":0,"deaths":6,"assist":7,"gameDuration":1701,"totalDamageDealtToChampions":6182,"totalDamageTaken":20565,"teamKills":28,"teamDeaths":25},{"position":"BOTTOM","visionScore":50,"csPerMin":7.0,"kills":14,"deaths":6,"assist":19,"gameDuration":2724,"totalDamageDealtToChampions":54926,"totalDamageTaken":35068,"teamKills":60,"teamDeaths":58},{"position":"BOTTOM","visionScore":21,"csPerMin":8.6,"kills":8,"deaths":2,"assist":11,"gameDuration":1243,"totalDamageDealtToChampions":18215,"totalDamageTaken":10360,"teamKills":28,"teamDeaths":18},{"position":"BOTTOM","visionScore":17,"csPerMin":7.2,"kills":3,"deaths":5,"assist":5,"gameDuration":1675,"totalDamageDealtToChampions":15468,"totalDamageTaken":16066,"teamKills":14,"teamDeaths":26},{"position":"BOTTOM","visionScore":12,"csPerMin":7.4,"kills":13,"deaths":5,"assist":10,"gameDuration":2060,"totalDamageDealtToChampions":37879,"totalDamageTaken":24403,"teamKills":39,"teamDeaths":27},{"position":"BOTTOM","visionScore":14,"csPerMin":7.8,"kills":8,"deaths":2,"assist":7,"gameDuration":1241,"totalDamageDealtToChampions":23317,"totalDamageTaken":11692,"teamKills":20,"teamDeaths":6},{"position":"BOTTOM","visionScore":27,"csPerMin":7.5,"kills":8,"deaths":6,"assist":9,"gameDuration":1862,"totalDamageDealtToChampions":20542,"totalDamageTaken":13129,"teamKills":26,"teamDeaths":29},{"position":"BOTTOM","visionScore":9,"csPerMin":7.6,"kills":11,"deaths":4,"assist":6,"gameDuration":1299,"totalDamageDealtToChampions":17545,"totalDamageTaken":13197,"teamKills":37,"teamDeaths":17},{"position":"BOTTOM","visionScore":28,"csPerMin":7.9,"kills":16,"deaths":5,"assist":3,"gameDuration":1646,"totalDamageDealtToChampions":21593,"totalDamageTaken":17753,"teamKills":27,"teamDeaths":37},{"position":"BOTTOM","visionScore":13,"csPerMin":5.8,"kills":9,"deaths":5,"assist":5,"gameDuration":1599,"totalDamageDealtToChampions":17509,"totalDamageTaken":16087,"teamKills":38,"teamDeaths":17},{"position":"BOTTOM","visionScore":20,"csPerMin":6.4,"kills":8,"deaths":10,"assist":10,"gameDuration":2318,"totalDamageDealtToChampions":38981,"totalDamageTaken":27654,"teamKills":30,"teamDeaths":39},{"position":"BOTTOM","visionScore":26,"csPerMin":8.0,"kills":5,"deaths":4,"assist":4,"gameDuration":1768,"totalDamageDealtToChampions":9672,"totalDamageTaken":15802,"teamKills":30,"teamDeaths":22},{"position":"BOTTOM","visionScore":38,"csPerMin":5.6,"kills":6,"deaths":6,"assist":8,"gameDuration":2165,"totalDamageDealtToChampions":15549,"totalDamageTaken":22775,"teamKills":27,"teamDeaths":23}],
#     "tier":"PLATINUM"}


def change_to_p_value(z):
    # ret = ( 1 / (2 * math.pi)**0.5 ) * math.exp(-0.5*z**2)
    return norm.cdf(z)


def z_value(d, mean, std):
    return (d - mean) / std


def get_player_lane_value(player_data):
    cols = ['visionScore', 'csPerMin', 'deathsRatio', 'killAssistPerMin', 'damageDealtPerMin', 'damageTakenPerMin', 'killsRatio']
    rank = player_data.get('tier')
    ret = {
        'lane1': {},
        'lane2': {},
    }
    for l_name, lane in player_data.items():
        if type(lane) != type(['list']): continue
        position = lane[0].get('position')  ## TOP, JUNGLE...
        if position == 'BOTTOM':
            position = 'carry'
        else:
            position = position.lower()
        player_df = pd.DataFrame(columns=cols)
        for l in lane:
            tmp_data = {
                'visionScore': l.get('visionScore'),
                'csPerMin': l.get('csPerMin'),
                'deathsRatio': l.get('deaths') / l.get('teamDeaths'),

                'killAssistPerMin': (l.get('kills') + l.get('assist')) / (l.get('gameDuration') / 60),
                'damageDealtPerMin': l.get('totalDamageDealtToChampions') / (l.get('gameDuration') / 60),
                'damageTakenPerMin': l.get('totalDamageTaken') / (l.get('gameDuration') / 60),

                'killsRatio': l.get('kills') /l.get('teamKills')
            }
            player_df = player_df.append(tmp_data, ignore_index=True)

        # print(player_df)
        player_mean = player_df.mean()
        # player_std = player_df.std()

        stats = pd.read_csv('./csv/2008/%s/2008_stastics.csv' % rank)
        lane_stats = stats[stats['position'] == position].reset_index(drop=True)
        # print(lane_stats)
        player_p_value = dict()
        for col in cols:
            mean = col + 'Mean'
            std = col + 'Std'
            player_p_value[col] = change_to_p_value(z_value(player_mean[col], lane_stats[mean][0], lane_stats[std][0]))
        # print(player_p_value)
        lane_data = {
            'aggressiveness': (player_p_value.get('killAssistPerMin') + player_p_value.get('damageDealtPerMin') + player_p_value.get('damageTakenPerMin')) / 3,
            'stability': (player_p_value.get('visionScore') + player_p_value.get('csPerMin') + (1 - player_p_value.get('deathsRatio'))) / 3,
            'influence': (player_p_value.get('visionScore') + player_p_value.get('killsRatio')) / 2,
        }
        ret[l_name] = lane_data
    return ret

# print(player_data)
print(get_player_lane_value(sys.srgv[1].replace("'", '"')))


# rank = data['rank']
# statics_data = pd.read_csv('./csv/200731/%s/200731_%s.csv' % (rank, rank))
# print(player_data.get('lane1').get(0))
# print(pd.DataFrame(player_data.get('lane1')).T)

# rank = 'diamond'
# lane = 'support'
# stats = pd.read_csv('./csv/2008/%s/2008_stastics.csv' % rank)
# # print(stats)
# lane_stats = stats[stats['position'] == lane].reset_index(drop=True)
# # print(lane_stats)