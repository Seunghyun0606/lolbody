import requests, json, random, time, os, csv

API_KEY = 'RGAPI-d30e4585-746b-4fc0-8d3c-223ba52469be'

def check_api_key(api_key):
    pass

# 랭크와 구간을 정하면 그 랭크에 위치한 유저의 소환사명을 리스트로 반환
def get_summoner_id_list(rank, division, page=1):
    if type(rank) != type('string'): return None
    if type(division) != type(123456789) or 0 > division or division > 4: return None
    rank = rank.upper()
    divisions = ['', 'I', 'II', 'III', 'IV']
    division = divisions[division]
    get_summoners_accounts_URL = 'https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/%s/%s' % (rank, division)
    summoner_accounts = requests.get(get_summoners_accounts_URL, \
        params={ 'api_key': API_KEY, 'page': page }).json()
    # 데이터 전송이 성공한 경우 list type 실패한 경우 dict type
    if type(summoner_accounts) != type([]): return None
    summoner_id_list = [v.get('summonerName') for v in summoner_accounts]
    return summoner_id_list

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
def get_summoner_match_id(account_id, random_page=False):
    if type(account_id) != type('string'): return None
    get_sommoner_matches_URL = 'https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/'
    matches_dict = requests.get(get_sommoner_matches_URL + account_id, \
        params={ 'api_key': API_KEY, 'queue': 420, 'season': 13 }).json()
    if matches_dict.get('endIndex') is None: return None
    match_idx = 0 if random_page == False else random.choice(range(matches_dict.get('endIndex')))
    match_id = matches_dict.get('matches')[match_idx].get('gameId')
    return match_id


# 블루팀, 레드팀의 total kda + 데이터 정합성 판별하여 반환
# input: match_participansts, 게임 플레이 시간
# output: teams_data
def get_teams_data(participants, playtime):
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
            teams_data[team_idx]['isTrash'] = True
        participants[idx]['timeline']['playtime'] = playtime
    
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
# input: int, 또는 숫자형 리터럴
# output: 'lane': [[플레이어 인게임 데이터]...]를 가지는 dict
def get_match_data(match_id):
    if type(match_id) != type(1234567890): 
        if type(match_id) == type('string') and match_id.isdigit():
            match_id = int(match_id)
        else: return None
    get_match_data_URL = 'https://kr.api.riotgames.com/lol/match/v4/matches/'
    match_data = requests.get(get_match_data_URL + str(match_id), \
        params={ 'api_key': API_KEY }).json()
    # 잘못된 match_id로 요청하는 경우
    if match_data.get('status') is not None: return None

    # 각 플레이어 인게임 데이터가 가지는 요소
    save_stats_list = [
      # 'account_id',                   # 암호화된 유저 아이디
      # 'summoner_name',                # 소환사 명
      # 'match_id,                      # match id
      # 'position',                     # 포지션
      # 'playtime',                     # 게임 플레이 시간
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
    teams_data = get_teams_data(match_participants, match_data.get('gameDuration'))

    # 받아온 teams_data로 유저별 인게임 stats 리스트로 저장
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1
        
        # 팀데이터가 쓰레기경우 그냥 넘겨버림
        if teams_data[team_idx]['isTrash']: continue
        if match_participants[idx]['timeline'].get('position') is None: continue

        account_id = match_participantIdentities[idx].get('player').get('accountId')
        summoner_name = match_participantIdentities[idx].get('player').get('summonerName')
        position = match_participants[idx].get('timeline').get('position')
        playtime = match_participants[idx].get('timeline').get('playtime')

        player_in_game_stats = [account_id, summoner_name, match_id, position, playtime]
        # print(player_in_game_stats)
        # print(match_id)

        # 플레이어 stats 삽입
        for stats in save_stats_list:
            player_in_game_stats.append(match_participants[idx].get('stats')[stats])
        
        # killsRatio, deathsRatio
        if int(player_in_game_stats[5]) + int(player_in_game_stats[7]) == 0:
            player_in_game_stats.append(0)
        else:
            kills_ratio = int((player_in_game_stats[5]) + int(player_in_game_stats[7])) / teams_data[team_idx]['kills']
            player_in_game_stats.append(kills_ratio)

        if int(player_in_game_stats[6]) == 0:
            player_in_game_stats.append(0)
        else:
            deaths_ratio = int(player_in_game_stats[6]) / int(teams_data[team_idx]['deaths'])
            player_in_game_stats.append(deaths_ratio)
        
        player_in_game_data_by_position[player_in_game_stats[3]].append(player_in_game_stats)

    return player_in_game_data_by_position


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


def main_logic(rank, division):
    now = time.localtime()
    now = "%02d%02d%02d_%02d:%02d:%02d:" % (int(str(now.tm_year)[2:]), now.tm_mon, now.tm_mday, now.tm_hour, now.tm_min, now.tm_sec)
    print(now)
    print()
    create_folder(now[:4])
    create_folder(now[:4] + '/' + rank)
    root = './csv/%s/%s/%s' % (now[:4], rank, now[:4])

    # 이미 summoner_list가 존재하는 경우
    try:
        file = open(root + '_summoner_id_list.csv', 'r', newline='', encoding='utf-8')
        csvf = csv.reader(file)
        for lst in csvf:
            summoner_id_list = lst
            break
    except:
        summoner_id_list = []
        for i in range(1, 2):
            time.sleep(1.2)
            tmp_summoner_id_list = get_summoner_id_list(rank, division, i)
            if tmp_summoner_id_list is None: break
            summoner_id_list += tmp_summoner_id_list
        
        file = open(root + '_summoner_id_list.csv', 'w', newline='', encoding='utf-8')
        csvfile = csv.writer(file)
        csvfile.writerow(summoner_id_list)

    print(summoner_id_list)
    print(len(summoner_id_list))


    try:
        file = open(root + '_summoner_account_list.csv', 'r', newline='', encoding='utf-8')
        csvf = csv.reader(file)
        for lst in csvf:
            summoner_account_list = lst
            break
    except:
        summoner_account_list = []
        for idx, name in enumerate(summoner_id_list):
            time.sleep(1.2)
            tmp_summoner_account_id = get_summoner_account_id(name)
            if tmp_summoner_account_id is None: continue
            print(idx)
            summoner_account_list.append(tmp_summoner_account_id)

        file = open(root + '_summoner_account_list.csv', 'w', newline='', encoding='utf-8')
        csvfile = csv.writer(file)
        csvfile.writerow(summoner_account_list)

    print(summoner_account_list)
    print(len(summoner_account_list))


    try:
        file = open(root + '_match_id_list.csv', 'r', newline='', encoding='utf-8')
        csvf = csv.reader(file)
        for lst in csvf:
            match_id_list = lst
            break
    except:
        match_id_list = []
        for idx, account_id in enumerate(summoner_account_list):
            time.sleep(1.2)
            match_id = get_summoner_match_id(account_id)
            if match_id is None: continue
            print(idx)
            match_id_list.append(match_id)

        file = open(root + '_match_id_list.csv', 'w', newline='', encoding='utf-8')
        csvfile = csv.writer(file)
        csvfile.writerow(match_id_list)

    print(match_id_list)
    print(len(match_id_list))


    top = []
    mid = []
    jungle = []
    carry = []
    support = []

    for idx, match_id in enumerate(match_id_list):
        time.sleep(1.2)
        player_in_game_data_by_position = get_match_data(match_id)
        if player_in_game_data_by_position is None: continue
        top += player_in_game_data_by_position['top']
        mid += player_in_game_data_by_position['mid']
        jungle += player_in_game_data_by_position['jungle']
        carry += player_in_game_data_by_position['carry']
        support += player_in_game_data_by_position['support']
        print(idx)

    lane_name = ['top', 'mid', 'jungle', 'carry', 'support']
    for idx, lane in enumerate([top, mid, jungle, carry, support]):
        print(lane, idx)
        try:
            file = open(root + '_%s.csv' % lane_name[idx], 'r', newline='', encoding='utf-8')
            continue
        except:
            file = open(root + '_%s.csv' % lane_name[idx], 'w', newline='', encoding='utf-8')
            csvfile = csv.writer(file)
            csvfile.writerow([
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
            ])
            for row in lane:
                if type(row) != type(['list']): continue
                csvfile.writerow(row)
            file.close()


def manual_mode():
    input_accept = False

    ranks = ['diamond', 'platinum', 'gold', 'silver', 'bronze', 'iron']
    rank_check = False
    rank = ''

    division_check = False
    division = 0
    while not input_accept:
        if not rank_check:
            print('랭크 입력 ex) diamond, platinum, gold, silver, bronze, iron')
            rank = input('랭크를 입력하세요: ').strip().lower()
            print()

        if rank not in ranks:
            rank_check = False
            # print('랭크: diamond, platinum, gold, silver, bronze, iron')
            print('랭크는 다음 값 중에서 입력해주세요(입력값: %s)' % rank)
            continue
        else:
            rank_check = True
        
        if not division_check:
            print('구간입력 ex) 1, 2, 3, 4')
            division = input('구간을 입력하세요: ')
            print()

        if not division.isdigit():
            print('구간은 숫자로 입력해주세요(입력값: %s)' % division)
            division_check = False
            continue

        division = int(division)
        if division < 1 or division > 4:
            print('구간은 1 ~ 4 사이의 값을 입력해 주세요(입력값: %s)' % division)
            division_check = False
            continue
        
        division_check = True

        print('rank: %s, divison: %s' % (rank, division))
        accept = input('원하시는 조건이 맞습니까?[y/n]: ')
        if accept.lower() == 'y':
            input_accept = True
        else:
            rank_check = False
            division_check = False
        print()

    main_logic(rank, division)


def auto_mode():
    ranks = ranks = ['diamond', 'platinum', 'gold', 'silver', 'bronze', 'iron']
    for rank in ranks:
        main_logic(rank, 1)

if __name__ == '__main__':
    # 랭크별로 auto수집
    auto_mode()
    # manual_mode()

    