import pymongo, requests, json, random
import numpy as np
import pandas as pd
from IPython.display import display

API_KEY = 'RGAPI-a9164853-7162-4cef-bf5e-55663968c07e'

def check_api_key(api_key):
    pass

# 티어와 구간을 정하면 그 랭크에 위치한 유저의 소환사명을 리스트로 반환
def get_summoner_id_list(tier, dicvision):

    pass

# 소환사 닉네임을 입력하면 accountId를 반환함
# input: string
# output: string
def get_summoner_account_id(summoner_name):
    if type(summoner_name) != type('string'): return None
    get_sommoner_accounts_URL = 'https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/'
    account_dict = requests.get(get_sommoner_accounts_URL + summoner_name, \
        params={ 'api_key': API_KEY }).json()
    account_id = account_dict.get('accountId')
    return account_id


# 소환사 id를 입력하면 경기 id를 반환함
# input: string, random factor
# output: string
def get_summoner_match_id(account_id, random=False):
    if type(account_id) != type('string'): return None
    get_sommoner_matches_URL = 'https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/'
    matches_dict = requests.get(get_sommoner_matches_URL + account_id, \
        params={ 'api_key': API_KEY, 'queue': 420, 'season': 13 }).json()
    if matches_dict.get('endIndex') is None: return None
    match_idx = 0 if random == False else random.choice(range(matches_dict.get('endIndex')))
    match_id = matches_dict.get('matches')[match_idx].get('gameId')
    return match_id


# 블루팀, 레드팀의 total kda + 데이터 정합성 판별하여 반환
# input: match_participansts
# output: teams_data
def get_teams_data(participants):
    # 반환 할 팀 데이터 값
    teams_data = [{
        'kills': 0,
        'deaths': 0,
        'assists': 0,
        'top': 0,
        'jungle': 0,
        'mid': 0,
        'carry': 0,
        'support': 0,
        'isTrash': False,
    },
    {
        'kills': 0,
        'deaths': 0,
        'assists': 0,
        'top': 0,
        'jungle': 0,
        'mid': 0,
        'carry': 0,
        'support': 0,
        'isTrash': False,
    }]

    # 데이터 정합성 판별 => 포지션이 잘 파악이 되었는가?
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1
        
        # 팀 데이터가 쓰레기로 판별되면 나머지 팀원 데이터도 그냥 넘겨버림
        if teams_data[team_idx]['isTrash']: continue

        lane = participants[idx].get('timeline').get('lane')
        role = participants[idx].get('timeline').get('role')

        # 정글러 판별(riot 정보 + 강타 스펠)
        if (participants[idx]['spell1Id'] == 11 or participants[idx]['spell2Id'] == 11)\
            and lane == 'JUNGLE' and role == 'NONE':
            teams_data[team_idx]['jungle'] += 1
            # 포지션 데이터 추가
            participants[idx]['timeline']['position'] = 'jungle'

        # 탑라이너 판별
        elif lane == 'TOP' and role == 'SOLO':
            teams_data[team_idx]['top'] += 1
            # 포지션 데이터 추가
            participants[idx]['timeline']['position'] = 'top'

        # 미드라이너 판별
        elif lane == 'MIDDLE' and role == 'SOLO':
            teams_data[team_idx]['mid'] += 1
            # 포지션 데이터 추가
            participants[idx]['timeline']['position'] = 'mid'

        # 원딜, 서폿 판별
        elif lane == 'BOTTOM':
            if role == 'DUO_CARRY':
                teams_data[team_idx]['carry'] += 1
                # 포지션 데이터 추가
                participants[idx]['timeline']['position'] = 'carry'

            elif role == 'DUO_SUPPORT':
                teams_data[team_idx]['support'] += 1
                # 포지션 데이터 추가
                participants[idx]['timeline']['position'] = 'support'

            # 쓰레기 데이터
            else:
                teams_data[team_idx]['isTrash'] = True
        # 쓰레기 데이터
        else:
            teams_data[idx]['isTrash'] = True
    
    # 쓰레기 데이터가 아닌경우 team kda 불러오기
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1

        if teams_data[team_idx]['isTrash']: continue

        teams_data[team_idx]['kills'] += participants[idx].get('stats').get('kills')
        teams_data[team_idx]['deaths'] += participants[idx].get('stats').get('deaths')
        teams_data[team_idx]['assists'] += participants[idx].get('stats').get('assists')
    return teams_data


# match id를 입력하면 데이터 값을 반환
# input: int
# output: 'lane': [[플레이어 인게임 데이터]...]를 가지는 dict
def get_match_data(match_id):
    if type(match_id) != type(1234567890): return None
    get_match_data_URL = 'https://kr.api.riotgames.com/lol/match/v4/matches/'
    match_data = requests.get(get_match_data_URL + str(match_id), \
        params={ 'api_key': API_KEY }).json()
    # 잘못된 match_id로 요청하는 경우
    if match_data.get('status') is not None: return None

    # 각 플레이어 인게임 데이터가 가지는 요소
    save_stats_list = [
      # 'account_id',                   # 암호화된 유저 아이디
      # 'summoner_name',                # 소환사 명
      # 'position',                     # 포지션
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
      # 'killsRatio',                   # 킬관여율
      # 'deathsRatio',                  # 데스관여울
    ]

    player_in_game_data_by_position = {
        'top': [],
        'mid': [],
        'jungle': [],
        'carry': [],
        'support': [],
        'trash': [],
    }

    match_participants = match_data.get('participants')
    match_participantIdentities = match_data.get('participantIdentities')
    teams_data = get_teams_data(match_participants)
    print(teams_data)

    # 받아온 teams_data로 유저별 인게임 stats 리스트로 저장
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1
        
        # 팀데이터가 쓰레기경우 그냥 넘겨버림
        if teams_data[team_idx]['isTrash']: continue

        position = match_participants[idx]['timeline']['position']
        account_id = match_participantIdentities[idx].get('player').get('accountId')
        summoner_name = match_participantIdentities[idx].get('player').get('summonerName')

        player_in_game_stats = [account_id, summoner_name, position]

        # 플레이어 stats 삽입
        for stats in save_stats_list:
            player_in_game_stats.append(match_participants[idx].get('stats')[stats])
        
        # killsRatio, deathsRatio
        if player_in_game_stats[3] + player_in_game_stats[5] == 0:
            player_in_game_stats.append(0)
        else:
            kills_ratio = (player_in_game_stats[3] + player_in_game_stats[5]) / teams_data[team_idx]['kills']
            player_in_game_stats.append(kills_ratio)

        if player_in_game_stats[4] == 0:
            player_in_game_stats.append(0)
        else:
            deaths_ratio = player_in_game_stats[4] / teams_data[team_idx]['deaths']
            player_in_game_stats.append(deaths_ratio)
        
        player_in_game_data_by_position[player_in_game_stats[2]].append(player_in_game_stats)

    return player_in_game_data_by_position


if __name__ == '__main__':
    # summoner = get_summoner_account_id('123')
    # print(get_summoner_match_id(summoner))
    # summoner = get_summoner_account_id('홍땅크로가자')
    # print(get_summoner_match_id(summoner))
    summoner = get_summoner_account_id('공짜5')
    match_id = get_summoner_match_id(summoner)
    player_in_game_data_by_position = get_match_data(match_id)

    
