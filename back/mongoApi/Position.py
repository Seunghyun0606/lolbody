# 패키지 불러오기

import numpy as np
import seaborn as sns
import pandas as pd
import sys
import json
from sklearn.preprocessing import MinMaxScaler

sns.set()
r = json.loads(sys.argv[2].replace("'",'"'))
participantId = sys.argv[1]

temp_match = pd.DataFrame(r)  # 전체 데이터 저장 (데이터 값에 딕셔너리 형태로 필요한 정보가 저장)
temp_timeline = pd.DataFrame()
len_timeline = temp_match.shape[0]
l = []
xs = {}
for i in range(len_timeline):  # 각 게임의 타임라인이 모두 다르기 때문 (1분 가량마다 타임라인이 찍힌다)
    temp_current_timeline = pd.DataFrame(temp_match['frames'].iloc[i]['participantFrames']).T
    ta = np.full((1,len(temp_match['frames'].iloc[i]['participantFrames'])),participantId)
    if i != (len_timeline - 1):
        temp_position = pd.DataFrame(list(temp_current_timeline['position'].values), index=temp_current_timeline.index)
        l.append(temp_position)

def mapscaler(data, x_range=(-120, 14870), y_range=(-120, 14980)):  # x, y의 범위
    x = data['x'].astype('float64').values.reshape(-1, 1)
    y = data['y'].astype('float64').values.reshape(-1, 1)
    x_range = np.array(x_range).astype('float64').reshape(-1, 1)
    y_range = np.array(y_range).astype('float64').reshape(-1, 1)
    scaler = MinMaxScaler(feature_range=(0, 512))  # 0~512로 변환
    scaler.fit(x_range)
    x = scaler.transform(x)
    scaler.fit(y_range)
    y = scaler.transform(y)
    return x, 512 - y

return_list = []
for i in range(len(l)):
    x,y = mapscaler(l[i])

    return_list.append(json.loads('{"x":'+str(x.tolist()[0][0])+',"y":'+str(y.tolist()[0][0])+'}'))
print(return_list)
