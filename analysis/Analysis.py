import csv, os, time
import numpy as np
import pandas as pd
# from IPython.display import display
# from sklearn.preprocessing import MinMaxScaler

pd.set_option('display.max_row', 500)

[
        'account_id',                   # 암호화된 유저 아이디
        'summoner_name',                # 소환사 명
        'match_id',                     # match id
        'position',                     # 포지션
        'playtime',                     # 게임 플레이 시간
        'kills',                        # kill
        'deaths',                       # deaths
        'assists',                      # assists
        'totalDamageDealtToChampions',  # 챔피언에게 입힌 피해량
        'totalHeal',                    # 총 회복량
        'damageSelfMitigated',          # 감소시킨 피해량(방어막?)
        'visionScore',                  # 시야점수
        'timeCCingOthers',              # cc기에 맞은 총 시간
        'totalDamageTaken',             # 받은 피해량
        'goldEarned',                   # 총 골드
        'totalMinionsKilled',           # cs
        'neutralMinionsKilled',         # 중립몹 킬수
        'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        'totalTimeCrowdControlDealt',   # cc기를 맞춘 총 시간
        'visionWardsBoughtInGame',      # 핑와 구매 개수
        'wardsPlaced',                  # 와드 설치수
        'wardsKilled',                  # 와드 파괴수
        'killsRatio',                   # 킬관여율
        'deathsRatio',                  # 데스관여울
    ]

def outliers(data):
    q1, q3 = np.percentile(data, [25, 75])
    iqr = q3 - q1
    lower_bound = q1 - (iqr * 1.5)
    upper_bound = q3 + (iqr * 1.5)
    return data[(lower_bound > data) | (data > upper_bound)]

def create_folder(path):
    root = './csv/'
    try:
        os.mkdir(root + path)
        print(path + '폴더를 생성하였습니다.')
    except:
        print('!!!!!' + path + '폴더가 이미 생성되어 있습니다.')
        print('데이터를 중복 생성하는 것이 아닌지 확인하세요.')
        print(root + path)
        print('작업 취소하기: ctrl + c')
    print()


def auto_mode():
    now = time.localtime()
    now = "%02d%02d%02d_%02d:%02d:%02d:" % (int(str(now.tm_year)[2:]), now.tm_mon, now.tm_mday, now.tm_hour, now.tm_min, now.tm_sec)
    positions = ['top', 'mid', 'jungle', 'carry', 'support']
    ranks = ['diamond', 'platinum', 'gold', 'silver', 'bronze', 'iron']
    for rank in ranks:
        try:
            file = open('./csv/%s/%s/%s_stastics.csv' % (now[:4], rank, now[:4]), 'w', newline='', encoding='utf-8')
            csvfile = csv.writer(file)
            csvfile.writerow([
                'position', 
                'visionScoreMean', 
                'visionScoreStd', 
                'csPerMinMean',
                'csPerMinStd', 
                'deathsRatioMean',
                'deathsRatioStd',
                'killAssistPerMinMean',
                'killAssistPerMinStd',
                'damageDealtPerMinMean',
                'damageDealtPerMinStd',
                'damageTakenPerMinMean',
                'damageTakenPerMinStd',
                'killsRatioMean',
                'killsRatioStd',
                ])
        except:
            continue

        for position in positions:
            data = pd.read_csv('./csv/2008/%s/2008_%s.csv' % (rank, position), encoding='utf-8')
            # print(data)

            # 중복된 소환사 제거
            data.drop_duplicates(keep='first', inplace=True)
            # print(data)

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

            # 이상치 제거
            idxs = []
            for column in list(using_data.columns):
                idxs += list(outliers(using_data[column]).index)
                # csPerMin_outlier_idx = list(outliers(using_data.csPerMin).index)
                # deathsRatio_outlier_idx = list(outliers(using_data.deathsRatio).index)
            using_data = using_data.drop(idxs).reset_index(drop=True)

            # 랭크별로 각 요소의 평균 삽입
            row = [position, ]
            for column in list(using_data.columns):
                row.extend([using_data[column].mean(), using_data[column].std()])
            print(row)
            csvfile.writerow(row)
            


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