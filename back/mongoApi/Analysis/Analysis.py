import csv, os, time, pymongo
import numpy as np
import pandas as pd
from IPython.display import display

connection = pymongo.MongoClient('mongodb://lolbody:fhfqkelchlrhdi3%232%401!@13.125.220.135:27017/test?authSource=test&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false')
db_root = connection.test

now = time.localtime()
now = "%02d.%02d" % (int(str(now.tm_year)[2:]), now.tm_mon)
now = '20.08'

source = '/csv/' + now + '/source/'
stastics = '/csv/' + now + '/stastics/'

pd.set_option('display.max_row', 500)
pd.set_option('display.max_columns', 100)
tiers = ['DIAMOND', 'PLATINUM', 'GOLD', 'SILVER', 'BRONZE', 'IRON', 'TOTAL']
positions = ['TOP', 'MID', 'JUNGLE', 'BOTTOM', 'SUPPORT', 'TOTAL', 'NONE']
spells = [1, 11, 12, 13, 14, 21, 3, 30, 31, 32, 39, 4, 6, 7]

# 각 플레이어 인게임 데이터가 가지는 요소
save_stats_list_450 = [
        'kills',                        # kill
        'deaths',                       # deaths
        'assists',                      # assists
        # "largestKillingSpree",
        # "largestMultiKill",
        # "killingSprees",
        # "longestTimeSpentLiving",
        # "doubleKills",
        # "tripleKills",
        # "quadraKills",
        # "pentaKills",
        # "unrealKills",
        'totalDamageDealtToChampions',  # 챔피언에게 입힌 피해량
        'totalHeal',                    # 총 회복량
        "totalUnitsHealed",              # 회복시켜준 유저수
        'damageSelfMitigated',          # 감소시킨 피해량(방어막?)
        "damageDealtToObjectives",       # 오브젝트에게 준 피해량
        "damageDealtToTurrets",         # 타워에 준 피해량
        'timeCCingOthers',              # cc기에 맞은 총 시간
        'totalDamageTaken',             # 받은 피해량
        'goldEarned',                   # 총 골드
        'totalMinionsKilled',           # cs
        # 'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        'totalTimeCrowdControlDealt',   # cc기를 맞춘 총 시간
        # 'wardsPlaced',                  # 와드 설치수
        # 'wardsKilled',                  # 와드 파괴수
        'killsRatio',                   # 킬관여율
        'deathsRatio',                  # 데스관여울
        'killAssist',
    ]

save_stats_list_420 = save_stats_list_450 + [
        'visionScore',                  # 시야점수
        'neutralMinionsKilled',         # 중립몹 킬수
        'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        'visionWardsBoughtInGame',      # 핑와 구매 개수
        'wardsPlaced',                  # 와드 설치수
        'wardsKilled',                  # 와드 파괴수
    ]


def get_save_stats_list(queue):
    if queue == 420:
        return save_stats_list_420
    else:
        return save_stats_list_450
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
    print(now)

    # 폴더 생성
    create_folder(stastics)

    for queue in [420, 450]:
        # 통계 파일 생성
        file = open(stastics + 'stastics_%s.csv' % queue, 'w', newline='', encoding='utf-8')
        csvfile = csv.writer(file)
        columns = ['position', 'tier', 'queue']

        save_stats_list = get_save_stats_list(queue)
        cor_range = 16 if queue == 420 else 9

        for stats in save_stats_list:
            if 'Ratio' in stats:
                columns.extend([stats+'Mean', stats+'Std'])
                continue
            columns.extend([stats+'PerMinMean', stats+'PerMinStd'])
        for i in range(cor_range):
            i = str(i)
            columns.extend(['xcor_%sMean' % i, 'xcor_%sStd' % i, 'ycor_%sMean' % i, 'ycor_%sStd' % i])
        for spell in spells:
            columns.append('spell_%s' % spell)
        csvfile.writerow(columns)

        # summoner_name을 기준으로 중복값 제거
        source_data = get_source_data(source, 'player_in_game_data_%s' % queue)
        df = pd.DataFrame(source_data[1:], columns=source_data[0])
        df = df[df['queue'] == str(queue)]
        df = df.drop_duplicates(['summoner_name'], keep=False).reset_index(drop=True)

        for position in positions:
            # 420 인경우 칼바람 데이터 안함
            if queue == 420 and position == 'NONE': continue
            # 450 인경우 협곡 데이터 안함
            if queue == 450 and position != 'NONE': continue
            for tier in tiers:
                if position == 'TOTAL' and tier == 'TOTAL':
                    position_tier_data = df.reset_index()
                elif position == 'TOTAL':
                    position_tier_data = df[(df['tier'] == tier)].reset_index()
                elif tier == 'TOTAL':
                    position_tier_data = df[(df['position'] == position)].reset_index()
                else:
                    position_tier_data = df[(df['position'] == position) & (df['tier'] == tier)].reset_index()
                row = [position, tier, queue]
                
                # print(position_tier_data)
                # 이상치 제거 + dtype 변환
                # 일단 dtype변환만
                b = len(position_tier_data)
                idxs = []
                for column in list(position_tier_data.columns):
                    if column == 'index' \
                        or column == 'match_id'\
                        or not is_number(position_tier_data[column][0]): continue
                    position_tier_data[column] = pd.to_numeric(position_tier_data[column])
                    # 일단 이상치 제거 안함
                    if True: continue
                    tmp = list(outliers(position_tier_data[column]).index)
                    # print(column, len(tmp))
                    idxs += tmp
                position_tier_data = position_tier_data.drop(idxs).reset_index(drop=True)


                for stats in save_stats_list:
                    if 'killAssist' == stats:
                        tmp_data = (position_tier_data['kills'] + position_tier_data['assists']) / (position_tier_data['playtime'] / 60)
                        row.extend([tmp_data.mean(), tmp_data.std()])
                        continue
                    position_tier_data[stats] = pd.to_numeric(position_tier_data[stats])
                    tmp_data = position_tier_data[stats]
                    if 'Ratio' not in stats:
                        tmp_data = position_tier_data[stats] / (position_tier_data['playtime'] / 60)
                    row.extend([tmp_data.mean(), tmp_data.std()])
                for i in range(cor_range):
                    position_tier_data['xcor_%s' % i] = pd.to_numeric(position_tier_data['xcor_%s' % i])
                    position_tier_data['ycor_%s' % i] = pd.to_numeric(position_tier_data['ycor_%s' % i])
                    row.extend([
                        position_tier_data['xcor_%s' % i].mean(),
                        position_tier_data['xcor_%s' % i].std(),
                        position_tier_data['ycor_%s' % i].mean(),
                        position_tier_data['ycor_%s' % i].std(),
                    ])
                for s in spells:
                    tmp_s = 0
                    for spell in ['spell1', 'spell2']:
                        tmp = position_tier_data[position_tier_data[spell] == s]
                        tmp_s += len(tmp)
                    row.append(tmp_s / len(position_tier_data))
                csvfile.writerow(row)
        file.close()
    

def set_data_base():
    for queue in [420, 450]:
        # db_root.stastics.insert({'_id': queue, 'stastics': {}})
        stastics_collection = db_root.stastics
        stastics_420_db = stastics_collection.find_one({'_id': queue})
        file = open(stastics + 'stastics_%s.csv' % queue, 'r', newline='', encoding='utf-8')
        reader = csv.DictReader(file)
        data_table = {
            'mean': {},
            'std': {},
        }
        line_table = {
                'TOP': data_table.copy(),
                'MID': data_table.copy(),
                'JUNGLE': data_table.copy(),
                'SUPPORT': data_table.copy(),
                'BOTTOM': data_table.copy(),
                'TOTAL': data_table.copy(),
        } if queue == 420 else {'NONE': data_table.copy()}
        tmp = {
            'DIAMOND': line_table.copy(),
            'PLATINUM': line_table.copy(),
            'GOLD': line_table.copy(),
            'SILVER': line_table.copy(),
            'BRONZE': line_table.copy(),
            'IRON': line_table.copy(),
            'TOTAL': line_table.copy(),
        }
        save_stats_list = get_save_stats_list(queue)
        for row in reader:
            tier = row.get('tier')
            line = row.get('position')
            # print(line)
            for k, v in row.items():
                for stats in save_stats_list:
                    if stats in k:
                        if 'Std' in k:
                            tmp[tier][line]['std'][k[:-3]] = v
                        if 'Mean' in k:
                            tmp[tier][line]['mean'][k[:-4]] = v
                        break
            # stastics_420_db['stastics'].append(tmp)
        stastics_collection.update({'_id': queue}, { '$set': {'stastics': tmp}})


if __name__ == '__main__':
    auto_mode()
    set_data_base()