import pymongo
import pandas as pd

# for stats in save_stats_list:
#     db_root.badges.insert({'stats': stats, 'name': ''})

now = '20.08'
queue = 420
stastics = './csv/' + now + '/stastics/'
df = pd.read_csv('./csv/%s/stastics/stastics_%s.csv' % (now, queue))
print(df[['tier', 'position', 'totalDamageDealtToChampionsPerMinMean']])