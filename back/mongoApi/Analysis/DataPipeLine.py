import requests, json, random, time, os, csv
import pandas as pd

class API_KEYs:
    def __init__(self, arr):
        self.keys = arr
        self.idx = 0
    
    def key(self):
        # time.sleep(1.2/len(self.keys))
        return self.keys[self.idx]

    def key_chage(self):
        if self.idx == len(self.keys) - 1:
            self.idx = 0
        else:
            self.idx += 1

API_DUMMY = [
    'RGAPI-9041c188-f0f9-42ad-8204-',
    'RGAPI-4630b7c8-6bdc-446f-b81e-',
    'RGAPI-ad13b224-7b6f-4358-8ff9-',
    'RGAPI-e1a57063-3429-414b-87f7-',
    'RGAPI-8cbfda0a-c52d-4a75-8aa5-',
    'RGAPI-44c9480b-4f14-4046-ab15-',
    'RGAPI-b91dd012-eb39-41ef-9d5f-',
    'RGAPI-255f85c9-c59c-4754-aee4-',
    'RGAPI-18a6cad9-eb35-4437-96ec-',
    ]

API = API_KEYs(API_DUMMY)
print(1.2/len(API.keys))

tiers = ['DIAMOND', 'PLATINUM', 'GOLD', 'SILVER', 'BRONZE', 'IRON']
ranks = ['', 'I', 'II', 'III', 'IV']
queues = [420, 430, 440, 450]

position_pair = [
    [ 'NONE', 'JUNGLE'],
    [ 'SOLO', 'TOP' ],
    [ 'SOLO', 'MIDDLE' ],
    [ 'DUO_CARRY', 'BOTTOM' ],
    [ 'DUO_SUPPORT', 'BOTTOM' ],
]

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
        'visionScore',                  # 시야점수
        'timeCCingOthers',              # cc기에 맞은 총 시간
        'totalDamageTaken',             # 받은 피해량
        'goldEarned',                   # 총 골드
        'totalMinionsKilled',           # cs
        'neutralMinionsKilled',         # 중립몹 킬수
        # 'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        'totalTimeCrowdControlDealt',   # cc기를 맞춘 총 시간
        'visionWardsBoughtInGame',      # 핑와 구매 개수
        # 'wardsPlaced',                  # 와드 설치수
        # 'wardsKilled',                  # 와드 파괴수
      # 'killsRatio',                   # 킬관여율
      # 'deathsRatio',                  # 데스관여울
    ]

save_stats_list_420 = save_stats_list_450 + [
        'neutralMinionsKilledEnemyJungle', # 상대 정글몹 킬수
        'wardsPlaced',                  # 와드 설치수
        'wardsKilled',                  # 와드 파괴수
    ]



def get_save_stats_list(queue):
    if queue == 420:
        return save_stats_list_420
    else:
        return save_stats_list_450

cors_list = []
for i in range(16):
    cors_list.append('xcor_%s' % i)
    cors_list.append('ycor_%s' % i)


# 랭크와 구간을 정하면 그 랭크에 위치한 유저의 소환사명을 리스트로 반환
def get_summoner_name_list(tier, rank):
    # 입력 데이터 판별 구간
    if type(tier) != type('string'): 
        print('tier가 문자형식이 아닙니다.' + str(type(tier)))
        return None
    tier = tier.upper()
    if tier not in tiers:
        print('tier가 잘못 입력되었습니다.' + tier)
        return None
    if type(rank) != type(123456789):
        print('rank가 숫자형식이 아닙니다.' + str(type(rank)))
    if 1 > rank or rank > 4:
        print('rank가 주어진 범위를 벗어났습니다.' + str(rank))
        return None

    # API URL 구성 구간
    rank = ranks[rank]
    get_summoners_accounts_URL = 'https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/%s/%s' % (tier, rank)

    # API 데이터 받아오는 구간
    success_api_data = False
    limit = 0
    while not success_api_data and limit < 100:
        page = random.choice(range(1, 100))
        summoner_accounts = requests.get(get_summoners_accounts_URL, \
            params={ 'api_key': API.key(), 'page': page }).json()

        # 데이터 전송이 성공한 경우 list type 실패한 경우 dict type
        if type(summoner_accounts) == type([]):
            if len(summoner_accounts) == 0: continue
            summoner_name_list = [v.get('summonerName') for v in summoner_accounts]
            success_api_data = True
            return summoner_name_list
        if type(summoner_accounts) == type({'k':'v'}):
            print(summoner_accounts)
            limit += 1
    return False


# 소환사 닉네임을 입력하면 accountId를 반환함
# input: string
# output: string
def get_account_id(summoner_name):
    summoner_name = str(summoner_name)
    get_sommoner_accounts_URL = 'https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s' % summoner_name
    account_dict = requests.get(get_sommoner_accounts_URL, \
        params={ 'api_key': API.key() }).json()
    # 오류발생시 오류내역 출력 후 None 반환
    if account_dict.get('status') is not None:
        return None
    account_id = account_dict.get('accountId')
    return account_id


# 소환사 id를 입력하면 경기 id를 반환함
# input: string, random factor
# output: string
def get_match_id(account_id, queue=420, season=13):
    account_id = str(account_id)
    get_sommoner_matches_URL = 'https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/%s' % account_id
    matches_dict = requests.get(get_sommoner_matches_URL, \
        params={ 'api_key': API.key(), 'queue': queue, 'season': season }).json()
    if matches_dict.get('status') is not None:
        print(matches_dict.get('status'))
        return None
    matches_list = matches_dict.get('matches')
    if not len(matches_list):
        print('게임 결과가 없습니다.')
        return None
    for match in matches_list:
        if queue == 450:
            return match.get('gameId')
        if [match.get('role'), match.get('lane')] in position_pair:
            return match.get('gameId')
    print('해당하는 데이터가 없습니다')
    return None
#####################################################################################


def get_teams_data(participants, playtime, queue):
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

    # 데이터 정합성 판별 => 포지션이 잘 파악이 되었는가? 420인 경우만
    if queue == 420:
        for idx in range(10):
            if idx < 5:
                team_idx = 0
            else:
                team_idx = 1
            
            # 팀 데이터가 쓰레기로 판별되면 나머지 팀원 데이터도 그냥 넘겨버림 420인 경우만
            if teams_data[team_idx]['isTrash']: continue

            lane = participants[idx].get('timeline').get('lane')
            role = participants[idx].get('timeline').get('role')


            # 정글러 판별(riot 정보 + 강타 스펠)
            if (participants[idx]['spell1Id'] == 11 or participants[idx]['spell2Id'] == 11)\
                and lane == 'JUNGLE' and role == 'NONE':
                teams_data[team_idx]['jungle'] += 1
                # 포지션 데이터 추가
                participants[idx]['timeline']['position'] = 'JUNGLE'

            # 탑라이너 판별
            elif lane == 'TOP' and role == 'SOLO':
                teams_data[team_idx]['top'] += 1
                # 포지션 데이터 추가
                participants[idx]['timeline']['position'] = 'TOP'

            # 미드라이너 판별
            elif lane == 'MIDDLE' and role == 'SOLO':
                teams_data[team_idx]['mid'] += 1
                # 포지션 데이터 추가
                participants[idx]['timeline']['position'] = 'MID'

            # 원딜, 서폿 판별
            elif lane == 'BOTTOM':
                if role == 'DUO_CARRY':
                    teams_data[team_idx]['carry'] += 1
                    # 포지션 데이터 추가
                    participants[idx]['timeline']['position'] = 'BOTTOM'

                elif role == 'DUO_SUPPORT':
                    teams_data[team_idx]['support'] += 1
                    # 포지션 데이터 추가
                    participants[idx]['timeline']['position'] = 'SUPPORT'

                # 쓰레기 데이터
                else:
                    teams_data[team_idx]['isTrash'] = True
            # 쓰레기 데이터
            else:
                teams_data[team_idx]['isTrash'] = True
            participants[idx]['timeline']['playtime'] = playtime
    # 450인경우
    else:
        for idx in range(10):
            participants[idx]['timeline']['position'] = 'NONE'
            participants[idx]['timeline']['playtime'] = playtime

    # team_kda 저장
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1

        # 쓰레기 데이터가 아닌경우 team kda 불러오기 420만
        if teams_data[team_idx]['isTrash'] and queue == 420: continue

        teams_data[team_idx]['kills'] += participants[idx].get('stats').get('kills')
        teams_data[team_idx]['deaths'] += participants[idx].get('stats').get('deaths')
        teams_data[team_idx]['assists'] += participants[idx].get('stats').get('assists')
    return teams_data


def mix(timeline, match):
    # timeline은 list(1분단위), time은 dict, match는 list(유저별로 10개)
    for idx, time in enumerate(timeline):
        if idx > 15: break
        xcor = 'xcor_%s' % idx
        ycor = 'ycor_%s' % idx
        for v in time.get('participantFrames').values():
            if v.get('position') is None: break
            k = int(v.get('participantId')) - 1
            match[k]['stats'][xcor] = v.get('position').get('x')
            match[k]['stats'][ycor] = v.get('position').get('y')
    return


# match id를 입력하면 데이터 값을 반환
# input: int, 또는 숫자형 리터럴
# output: 'lane': [[플레이어 인게임 데이터]...]를 가지는 dict
def get_match_data(match_id, tier, queue):
    if type(match_id) == type(123456789):
        match_id = str(match_id)
    if type(match_id) != type('String'):
        print('올바른 match_id 타입이 아닙니다.')
        return None
    
    get_match_data_URL = 'https://kr.api.riotgames.com/lol/match/v4/matches/%s' % match_id
    get_timeline_data_URL = 'https://kr.api.riotgames.com/lol/match/v4/timelines/by-match/%s' % match_id
    match_data = requests.get(get_match_data_URL,\
        params={ 'api_key': API.key() }).json()
    timeline_data = requests.get(get_timeline_data_URL,\
        params={ 'api_key': API.key() }).json()
        
    # 잘못된 match_id로 요청하는 경우
    if match_data.get('status') is not None:
        print(match_data.get('status'))
        return None
    if timeline_data.get('status') is not None:
        print(match_data.get('status'))
        return None
    if match_data.get('gameDuration') < 300:
        print('다시하기인 매치입니다.')
        return None

    player_in_game_data = []

    timeline_data = timeline_data.get('frames')
    match_participants = match_data.get('participants')

    # timeline 추가
    mix(timeline_data, match_participants)
    match_participantIdentities = match_data.get('participantIdentities')
    teams_data = get_teams_data(match_participants, match_data.get('gameDuration'), queue)

    # 받아온 teams_data로 유저별 인게임 stats 리스트로 저장
    for idx in range(10):
        if idx < 5:
            team_idx = 0
        else:
            team_idx = 1
        
        # 팀데이터가 쓰레기경우 그냥 넘겨버림 420인경우만
        if teams_data[team_idx]['isTrash'] and queue == 420: continue
        if match_participants[idx]['timeline'].get('position') is None: continue

        account_id = match_participantIdentities[idx].get('player').get('accountId')
        summoner_name = match_participantIdentities[idx].get('player').get('summonerName')
        position = match_participants[idx].get('timeline').get('position')
        playtime = match_participants[idx].get('timeline').get('playtime')
        team = (team_idx + 1) * 100
        spell1 = match_participants[idx]['spell1Id']
        spell2 = match_participants[idx]['spell2Id']

        player_in_game_stats = [account_id, summoner_name, tier, match_id, queue, team, position, playtime, spell1, spell2]

        # 플레이어 stats 삽입
        save_stats_list = get_save_stats_list(queue)
        for stats in get_save_stats_list(queue):
            # if stats == 'totalDamageDealtToChampions':
            #     print(match_participants[idx].get('stats')[stats] / (playtime/60))
            if stats == 'totalHeal':
                if match_participants[idx].get('stats').get('totalUnitsHealed') <= 1:
                    player_in_game_stats.append(0)
                    continue
            player_in_game_stats.append(match_participants[idx].get('stats')[stats])
        
        # killsRatio, deathsRatio
        if int(player_in_game_stats[10]) + int(player_in_game_stats[12]) == 0:
            player_in_game_stats.append(0)
        else:
            kills_ratio = int((player_in_game_stats[10]) + int(player_in_game_stats[12])) / teams_data[team_idx]['kills']
            player_in_game_stats.append(kills_ratio)

        if int(player_in_game_stats[11]) == 0:
            player_in_game_stats.append(0)
        else:
            deaths_ratio = int(player_in_game_stats[11]) / int(teams_data[team_idx]['deaths'])
            player_in_game_stats.append(deaths_ratio)

        for cor in cors_list:
            player_in_game_stats.append(match_participants[idx].get('stats').get(cor))
        
        player_in_game_data.append(player_in_game_stats)

    return player_in_game_data


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


def auto_mode():
    now = time.localtime()
    now = "%02d.%02d" % (int(str(now.tm_year)[2:]), now.tm_mon)
    print(now)
    # 폴더 생성
    create_folder('./csv/' + now)
    source = './csv/' + now + '/source/'
    create_folder(source)

    # summoner_list가 있으면 불러오고 아니면 구하기
    summoner_list = get_source_data(source, 'name_account_match_tier')
    print(summoner_list[0])
    if not summoner_list:
        file = open(source + 'name_account_match_tier.csv', 'w', newline='', encoding='utf-8')
        csvfile = csv.writer(file)
        for tier in tiers:
            print(tier)
            name_list = get_summoner_name_list(tier, 3)
            print(len(name_list))
            for idx, name in enumerate(name_list):
                print(idx)
                account = get_account_id(name)
                if account is None: continue
                for queue in [420, 450]:
                    match = get_match_id(account, season=13, queue=queue)
                    if match is None: continue
                    csvfile.writerow([name, account, match, tier, queue])
                API.key_chage()
        summoner_list = get_source_data(source, 'name_account_match_tier')
        print('소스파일 생성 완료')
    #####################################################33
    df = pd.DataFrame(summoner_list, columns=['소환사명', 'account', 'match', 'tier', 'queue'])
    print(df)


    # player_in_game_data_list 작성
    for queue in [420, 450]:
        queue_df = df[df['queue'] == str(queue)]
        player_in_game_data = get_source_data(source, 'player_in_game_data_%s' % queue)
        if not player_in_game_data:
            file = open(source + 'player_in_game_data_%s.csv' % queue, 'w', newline='', encoding='utf-8')
            csvfile = csv.writer(file)
            save_stats_list = get_save_stats_list(queue)
            columns = ['account_id', 'summoner_name', 'tier', 'match_id', 'queue', 'team', 'position', 'playtime', 'spell1', 'spell2'] + save_stats_list + ['killsRatio', 'deathsRatio'] + cors_list
            csvfile.writerow(columns)
            print(len(queue_df))
            for idx, match_id in enumerate(queue_df['match']):
                print(idx)
                tier = queue_df.iloc[idx]['tier']
                # queue = queue_df.iloc[idx]['queue']
                tmp_data = get_match_data(match_id, tier, queue)
                if tmp_data is None: continue
                # player_in_game_data.extend(tmp_data)
                for tmp in tmp_data:
                    csvfile.writerow(tmp)
                if not idx % 20:
                    API.key_chage()
    return


if __name__ == '__main__':
    auto_mode()