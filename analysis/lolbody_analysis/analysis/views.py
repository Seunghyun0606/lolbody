# from django.shortcuts import render

from rest_framework.response import Response
# from rest_framework.renderers import JSONRenderer
from rest_framework.decorators import api_view

import csv, requests, json
import pandas as pd
# Create your views here.

@api_view(['GET'])
def stats(request, rank, lane):
    # manage.py 기준
    stats = pd.read_csv('../csv/200731/%s/200731_stastics.csv' % rank)
    # print(stats)
    lane_stats = stats[stats['position'] == lane]
    return Response(lane_stats.T.to_dict().get(lane_stats.index[0]))



def summoner_stats(request, summoner_name):
    # summoner_data = requests.get().json()  # 소환사 정보를 가져오는 URL
    pass