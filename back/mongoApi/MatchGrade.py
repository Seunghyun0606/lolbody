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
        'visionScore' : player_data.get('visionScore'),
        'csPerMin' : player_data.get('csPerMin'),
        'deathsRatio' : player_data.get('deathRatio') / 100,
        'killAssistPerMin' : (player_data.get('kill') + player_data.get('assist')) / (player_data.get('duration') / 60)\
            if player_data.get('kill') + player_data.get('assist') !=0 else 0,
        'damageDealtPerMin' : player_data.get('damageDealt') / (player_data.get('duration') / 60),
        'damageTakenPerMin' : player_data.get('damageTaken') / (player_data.get('duration') / 60),
        'killsRatio' : player_data.get('killRatio') / 100,
        # kill : player_data.get('kill')
        # assist : player_data.get('assist')
        # death : player_data.get('death')
        # duration : player_data.get('duration')
        # kda : player_data.get('kda') if player_data.get('kda') >=0 else float('inf')
        # gold : player_data.get('gold')
        # cs : player_data.get('cs')
    }
    tier = player_data.get('tier')
    cols = ['visionScore', 'csPerMin', 'deathsRatio', 'killAssistPerMin', 'damageDealtPerMin', 'damageTakenPerMin', 'killsRatio']
    # cols2 = ['visionScore', 'csPerMin', 'damageDealtPerMin', 'damageTakenPerMin']

    # print(player_df)
    # player_mean = player_df.mean()
    # player_std = player_df.std()


    stats = pd.read_csv('./csv/2008/%s/2008_stastics.csv' % tier.lower())
    # 포지션 유사도 판별로 정해볼 예정
    min_distance = float('inf')
    tmp_position = ''
    player_p_value = dict()
    for position in ['top', 'jungle', 'mid', 'carry', 'support']:
        lane_stats = stats[stats['position'] == position].reset_index(drop=True)
        # print(lane_stats)
        tmp_distance = 0
        tmp_player_p_value = dict()
        for col in cols:
            mean = col + 'Mean'
            std = col + 'Std'
            tmp_player_p_value[col] = change_to_p_value(z_value(data[col], lane_stats[mean][0], lane_stats[std][0]))
            player_z_value = z_value(data[col], lane_stats[mean][0], lane_stats[std][0])
            # print(col, player_z_value)
            tmp_distance += player_z_value**2
        tmp_distance = tmp_distance**0.5
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
    # d = "{'death':11,'kda':0.7272727272727273,'deathRatio':32.35294117647059,'kill':8,'csPerMin':3.3412322274881516,'duration':1688,'gold':9846,'cs':94,'tier':'BRONZE','assist':0,'killRatio':53.333333333333336,'damageDealt':12712,'damageTaken':25843,'visionScore':15}"
    # d2 = "{'death':5,'visionScore':28,'kda':5.4,'deathRatio':16.666666666666668,'kill':1,'csPerMin':0.976491862567812,'duration':1659,'gold':8030,'cs':27,'tier':'SILVER','assist':26,'killRatio':77.14285714285714,'damageDealt':9923,'damageTaken':16545}"
    player_data = json.loads(sys.argv[1].replace("'", '"'))
    # print(type(player_data))
    print(get_player_match_grade(player_data))


# duration, k, d, a, killRatio, gold, cs, csPerMin
# 필요한것 => deathRatio, damageDealt, damageTaken