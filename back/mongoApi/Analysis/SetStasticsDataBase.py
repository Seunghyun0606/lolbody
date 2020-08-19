import pymongo, csv
import pandas as pd

connection = pymongo.MongoClient('mongodb://lolbody:fhfqkelchlrhdi3%232%401!@13.125.220.135:27017/test?authSource=test&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false')

stastics_db = connection.test.stastics
# print(stastics_db)

## 420 데이터 저장
tiers = ['DIAMOND', 'PLATINUM', 'GOLD', 'SILVER', 'BRONZE', 'IRON', 'TOTAL']
positions = ['TOP', 'MID', 'JUNGLE', 'SUPPORT', 'BOTTOM', 'TOTAL']
stastics_df = pd.read_csv('./Analysis/csv/20.08/stastics/stastics_420.csv')
# print(list(stastics_df))

save_list = [
    'killsPerMinMean', 'killsPerMinStd', 'deathsPerMinMean', 'deathsPerMinStd', 'assistsPerMinMean', 'assistsPerMinStd', 'totalDamageDealtToChampionsPerMinMean', 'totalDamageDealtToChampionsPerMinStd', 'totalHealPerMinMean', 'totalHealPerMinStd', 'totalUnitsHealedPerMinMean', 'totalUnitsHealedPerMinStd', 'damageSelfMitigatedPerMinMean', 'damageSelfMitigatedPerMinStd', 'damageDealtToObjectivesPerMinMean', 'damageDealtToObjectivesPerMinStd', 'damageDealtToTurretsPerMinMean', 'damageDealtToTurretsPerMinStd', 'visionScorePerMinMean', 'visionScorePerMinStd', 'timeCCingOthersPerMinMean', 'timeCCingOthersPerMinStd', 'totalDamageTakenPerMinMean', 'totalDamageTakenPerMinStd', 'goldEarnedPerMinMean', 'goldEarnedPerMinStd', 'totalMinionsKilledPerMinMean', 'totalMinionsKilledPerMinStd', 'neutralMinionsKilledPerMinMean', 'neutralMinionsKilledPerMinStd', 'totalTimeCrowdControlDealtPerMinMean', 'totalTimeCrowdControlDealtPerMinStd', 'visionWardsBoughtInGamePerMinMean', 'visionWardsBoughtInGamePerMinStd', 'killsRatioMean', 'killsRatioStd', 'deathsRatioMean', 'deathsRatioStd', 'killAssistPerMinMean', 'killAssistPerMinStd', 'neutralMinionsKilledEnemyJunglePerMinMean', 'neutralMinionsKilledEnemyJunglePerMinStd', 'wardsPlacedPerMinMean', 'wardsPlacedPerMinStd', 'wardsKilledPerMinMean', 'wardsKilledPerMinStd',
]

save_dict = {
    'DIAMOND': {},
    'PLATINUM': {},
    'GOLD': {},
    'SILVER': {},
    'BRONZE': {},
    'IRON': {},
    'TOTAL': {},
}

for tier in tiers:
    line_dict = {
            'TOP': {},
            'MID': {},
            'JUNGLE': {},
            'SUPPORT': {},
            'BOTTOM': {},
            'TOTAL': {},
        }
    for position in positions:
        tmp_df = stastics_df[(stastics_df['tier'] == tier) & (stastics_df['position'] == position)].reset_index(drop=True)
        in_data = {
            'mean': {},
            'std': {}
        }
        for stats in save_list:
            if 'Mean' in stats:
                in_data['mean'][stats[:-4]] = tmp_df[stats][0]
            elif 'Std' in stats:
                in_data['std'][stats[:-3]] = tmp_df[stats][0]
        line_dict[position] = in_data.copy()
    save_dict[tier] = line_dict.copy()

stastics_db.update({'_id': 420}, {'$set': {'stastics': save_dict}})

stastics_df = pd.read_csv('./Analysis/csv/20.08/stastics/stastics_450.csv')
save_list = [
    'killsPerMinMean', 'killsPerMinStd', 'deathsPerMinMean', 'deathsPerMinStd', 'assistsPerMinMean', 'assistsPerMinStd', 'totalDamageDealtToChampionsPerMinMean', 'totalDamageDealtToChampionsPerMinStd', 'totalHealPerMinMean', 'totalHealPerMinStd', 'totalUnitsHealedPerMinMean', 'totalUnitsHealedPerMinStd', 'damageSelfMitigatedPerMinMean', 'damageSelfMitigatedPerMinStd', 'damageDealtToObjectivesPerMinMean', 'damageDealtToObjectivesPerMinStd', 'damageDealtToTurretsPerMinMean', 'damageDealtToTurretsPerMinStd', 'visionScorePerMinMean', 'visionScorePerMinStd', 'timeCCingOthersPerMinMean', 'timeCCingOthersPerMinStd', 'totalDamageTakenPerMinMean', 'totalDamageTakenPerMinStd', 'goldEarnedPerMinMean', 'goldEarnedPerMinStd', 'totalMinionsKilledPerMinMean', 'totalMinionsKilledPerMinStd', 'neutralMinionsKilledPerMinMean', 'neutralMinionsKilledPerMinStd', 'totalTimeCrowdControlDealtPerMinMean', 'totalTimeCrowdControlDealtPerMinStd', 'visionWardsBoughtInGamePerMinMean', 'visionWardsBoughtInGamePerMinStd', 'killsRatioMean', 'killsRatioStd', 'deathsRatioMean', 'deathsRatioStd', 'killAssistPerMinMean', 'killAssistPerMinStd'
]

save_dict = {
    'DIAMOND': {},
    'PLATINUM': {},
    'GOLD': {},
    'SILVER': {},
    'BRONZE': {},
    'IRON': {},
    'TOTAL': {},
}

for tier in tiers:
    line_dict = {
        'NONE': {}
    }
    tmp_df = stastics_df[(stastics_df['tier'] == tier) & (stastics_df['position'] == 'NONE')].reset_index(drop=True)
    in_data = {
        'mean': {},
        'std': {}
    }
    for stats in save_list:
        if 'Mean' in stats:
            in_data['mean'][stats[:-4]] = tmp_df[stats][0]
        elif 'Std' in stats:
            in_data['std'][stats[:-3]] = tmp_df[stats][0]
    line_dict['NONE'] = in_data.copy()
    save_dict[tier] = line_dict.copy()

# print(save_dict)
stastics_db.update({'_id': 450}, {'$set': {'stastics': save_dict}})
