import csv, os, time
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from IPython.display import display
# from sklearn.preprocessing import MinMaxScaler

# %matplotlib inline
# sns.set_style('darkgrid')

pd.set_option('display.max_row', 500)
pd.set_option('display.max_columns', 100)
tiers = ['DIAMOND', 'PLATINUM', 'GOLD', 'SILVER', 'BRONZE', 'IRON']
spells = [1, 11, 12, 13, 14, 21, 3, 30, 31, 32, 39, 4, 6, 7]

save_stats_list = [
        'kills',                        # kill
        'deaths',                       # deaths
        'assists',                      # assists
        # "largestKillingSpree": 7,
        # "largestMultiKill": 1,
        # "killingSprees": 1,
        # "longestTimeSpentLiving": 856,
        # "doubleKills": 0,
        # "tripleKills": 0,
        # "quadraKills": 0,
        # "pentaKills": 0,
        # "unrealKills": 0,
        'totalDamageDealtToChampions',  # 챔피언에게 입힌 피해량
        'totalHeal',                    # 총 회복량
        # "totalUnitsHealed"              # 회복시켜준 유저수
        'damageSelfMitigated',          # 감소시킨 피해량(방어막?)
        # "damageDealtToObjectives"       # 오브젝트에게 준 피해량
        # "damageDealtToTurrets",         # 타워에 준 피해량
        'visionScore',                  # 시야점수
        # 'timeCCingOthers',              # cc기에 맞은 총 시간
        'totalDamageTaken',             # 받은 피해량
        'goldEarned',                   # 총 골드
        'totalMinionsKilled',           # cs
        'neutralMinionsKilled',         # 중립몹 킬수
        'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        # 'totalTimeCrowdControlDealt',   # cc기를 맞춘 총 시간
        'visionWardsBoughtInGame',      # 핑와 구매 개수
        'wardsPlaced',                  # 와드 설치수
        'wardsKilled',                  # 와드 파괴수
        'killsRatio',                   # 킬관여율
        'deathsRatio',                  # 데스관여울
        'killAssist',
    ]

def outliers(data):
    q1, q3 = np.percentile(data, [25, 75])
    iqr = q3 - q1
    lower_bound = q1 - (iqr * 1.5)
    upper_bound = q3 + (iqr * 1.5)
    return data[(lower_bound > data) | (data > upper_bound)]

def create_folder(path):
    try:
        os.mkdir(path)
        print(path + '폴더를 생성하였습니다.')
    except:
        print('!!!!!' + path + '폴더가 이미 생성되어 있습니다.')
        print('데이터를 중복 생성하는 것이 아닌지 확인하세요.')
    print()


def get_source_data(source, file_name):
    try:
        file = open(source + '%s.csv' % file_name, 'r', newline='', encoding='utf-8')
        csvf = csv.reader(file)
        ret = []
        for lst in csvf:
            ret.append(lst)
        return ret
    except:
        print('소스파일이 존재하지 않습니다')
        return False

def is_number(num):
    try:
        judge = str(float(num))
        return False if(judge=='nan' or judge=='inf' or judge =='-inf') else True
    except ValueError:
        return False


def auto_mode():
    now = time.localtime()
    now = "%02d.%02d" % (int(str(now.tm_year)[2:]), now.tm_mon)
    print(now)

    # 폴더 생성
    source = './Analysis/csv/' + now + '/source/'
    stastics = './Analysis/csv/' + now + '/stastics/'
    create_folder(stastics)

    # 통계 파일 생성
    file = open(stastics + 'stastics.csv', 'w', newline='', encoding='utf-8')
    csvfile = csv.writer(file)
    columns = ['position', 'tier']
    for stats in save_stats_list:
        if 'Ratio' in stats:
            columns.extend([stats+'Mean', stats+'Std'])
            continue
        columns.extend([stats+'PerMinMean', stats+'PerMinStd'])
    for i in range(16):
        i = str(i)
        columns.extend(['xcor_%sMean' % i, 'xcor_%sStd' % i, 'ycor_%sMean' % i, 'ycor_%sStd' % i])
    for spell in spells:
        columns.append('spell_%s' % spell)
    csvfile.writerow(columns)

    # summoner_name을 기준으로 중복값 제거
    source_data = get_source_data(source, 'player_in_game_data')
    df = pd.DataFrame(source_data[1:], columns=source_data[0])
    df = df.drop_duplicates(['summoner_name'], keep=False).reset_index(drop=True)
    # print(df)

    for position in ['TOP', 'MID', 'JUNGLE', 'BOTTOM', 'SUPPORT']:
        for tier in tiers:
            row = [position, tier, ]
            position_tier_data = df[(df['position'] == position) & (df['tier'] == tier)].reset_index()

            # 이상치 제거 + dtype 변환
            # 일단 dtype변환만
            b = len(position_tier_data)
            idxs = []
            for column in list(position_tier_data.columns):
                if column == 'index' \
                    or column == 'match_id'\
                    or not is_number(position_tier_data[column][0]): continue
                position_tier_data[column] = pd.to_numeric(position_tier_data[column])
                # if 'cor_' in column: continue
                if True: continue
                tmp = list(outliers(position_tier_data[column]).index)
                print(column, len(tmp))
                idxs += tmp
            position_tier_data = position_tier_data.drop(idxs).reset_index(drop=True)
            # print(b, len(position_tier_data))
            # print(len(set(idxs)))


            for stats in save_stats_list:
                if 'killAssist' == stats:
                    tmp_data = (position_tier_data['kills'] + position_tier_data['assists']) / (position_tier_data['playtime'] / 60)
                    # print(tmp_data)
                    row.extend([tmp_data.mean(), tmp_data.std()])
                    continue
                position_tier_data[stats] = pd.to_numeric(position_tier_data[stats])
                tmp_data = position_tier_data[stats]
                if 'Ratio' not in stats:
                    tmp_data = position_tier_data[stats] / (position_tier_data['playtime'] / 60)
                row.extend([tmp_data.mean(), tmp_data.std()])
            for i in range(16):
                position_tier_data['xcor_%s' % i] = pd.to_numeric(position_tier_data['xcor_%s' % i])
                position_tier_data['ycor_%s' % i] = pd.to_numeric(position_tier_data['ycor_%s' % i])
                row.extend([
                    position_tier_data['xcor_%s' % i].mean(),
                    position_tier_data['xcor_%s' % i].std(),
                    position_tier_data['ycor_%s' % i].mean(),
                    position_tier_data['ycor_%s' % i].std(),
                ])
            for s in spells:
                # print(position_tier_data[spell])
                tmp_s = 0
                for spell in ['spell1', 'spell2']:
                    tmp = position_tier_data[position_tier_data[spell] == s]
                    tmp_s += len(tmp)
                row.append(tmp_s / len(position_tier_data))
            csvfile.writerow(row)
    file.close()          

if __name__ == '__main__':
    auto_mode()
    # data_stastics = [d.mean(), d.std()]
    # print(data_stastics)

    # z값으로 변환
    # z_d = (d - d.mean()) / d.std()
    # print(d)
    # print(z_d)

    # 정규화
    # d = d.reset_index(drop=True)
    # normal_d = pd.DataFrame(MinMaxScaler().fit_transform(d), columns=d.columns)
    # print(normal_d)