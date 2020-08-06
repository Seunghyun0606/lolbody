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
        'visionScore': 'vision',
        'csPerMin': 'cs',
        'deathsRatio': 'death',
        'killAssistPerMin': 'fight',
        'damageDealtPerMin': 'dealt',
        'damageTakenPerMin': 'taken',
        'killsRatio': 'kill',
    }
    badges = []
    for k, v in player_p_value.items():
        if v > 0.70:
            badges.append(badge_items.get(k))
    return badges


def get_player_lane_value(player_data):
    cols = ['visionScore', 'csPerMin', 'deathsRatio', 'killAssistPerMin', 'damageDealtPerMin', 'damageTakenPerMin', 'killsRatio']
    rank = player_data.get('tier')
    ret = {
        'lane1': {},
        'lane2': {},
        'badges': [],
    }
    for l_name, lane in player_data.items():
        if type(lane) != type(['list']): continue
        if not len(lane): continue
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
        ret['badges'] = get_badges(player_p_value)
    return ret

if __name__ == '__main__':
    # print(player_data)
    player_data = json.loads(sys.argv[1].replace("'", '"'))
    # print(type(player_data))
    print(get_player_lane_value(player_data))
