import csv, requests, json, os, sys
import pandas as pd

# print(os.path.abspath(os.path.dirname(__file__)))
# print(os.path.dirname(__file__))
# print(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__)))))
sys.path.append(os.path.abspath(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))

# 분석용 데이터 수집 스크립트
import Analysis, DataPipeline
# from django.shortcuts import render

from rest_framework.response import Response
# from rest_framework.renderers import JSONRenderer
from rest_framework.decorators import api_view

# Create your views here.

@api_view(['GET'])
def stats(request, rank, lane):
    # manage.py 기준
    stats = pd.read_csv('../csv/2008/%s/2008_stastics.csv' % rank)
    # print(stats)
    lane_stats = stats[stats['position'] == lane]
    return Response(lane_stats.T.to_dict().get(lane_stats.index[0]))


@api_view(['GET'])
def summoner_stats(request, summoner_name):
    # summoner_data = requests.get().json()  # 소환사 정보를 가져오는 URL
    
    pass