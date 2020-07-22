package com.ssafy.lolbody.dto;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class MatchDto {
	@Id
	private long gameId;
	private List<ParticipantIdentityDto> participantIdentities;
	private int queueId;
	private String gameType;
	private long gameDuration;
	private List<TeamStatsDto> teams;
	private String platformId;
	private long gameCreation;
	private int seasonId;
	private String gameVersion;
	private int mapId;
	private String gameMode;
	private List<ParticipantDto> participants;

	public static class ParticipantIdentityDto {
		private int participantId;
		private PlayerDto player;

		public int getParticipantId() {
			return participantId;
		}

		public void setParticipantId(int participantId) {
			this.participantId = participantId;
		}

		public PlayerDto getPlayer() {
			return player;
		}

		public void setPlayer(PlayerDto player) {
			this.player = player;
		}

		@Override
		public String toString() {
			return "ParticipantIdentityDto [participantId=" + participantId + ", player=" + player + "]";
		}
	}

	public static class PlayerDto {
		private int profileIcon;
		private String accountId;
		private String matchHistoryUri;
		private String currentAccountId;
		private String currentPlatformId;
		private String summonerName;
		private String summonerId;
		private String platformId;

		public int getProfileIcon() {
			return profileIcon;
		}

		public void setProfileIcon(int profileIcon) {
			this.profileIcon = profileIcon;
		}

		public String getAccountId() {
			return accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public String getMatchHistoryUri() {
			return matchHistoryUri;
		}

		public void setMatchHistoryUri(String matchHistoryUri) {
			this.matchHistoryUri = matchHistoryUri;
		}

		public String getCurrentAccountId() {
			return currentAccountId;
		}

		public void setCurrentAccountId(String currentAccountId) {
			this.currentAccountId = currentAccountId;
		}

		public String getCurrentPlatformId() {
			return currentPlatformId;
		}

		public void setCurrentPlatformId(String currentPlatformId) {
			this.currentPlatformId = currentPlatformId;
		}

		public String getSummonerName() {
			return summonerName;
		}

		public void setSummonerName(String summonerName) {
			this.summonerName = summonerName;
		}

		public String getSummonerId() {
			return summonerId;
		}

		public void setSummonerId(String summonerId) {
			this.summonerId = summonerId;
		}

		public String getPlatformId() {
			return platformId;
		}

		public void setPlatformId(String platformId) {
			this.platformId = platformId;
		}

		@Override
		public String toString() {
			return "PlayerDto [profileIcon=" + profileIcon + ", accountId=" + accountId + ", matchHistoryUri="
					+ matchHistoryUri + ", currentAccountId=" + currentAccountId + ", currentPlatformId="
					+ currentPlatformId + ", summonerName=" + summonerName + ", summonerId=" + summonerId
					+ ", platformId=" + platformId + "]";
		}
	}

	public static class TeamStatsDto {
		private int towerKills;
		private int riftHeraldKills;
		private boolean firstBlood;
		private int inhibitorKills;
		private List<TeamBansDto> bans;
		private boolean firstBaron;
		private boolean firstDragon;
		private int dominionVictoryScore;
		private int dragonKills;
		private int baronKills;
		private boolean firstInhibitor;
		private boolean firstTower;
		private int vilemawKills;
		private boolean firstRiftHerald;
		private int teamId;
		private String win;

		public int getTowerKills() {
			return towerKills;
		}

		public void setTowerKills(int towerKills) {
			this.towerKills = towerKills;
		}

		public int getRiftHeraldKills() {
			return riftHeraldKills;
		}

		public void setRiftHeraldKills(int riftHeraldKills) {
			this.riftHeraldKills = riftHeraldKills;
		}

		public boolean isFirstBlood() {
			return firstBlood;
		}

		public void setFirstBlood(boolean firstBlood) {
			this.firstBlood = firstBlood;
		}

		public int getInhibitorKills() {
			return inhibitorKills;
		}

		public void setInhibitorKills(int inhibitorKills) {
			this.inhibitorKills = inhibitorKills;
		}

		public List<TeamBansDto> getBans() {
			return bans;
		}

		public void setBans(List<TeamBansDto> bans) {
			this.bans = bans;
		}

		public boolean isFirstBaron() {
			return firstBaron;
		}

		public void setFirstBaron(boolean firstBaron) {
			this.firstBaron = firstBaron;
		}

		public boolean isFirstDragon() {
			return firstDragon;
		}

		public void setFirstDragon(boolean firstDragon) {
			this.firstDragon = firstDragon;
		}

		public int getDominionVictoryScore() {
			return dominionVictoryScore;
		}

		public void setDominionVictoryScore(int dominionVictoryScore) {
			this.dominionVictoryScore = dominionVictoryScore;
		}

		public int getDragonKills() {
			return dragonKills;
		}

		public void setDragonKills(int dragonKills) {
			this.dragonKills = dragonKills;
		}

		public int getBaronKills() {
			return baronKills;
		}

		public void setBaronKills(int baronKills) {
			this.baronKills = baronKills;
		}

		public boolean isFirstInhibitor() {
			return firstInhibitor;
		}

		public void setFirstInhibitor(boolean firstInhibitor) {
			this.firstInhibitor = firstInhibitor;
		}

		public boolean isFirstTower() {
			return firstTower;
		}

		public void setFirstTower(boolean firstTower) {
			this.firstTower = firstTower;
		}

		public int getVilemawKills() {
			return vilemawKills;
		}

		public void setVilemawKills(int vilemawKills) {
			this.vilemawKills = vilemawKills;
		}

		public boolean isFirstRiftHerald() {
			return firstRiftHerald;
		}

		public void setFirstRiftHerald(boolean firstRiftHerald) {
			this.firstRiftHerald = firstRiftHerald;
		}

		public int getTeamId() {
			return teamId;
		}

		public void setTeamId(int teamId) {
			this.teamId = teamId;
		}

		public String getWin() {
			return win;
		}

		public void setWin(String win) {
			this.win = win;
		}

		@Override
		public String toString() {
			return "TeamStatsDto [towerKills=" + towerKills + ", riftHeraldKills=" + riftHeraldKills + ", firstBlood="
					+ firstBlood + ", inhibitorKills=" + inhibitorKills + ", bans=" + bans + ", firstBaron="
					+ firstBaron + ", firstDragon=" + firstDragon + ", dominionVictoryScore=" + dominionVictoryScore
					+ ", dragonKills=" + dragonKills + ", baronKills=" + baronKills + ", firstInhibitor="
					+ firstInhibitor + ", firstTower=" + firstTower + ", vilemawKills=" + vilemawKills
					+ ", firstRiftHerald=" + firstRiftHerald + ", teamId=" + teamId + ", win=" + win + "]";
		}
	}

	public static class TeamBansDto {
		private int championId;
		private int pickTurn;

		public int getChampionId() {
			return championId;
		}

		public void setChampionId(int championId) {
			this.championId = championId;
		}

		public int getPickTurn() {
			return pickTurn;
		}

		public void setPickTurn(int pickTurn) {
			this.pickTurn = pickTurn;
		}

		@Override
		public String toString() {
			return "TeamBansDto [championId=" + championId + ", pickTurn=" + pickTurn + "]";
		}
	}

	public static class ParticipantDto {
		private int participantId;
		private int championId;
		private List<RuneDto> runes;
		private ParticipantStatsDto stats;
		private int teamId;
		private ParticipantTimelineDto timeline;
		private int spell1Id;
		private int spell2Id;
		private String highestAchievedSeasonTier;
		private List<MasteryDto> masteries;

		public int getParticipantId() {
			return participantId;
		}

		public void setParticipantId(int participantId) {
			this.participantId = participantId;
		}

		public int getChampionId() {
			return championId;
		}

		public void setChampionId(int championId) {
			this.championId = championId;
		}

		public List<RuneDto> getRunes() {
			return runes;
		}

		public void setRunes(List<RuneDto> runes) {
			this.runes = runes;
		}

		public ParticipantStatsDto getStats() {
			return stats;
		}

		public void setStats(ParticipantStatsDto stats) {
			this.stats = stats;
		}

		public int getTeamId() {
			return teamId;
		}

		public void setTeamId(int teamId) {
			this.teamId = teamId;
		}

		public ParticipantTimelineDto getTimeline() {
			return timeline;
		}

		public void setTimeline(ParticipantTimelineDto timeline) {
			this.timeline = timeline;
		}

		public int getSpell1Id() {
			return spell1Id;
		}

		public void setSpell1Id(int spell1Id) {
			this.spell1Id = spell1Id;
		}

		public int getSpell2Id() {
			return spell2Id;
		}

		public void setSpell2Id(int spell2Id) {
			this.spell2Id = spell2Id;
		}

		public String getHighestAchievedSeasonTier() {
			return highestAchievedSeasonTier;
		}

		public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
			this.highestAchievedSeasonTier = highestAchievedSeasonTier;
		}

		public List<MasteryDto> getMasteries() {
			return masteries;
		}

		public void setMasteries(List<MasteryDto> masteries) {
			this.masteries = masteries;
		}

		@Override
		public String toString() {
			return "ParticipantDto [participantId=" + participantId + ", championId=" + championId + ", runes=" + runes
					+ ", stats=" + stats + ", teamId=" + teamId + ", timeline=" + timeline + ", spell1Id=" + spell1Id
					+ ", spell2Id=" + spell2Id + ", highestAchievedSeasonTier=" + highestAchievedSeasonTier
					+ ", masteries=" + masteries + "]";
		}
	}

	public static class RuneDto {
		private int runeId;
		private int rank;

		public int getRuneId() {
			return runeId;
		}

		public void setRuneId(int runeId) {
			this.runeId = runeId;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public String toString() {
			return "RuneDto [runeId=" + runeId + ", rank=" + rank + "]";
		}
	}

	public static class ParticipantStatsDto {
		private int item0;
		private int item2;
		private int totalUnitsHealed;
		private int item1;
		private int largestMultiKill;
		private int goldEarned;
		private boolean firstInhibitorKill;
		private long physicalDamageTaken;
		private int nodeNeutralizeAssist;
		private int totalPlayerScore;
		private int champLevel;
		private long damageDealtToObjectives;
		private long totalDamageTaken;
		private int neutralMinionsKilled;
		private int deaths;
		private int tripleKills;
		private long magicDamageDealtToChampions;
		private int wardsKilled;
		private int pentaKills;
		private long damageSelfMitigated;
		private int largestCriticalStrike;
		private int nodeNeutralize;
		private int totalTimeCrowdControlDealt;
		private boolean firstTowerKill;
		private long magicDamageDealt;
		private int totalScoreRank;
		private int nodeCapture;
		private int wardsPlaced;
		private long totalDamageDealt;
		private long timeCCingOthers;
		private long magicalDamageTaken;
		private int largestKillingSpree;
		private long totalDamageDealtToChampions;
		private long physicalDamageDealtToChampions;
		private int neutralMinionsKilledTeamJungle;
		private int totalMinionsKilled;
		private boolean firstInhibitorAssist;
		private int visionWardsBoughtInGame;
		private int objectivePlayerScore;
		private int kills;
		private boolean firstTowerAssist;
		private int combatPlayerScore;
		private int inhibitorKills;
		private int turretKills;
		private int participantId;
		private long trueDamageTaken;
		private boolean firstBloodAssist;
		private int nodeCaptureAssist;
		private int assists;
		private int teamObjective;
		private int altarsNeutralized;
		private int goldSpent;
		private long damageDealtToTurrets;
		private int altarsCaptured;
		private boolean win;
		private long totalHeal;
		private int unrealKills;
		private long visionScore;
		private long physicalDamageDealt;
		private boolean firstBloodKill;
		private int longestTimeSpentLiving;
		private int killingSprees;
		private int sightWardsBoughtInGame;
		private long trueDamageDealtToChampions;
		private int neutralMinionsKilledEnemyJungle;
		private int doubleKills;
		private long trueDamageDealt;
		private int quadraKills;
		private int item4;
		private int item3;
		private int item6;
		private int item5;
		private int playerScore0;
		private int playerScore1;
		private int playerScore2;
		private int playerScore3;
		private int playerScore4;
		private int playerScore5;
		private int playerScore6;
		private int playerScore7;
		private int playerScore8;
		private int playerScore9;
		private int perk0;
		private int perk0Var1;
		private int perk0Var2;
		private int perk0Var3;
		private int perk1;
		private int perk1Var1;
		private int perk1Var2;
		private int perk1Var3;
		private int perk2;
		private int perk2Var1;
		private int perk2Var2;
		private int perk2Var3;
		private int perk3;
		private int perk3Var1;
		private int perk3Var2;
		private int perk3Var3;
		private int perk4;
		private int perk4Var1;
		private int perk4Var2;
		private int perk4Var3;
		private int perk5;
		private int perk5Var1;
		private int perk5Var2;
		private int perk5Var3;
		private int perkPrimaryStyle;
		private int perkSubStyle;

		public int getItem0() {
			return item0;
		}

		public void setItem0(int item0) {
			this.item0 = item0;
		}

		public int getItem2() {
			return item2;
		}

		public void setItem2(int item2) {
			this.item2 = item2;
		}

		public int getTotalUnitsHealed() {
			return totalUnitsHealed;
		}

		public void setTotalUnitsHealed(int totalUnitsHealed) {
			this.totalUnitsHealed = totalUnitsHealed;
		}

		public int getItem1() {
			return item1;
		}

		public void setItem1(int item1) {
			this.item1 = item1;
		}

		public int getLargestMultiKill() {
			return largestMultiKill;
		}

		public void setLargestMultiKill(int largestMultiKill) {
			this.largestMultiKill = largestMultiKill;
		}

		public int getGoldEarned() {
			return goldEarned;
		}

		public void setGoldEarned(int goldEarned) {
			this.goldEarned = goldEarned;
		}

		public boolean isFirstInhibitorKill() {
			return firstInhibitorKill;
		}

		public void setFirstInhibitorKill(boolean firstInhibitorKill) {
			this.firstInhibitorKill = firstInhibitorKill;
		}

		public long getPhysicalDamageTaken() {
			return physicalDamageTaken;
		}

		public void setPhysicalDamageTaken(long physicalDamageTaken) {
			this.physicalDamageTaken = physicalDamageTaken;
		}

		public int getNodeNeutralizeAssist() {
			return nodeNeutralizeAssist;
		}

		public void setNodeNeutralizeAssist(int nodeNeutralizeAssist) {
			this.nodeNeutralizeAssist = nodeNeutralizeAssist;
		}

		public int getTotalPlayerScore() {
			return totalPlayerScore;
		}

		public void setTotalPlayerScore(int totalPlayerScore) {
			this.totalPlayerScore = totalPlayerScore;
		}

		public int getChampLevel() {
			return champLevel;
		}

		public void setChampLevel(int champLevel) {
			this.champLevel = champLevel;
		}

		public long getDamageDealtToObjectives() {
			return damageDealtToObjectives;
		}

		public void setDamageDealtToObjectives(long damageDealtToObjectives) {
			this.damageDealtToObjectives = damageDealtToObjectives;
		}

		public long getTotalDamageTaken() {
			return totalDamageTaken;
		}

		public void setTotalDamageTaken(long totalDamageTaken) {
			this.totalDamageTaken = totalDamageTaken;
		}

		public int getNeutralMinionsKilled() {
			return neutralMinionsKilled;
		}

		public void setNeutralMinionsKilled(int neutralMinionsKilled) {
			this.neutralMinionsKilled = neutralMinionsKilled;
		}

		public int getDeaths() {
			return deaths;
		}

		public void setDeaths(int deaths) {
			this.deaths = deaths;
		}

		public int getTripleKills() {
			return tripleKills;
		}

		public void setTripleKills(int tripleKills) {
			this.tripleKills = tripleKills;
		}

		public long getMagicDamageDealtToChampions() {
			return magicDamageDealtToChampions;
		}

		public void setMagicDamageDealtToChampions(long magicDamageDealtToChampions) {
			this.magicDamageDealtToChampions = magicDamageDealtToChampions;
		}

		public int getWardsKilled() {
			return wardsKilled;
		}

		public void setWardsKilled(int wardsKilled) {
			this.wardsKilled = wardsKilled;
		}

		public int getPentaKills() {
			return pentaKills;
		}

		public void setPentaKills(int pentaKills) {
			this.pentaKills = pentaKills;
		}

		public long getDamageSelfMitigated() {
			return damageSelfMitigated;
		}

		public void setDamageSelfMitigated(long damageSelfMitigated) {
			this.damageSelfMitigated = damageSelfMitigated;
		}

		public int getLargestCriticalStrike() {
			return largestCriticalStrike;
		}

		public void setLargestCriticalStrike(int largestCriticalStrike) {
			this.largestCriticalStrike = largestCriticalStrike;
		}

		public int getNodeNeutralize() {
			return nodeNeutralize;
		}

		public void setNodeNeutralize(int nodeNeutralize) {
			this.nodeNeutralize = nodeNeutralize;
		}

		public int getTotalTimeCrowdControlDealt() {
			return totalTimeCrowdControlDealt;
		}

		public void setTotalTimeCrowdControlDealt(int totalTimeCrowdControlDealt) {
			this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
		}

		public boolean isFirstTowerKill() {
			return firstTowerKill;
		}

		public void setFirstTowerKill(boolean firstTowerKill) {
			this.firstTowerKill = firstTowerKill;
		}

		public long getMagicDamageDealt() {
			return magicDamageDealt;
		}

		public void setMagicDamageDealt(long magicDamageDealt) {
			this.magicDamageDealt = magicDamageDealt;
		}

		public int getTotalScoreRank() {
			return totalScoreRank;
		}

		public void setTotalScoreRank(int totalScoreRank) {
			this.totalScoreRank = totalScoreRank;
		}

		public int getNodeCapture() {
			return nodeCapture;
		}

		public void setNodeCapture(int nodeCapture) {
			this.nodeCapture = nodeCapture;
		}

		public int getWardsPlaced() {
			return wardsPlaced;
		}

		public void setWardsPlaced(int wardsPlaced) {
			this.wardsPlaced = wardsPlaced;
		}

		public long getTotalDamageDealt() {
			return totalDamageDealt;
		}

		public void setTotalDamageDealt(long totalDamageDealt) {
			this.totalDamageDealt = totalDamageDealt;
		}

		public long getTimeCCingOthers() {
			return timeCCingOthers;
		}

		public void setTimeCCingOthers(long timeCCingOthers) {
			this.timeCCingOthers = timeCCingOthers;
		}

		public long getMagicalDamageTaken() {
			return magicalDamageTaken;
		}

		public void setMagicalDamageTaken(long magicalDamageTaken) {
			this.magicalDamageTaken = magicalDamageTaken;
		}

		public int getLargestKillingSpree() {
			return largestKillingSpree;
		}

		public void setLargestKillingSpree(int largestKillingSpree) {
			this.largestKillingSpree = largestKillingSpree;
		}

		public long getTotalDamageDealtToChampions() {
			return totalDamageDealtToChampions;
		}

		public void setTotalDamageDealtToChampions(long totalDamageDealtToChampions) {
			this.totalDamageDealtToChampions = totalDamageDealtToChampions;
		}

		public long getPhysicalDamageDealtToChampions() {
			return physicalDamageDealtToChampions;
		}

		public void setPhysicalDamageDealtToChampions(long physicalDamageDealtToChampions) {
			this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
		}

		public int getNeutralMinionsKilledTeamJungle() {
			return neutralMinionsKilledTeamJungle;
		}

		public void setNeutralMinionsKilledTeamJungle(int neutralMinionsKilledTeamJungle) {
			this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
		}

		public int getTotalMinionsKilled() {
			return totalMinionsKilled;
		}

		public void setTotalMinionsKilled(int totalMinionsKilled) {
			this.totalMinionsKilled = totalMinionsKilled;
		}

		public boolean isFirstInhibitorAssist() {
			return firstInhibitorAssist;
		}

		public void setFirstInhibitorAssist(boolean firstInhibitorAssist) {
			this.firstInhibitorAssist = firstInhibitorAssist;
		}

		public int getVisionWardsBoughtInGame() {
			return visionWardsBoughtInGame;
		}

		public void setVisionWardsBoughtInGame(int visionWardsBoughtInGame) {
			this.visionWardsBoughtInGame = visionWardsBoughtInGame;
		}

		public int getObjectivePlayerScore() {
			return objectivePlayerScore;
		}

		public void setObjectivePlayerScore(int objectivePlayerScore) {
			this.objectivePlayerScore = objectivePlayerScore;
		}

		public int getKills() {
			return kills;
		}

		public void setKills(int kills) {
			this.kills = kills;
		}

		public boolean isFirstTowerAssist() {
			return firstTowerAssist;
		}

		public void setFirstTowerAssist(boolean firstTowerAssist) {
			this.firstTowerAssist = firstTowerAssist;
		}

		public int getCombatPlayerScore() {
			return combatPlayerScore;
		}

		public void setCombatPlayerScore(int combatPlayerScore) {
			this.combatPlayerScore = combatPlayerScore;
		}

		public int getInhibitorKills() {
			return inhibitorKills;
		}

		public void setInhibitorKills(int inhibitorKills) {
			this.inhibitorKills = inhibitorKills;
		}

		public int getTurretKills() {
			return turretKills;
		}

		public void setTurretKills(int turretKills) {
			this.turretKills = turretKills;
		}

		public int getParticipantId() {
			return participantId;
		}

		public void setParticipantId(int participantId) {
			this.participantId = participantId;
		}

		public long getTrueDamageTaken() {
			return trueDamageTaken;
		}

		public void setTrueDamageTaken(long trueDamageTaken) {
			this.trueDamageTaken = trueDamageTaken;
		}

		public boolean isFirstBloodAssist() {
			return firstBloodAssist;
		}

		public void setFirstBloodAssist(boolean firstBloodAssist) {
			this.firstBloodAssist = firstBloodAssist;
		}

		public int getNodeCaptureAssist() {
			return nodeCaptureAssist;
		}

		public void setNodeCaptureAssist(int nodeCaptureAssist) {
			this.nodeCaptureAssist = nodeCaptureAssist;
		}

		public int getAssists() {
			return assists;
		}

		public void setAssists(int assists) {
			this.assists = assists;
		}

		public int getTeamObjective() {
			return teamObjective;
		}

		public void setTeamObjective(int teamObjective) {
			this.teamObjective = teamObjective;
		}

		public int getAltarsNeutralized() {
			return altarsNeutralized;
		}

		public void setAltarsNeutralized(int altarsNeutralized) {
			this.altarsNeutralized = altarsNeutralized;
		}

		public int getGoldSpent() {
			return goldSpent;
		}

		public void setGoldSpent(int goldSpent) {
			this.goldSpent = goldSpent;
		}

		public long getDamageDealtToTurrets() {
			return damageDealtToTurrets;
		}

		public void setDamageDealtToTurrets(long damageDealtToTurrets) {
			this.damageDealtToTurrets = damageDealtToTurrets;
		}

		public int getAltarsCaptured() {
			return altarsCaptured;
		}

		public void setAltarsCaptured(int altarsCaptured) {
			this.altarsCaptured = altarsCaptured;
		}

		public boolean isWin() {
			return win;
		}

		public void setWin(boolean win) {
			this.win = win;
		}

		public long getTotalHeal() {
			return totalHeal;
		}

		public void setTotalHeal(long totalHeal) {
			this.totalHeal = totalHeal;
		}

		public int getUnrealKills() {
			return unrealKills;
		}

		public void setUnrealKills(int unrealKills) {
			this.unrealKills = unrealKills;
		}

		public long getVisionScore() {
			return visionScore;
		}

		public void setVisionScore(long visionScore) {
			this.visionScore = visionScore;
		}

		public long getPhysicalDamageDealt() {
			return physicalDamageDealt;
		}

		public void setPhysicalDamageDealt(long physicalDamageDealt) {
			this.physicalDamageDealt = physicalDamageDealt;
		}

		public boolean isFirstBloodKill() {
			return firstBloodKill;
		}

		public void setFirstBloodKill(boolean firstBloodKill) {
			this.firstBloodKill = firstBloodKill;
		}

		public int getLongestTimeSpentLiving() {
			return longestTimeSpentLiving;
		}

		public void setLongestTimeSpentLiving(int longestTimeSpentLiving) {
			this.longestTimeSpentLiving = longestTimeSpentLiving;
		}

		public int getKillingSprees() {
			return killingSprees;
		}

		public void setKillingSprees(int killingSprees) {
			this.killingSprees = killingSprees;
		}

		public int getSightWardsBoughtInGame() {
			return sightWardsBoughtInGame;
		}

		public void setSightWardsBoughtInGame(int sightWardsBoughtInGame) {
			this.sightWardsBoughtInGame = sightWardsBoughtInGame;
		}

		public long getTrueDamageDealtToChampions() {
			return trueDamageDealtToChampions;
		}

		public void setTrueDamageDealtToChampions(long trueDamageDealtToChampions) {
			this.trueDamageDealtToChampions = trueDamageDealtToChampions;
		}

		public int getNeutralMinionsKilledEnemyJungle() {
			return neutralMinionsKilledEnemyJungle;
		}

		public void setNeutralMinionsKilledEnemyJungle(int neutralMinionsKilledEnemyJungle) {
			this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
		}

		public int getDoubleKills() {
			return doubleKills;
		}

		public void setDoubleKills(int doubleKills) {
			this.doubleKills = doubleKills;
		}

		public long getTrueDamageDealt() {
			return trueDamageDealt;
		}

		public void setTrueDamageDealt(long trueDamageDealt) {
			this.trueDamageDealt = trueDamageDealt;
		}

		public int getQuadraKills() {
			return quadraKills;
		}

		public void setQuadraKills(int quadraKills) {
			this.quadraKills = quadraKills;
		}

		public int getItem4() {
			return item4;
		}

		public void setItem4(int item4) {
			this.item4 = item4;
		}

		public int getItem3() {
			return item3;
		}

		public void setItem3(int item3) {
			this.item3 = item3;
		}

		public int getItem6() {
			return item6;
		}

		public void setItem6(int item6) {
			this.item6 = item6;
		}

		public int getItem5() {
			return item5;
		}

		public void setItem5(int item5) {
			this.item5 = item5;
		}

		public int getPlayerScore0() {
			return playerScore0;
		}

		public void setPlayerScore0(int playerScore0) {
			this.playerScore0 = playerScore0;
		}

		public int getPlayerScore1() {
			return playerScore1;
		}

		public void setPlayerScore1(int playerScore1) {
			this.playerScore1 = playerScore1;
		}

		public int getPlayerScore2() {
			return playerScore2;
		}

		public void setPlayerScore2(int playerScore2) {
			this.playerScore2 = playerScore2;
		}

		public int getPlayerScore3() {
			return playerScore3;
		}

		public void setPlayerScore3(int playerScore3) {
			this.playerScore3 = playerScore3;
		}

		public int getPlayerScore4() {
			return playerScore4;
		}

		public void setPlayerScore4(int playerScore4) {
			this.playerScore4 = playerScore4;
		}

		public int getPlayerScore5() {
			return playerScore5;
		}

		public void setPlayerScore5(int playerScore5) {
			this.playerScore5 = playerScore5;
		}

		public int getPlayerScore6() {
			return playerScore6;
		}

		public void setPlayerScore6(int playerScore6) {
			this.playerScore6 = playerScore6;
		}

		public int getPlayerScore7() {
			return playerScore7;
		}

		public void setPlayerScore7(int playerScore7) {
			this.playerScore7 = playerScore7;
		}

		public int getPlayerScore8() {
			return playerScore8;
		}

		public void setPlayerScore8(int playerScore8) {
			this.playerScore8 = playerScore8;
		}

		public int getPlayerScore9() {
			return playerScore9;
		}

		public void setPlayerScore9(int playerScore9) {
			this.playerScore9 = playerScore9;
		}

		public int getPerk0() {
			return perk0;
		}

		public void setPerk0(int perk0) {
			this.perk0 = perk0;
		}

		public int getPerk0Var1() {
			return perk0Var1;
		}

		public void setPerk0Var1(int perk0Var1) {
			this.perk0Var1 = perk0Var1;
		}

		public int getPerk0Var2() {
			return perk0Var2;
		}

		public void setPerk0Var2(int perk0Var2) {
			this.perk0Var2 = perk0Var2;
		}

		public int getPerk0Var3() {
			return perk0Var3;
		}

		public void setPerk0Var3(int perk0Var3) {
			this.perk0Var3 = perk0Var3;
		}

		public int getPerk1() {
			return perk1;
		}

		public void setPerk1(int perk1) {
			this.perk1 = perk1;
		}

		public int getPerk1Var1() {
			return perk1Var1;
		}

		public void setPerk1Var1(int perk1Var1) {
			this.perk1Var1 = perk1Var1;
		}

		public int getPerk1Var2() {
			return perk1Var2;
		}

		public void setPerk1Var2(int perk1Var2) {
			this.perk1Var2 = perk1Var2;
		}

		public int getPerk1Var3() {
			return perk1Var3;
		}

		public void setPerk1Var3(int perk1Var3) {
			this.perk1Var3 = perk1Var3;
		}

		public int getPerk2() {
			return perk2;
		}

		public void setPerk2(int perk2) {
			this.perk2 = perk2;
		}

		public int getPerk2Var1() {
			return perk2Var1;
		}

		public void setPerk2Var1(int perk2Var1) {
			this.perk2Var1 = perk2Var1;
		}

		public int getPerk2Var2() {
			return perk2Var2;
		}

		public void setPerk2Var2(int perk2Var2) {
			this.perk2Var2 = perk2Var2;
		}

		public int getPerk2Var3() {
			return perk2Var3;
		}

		public void setPerk2Var3(int perk2Var3) {
			this.perk2Var3 = perk2Var3;
		}

		public int getPerk3() {
			return perk3;
		}

		public void setPerk3(int perk3) {
			this.perk3 = perk3;
		}

		public int getPerk3Var1() {
			return perk3Var1;
		}

		public void setPerk3Var1(int perk3Var1) {
			this.perk3Var1 = perk3Var1;
		}

		public int getPerk3Var2() {
			return perk3Var2;
		}

		public void setPerk3Var2(int perk3Var2) {
			this.perk3Var2 = perk3Var2;
		}

		public int getPerk3Var3() {
			return perk3Var3;
		}

		public void setPerk3Var3(int perk3Var3) {
			this.perk3Var3 = perk3Var3;
		}

		public int getPerk4() {
			return perk4;
		}

		public void setPerk4(int perk4) {
			this.perk4 = perk4;
		}

		public int getPerk4Var1() {
			return perk4Var1;
		}

		public void setPerk4Var1(int perk4Var1) {
			this.perk4Var1 = perk4Var1;
		}

		public int getPerk4Var2() {
			return perk4Var2;
		}

		public void setPerk4Var2(int perk4Var2) {
			this.perk4Var2 = perk4Var2;
		}

		public int getPerk4Var3() {
			return perk4Var3;
		}

		public void setPerk4Var3(int perk4Var3) {
			this.perk4Var3 = perk4Var3;
		}

		public int getPerk5() {
			return perk5;
		}

		public void setPerk5(int perk5) {
			this.perk5 = perk5;
		}

		public int getPerk5Var1() {
			return perk5Var1;
		}

		public void setPerk5Var1(int perk5Var1) {
			this.perk5Var1 = perk5Var1;
		}

		public int getPerk5Var2() {
			return perk5Var2;
		}

		public void setPerk5Var2(int perk5Var2) {
			this.perk5Var2 = perk5Var2;
		}

		public int getPerk5Var3() {
			return perk5Var3;
		}

		public void setPerk5Var3(int perk5Var3) {
			this.perk5Var3 = perk5Var3;
		}

		public int getPerkPrimaryStyle() {
			return perkPrimaryStyle;
		}

		public void setPerkPrimaryStyle(int perkPrimaryStyle) {
			this.perkPrimaryStyle = perkPrimaryStyle;
		}

		public int getPerkSubStyle() {
			return perkSubStyle;
		}

		public void setPerkSubStyle(int perkSubStyle) {
			this.perkSubStyle = perkSubStyle;
		}

		@Override
		public String toString() {
			return "ParticipantStatsDto [item0=" + item0 + ", item2=" + item2 + ", totalUnitsHealed=" + totalUnitsHealed
					+ ", item1=" + item1 + ", largestMultiKill=" + largestMultiKill + ", goldEarned=" + goldEarned
					+ ", firstInhibitorKill=" + firstInhibitorKill + ", physicalDamageTaken=" + physicalDamageTaken
					+ ", nodeNeutralizeAssist=" + nodeNeutralizeAssist + ", totalPlayerScore=" + totalPlayerScore
					+ ", champLevel=" + champLevel + ", damageDealtToObjectives=" + damageDealtToObjectives
					+ ", totalDamageTaken=" + totalDamageTaken + ", neutralMinionsKilled=" + neutralMinionsKilled
					+ ", deaths=" + deaths + ", tripleKills=" + tripleKills + ", magicDamageDealtToChampions="
					+ magicDamageDealtToChampions + ", wardsKilled=" + wardsKilled + ", pentaKills=" + pentaKills
					+ ", damageSelfMitigated=" + damageSelfMitigated + ", largestCriticalStrike="
					+ largestCriticalStrike + ", nodeNeutralize=" + nodeNeutralize + ", totalTimeCrowdControlDealt="
					+ totalTimeCrowdControlDealt + ", firstTowerKill=" + firstTowerKill + ", magicDamageDealt="
					+ magicDamageDealt + ", totalScoreRank=" + totalScoreRank + ", nodeCapture=" + nodeCapture
					+ ", wardsPlaced=" + wardsPlaced + ", totalDamageDealt=" + totalDamageDealt + ", timeCCingOthers="
					+ timeCCingOthers + ", magicalDamageTaken=" + magicalDamageTaken + ", largestKillingSpree="
					+ largestKillingSpree + ", totalDamageDealtToChampions=" + totalDamageDealtToChampions
					+ ", physicalDamageDealtToChampions=" + physicalDamageDealtToChampions
					+ ", neutralMinionsKilledTeamJungle=" + neutralMinionsKilledTeamJungle + ", totalMinionsKilled="
					+ totalMinionsKilled + ", firstInhibitorAssist=" + firstInhibitorAssist
					+ ", visionWardsBoughtInGame=" + visionWardsBoughtInGame + ", objectivePlayerScore="
					+ objectivePlayerScore + ", kills=" + kills + ", firstTowerAssist=" + firstTowerAssist
					+ ", combatPlayerScore=" + combatPlayerScore + ", inhibitorKills=" + inhibitorKills
					+ ", turretKills=" + turretKills + ", participantId=" + participantId + ", trueDamageTaken="
					+ trueDamageTaken + ", firstBloodAssist=" + firstBloodAssist + ", nodeCaptureAssist="
					+ nodeCaptureAssist + ", assists=" + assists + ", teamObjective=" + teamObjective
					+ ", altarsNeutralized=" + altarsNeutralized + ", goldSpent=" + goldSpent
					+ ", damageDealtToTurrets=" + damageDealtToTurrets + ", altarsCaptured=" + altarsCaptured + ", win="
					+ win + ", totalHeal=" + totalHeal + ", unrealKills=" + unrealKills + ", visionScore=" + visionScore
					+ ", physicalDamageDealt=" + physicalDamageDealt + ", firstBloodKill=" + firstBloodKill
					+ ", longestTimeSpentLiving=" + longestTimeSpentLiving + ", killingSprees=" + killingSprees
					+ ", sightWardsBoughtInGame=" + sightWardsBoughtInGame + ", trueDamageDealtToChampions="
					+ trueDamageDealtToChampions + ", neutralMinionsKilledEnemyJungle="
					+ neutralMinionsKilledEnemyJungle + ", doubleKills=" + doubleKills + ", trueDamageDealt="
					+ trueDamageDealt + ", quadraKills=" + quadraKills + ", item4=" + item4 + ", item3=" + item3
					+ ", item6=" + item6 + ", item5=" + item5 + ", playerScore0=" + playerScore0 + ", playerScore1="
					+ playerScore1 + ", playerScore2=" + playerScore2 + ", playerScore3=" + playerScore3
					+ ", playerScore4=" + playerScore4 + ", playerScore5=" + playerScore5 + ", playerScore6="
					+ playerScore6 + ", playerScore7=" + playerScore7 + ", playerScore8=" + playerScore8
					+ ", playerScore9=" + playerScore9 + ", perk0=" + perk0 + ", perk0Var1=" + perk0Var1
					+ ", perk0Var2=" + perk0Var2 + ", perk0Var3=" + perk0Var3 + ", perk1=" + perk1 + ", perk1Var1="
					+ perk1Var1 + ", perk1Var2=" + perk1Var2 + ", perk1Var3=" + perk1Var3 + ", perk2=" + perk2
					+ ", perk2Var1=" + perk2Var1 + ", perk2Var2=" + perk2Var2 + ", perk2Var3=" + perk2Var3 + ", perk3="
					+ perk3 + ", perk3Var1=" + perk3Var1 + ", perk3Var2=" + perk3Var2 + ", perk3Var3=" + perk3Var3
					+ ", perk4=" + perk4 + ", perk4Var1=" + perk4Var1 + ", perk4Var2=" + perk4Var2 + ", perk4Var3="
					+ perk4Var3 + ", perk5=" + perk5 + ", perk5Var1=" + perk5Var1 + ", perk5Var2=" + perk5Var2
					+ ", perk5Var3=" + perk5Var3 + ", perkPrimaryStyle=" + perkPrimaryStyle + ", perkSubStyle="
					+ perkSubStyle + "]";
		}
	}

	public static class ParticipantTimelineDto {
		private int participantId;
		private Map<String, Double> csDiffPerMinDeltas;
		private Map<String, Double> damageTakenPerMinDeltas;
		private String role;
		private Map<String, Double> damageTakenDiffPerMinDeltas;
		private Map<String, Double> xpPerMinDeltas;
		private Map<String, Double> xpDiffPerMinDeltas;
		private String lane;
		private Map<String, Double> creepsPerMinDeltas;
		private Map<String, Double> goldPerMinDeltas;

		public int getParticipantId() {
			return participantId;
		}

		public void setParticipantId(int participantId) {
			this.participantId = participantId;
		}

		public Map<String, Double> getCsDiffPerMinDeltas() {
			return csDiffPerMinDeltas;
		}

		public void setCsDiffPerMinDeltas(Map<String, Double> csDiffPerMinDeltas) {
			this.csDiffPerMinDeltas = csDiffPerMinDeltas;
		}

		public Map<String, Double> getDamageTakenPerMinDeltas() {
			return damageTakenPerMinDeltas;
		}

		public void setDamageTakenPerMinDeltas(Map<String, Double> damageTakenPerMinDeltas) {
			this.damageTakenPerMinDeltas = damageTakenPerMinDeltas;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public Map<String, Double> getDamageTakenDiffPerMinDeltas() {
			return damageTakenDiffPerMinDeltas;
		}

		public void setDamageTakenDiffPerMinDeltas(Map<String, Double> damageTakenDiffPerMinDeltas) {
			this.damageTakenDiffPerMinDeltas = damageTakenDiffPerMinDeltas;
		}

		public Map<String, Double> getXpPerMinDeltas() {
			return xpPerMinDeltas;
		}

		public void setXpPerMinDeltas(Map<String, Double> xpPerMinDeltas) {
			this.xpPerMinDeltas = xpPerMinDeltas;
		}

		public Map<String, Double> getXpDiffPerMinDeltas() {
			return xpDiffPerMinDeltas;
		}

		public void setXpDiffPerMinDeltas(Map<String, Double> xpDiffPerMinDeltas) {
			this.xpDiffPerMinDeltas = xpDiffPerMinDeltas;
		}

		public String getLane() {
			return lane;
		}

		public void setLane(String lane) {
			this.lane = lane;
		}

		public Map<String, Double> getCreepsPerMinDeltas() {
			return creepsPerMinDeltas;
		}

		public void setCreepsPerMinDeltas(Map<String, Double> creepsPerMinDeltas) {
			this.creepsPerMinDeltas = creepsPerMinDeltas;
		}

		public Map<String, Double> getGoldPerMinDeltas() {
			return goldPerMinDeltas;
		}

		public void setGoldPerMinDeltas(Map<String, Double> goldPerMinDeltas) {
			this.goldPerMinDeltas = goldPerMinDeltas;
		}

		@Override
		public String toString() {
			return "ParticipantTimelineDto [participantId=" + participantId + ", csDiffPerMinDeltas="
					+ csDiffPerMinDeltas + ", damageTakenPerMinDeltas=" + damageTakenPerMinDeltas + ", role=" + role
					+ ", damageTakenDiffPerMinDeltas=" + damageTakenDiffPerMinDeltas + ", xpPerMinDeltas="
					+ xpPerMinDeltas + ", xpDiffPerMinDeltas=" + xpDiffPerMinDeltas + ", lane=" + lane
					+ ", creepsPerMinDeltas=" + creepsPerMinDeltas + ", goldPerMinDeltas=" + goldPerMinDeltas + "]";
		}
	}

	public static class MasteryDto {
		private int rank;
		private int masteryId;

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		public int getMasteryId() {
			return masteryId;
		}

		public void setMasteryId(int masteryId) {
			this.masteryId = masteryId;
		}

		@Override
		public String toString() {
			return "MasteryDto [rank=" + rank + ", masteryId=" + masteryId + "]";
		}
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public List<ParticipantIdentityDto> getParticipantIdentities() {
		return participantIdentities;
	}

	public void setParticipantIdentities(List<ParticipantIdentityDto> participantIdentities) {
		this.participantIdentities = participantIdentities;
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public long getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}

	public List<TeamStatsDto> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamStatsDto> teams) {
		this.teams = teams;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public long getGameCreation() {
		return gameCreation;
	}

	public void setGameCreation(long gameCreation) {
		this.gameCreation = gameCreation;
	}

	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public List<ParticipantDto> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantDto> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "MatchDto [gameId=" + gameId + ", participantIdentities=" + participantIdentities + ", queueId="
				+ queueId + ", gameType=" + gameType + ", gameDuration=" + gameDuration + ", teams=" + teams
				+ ", platformId=" + platformId + ", gameCreation=" + gameCreation + ", seasonId=" + seasonId
				+ ", gameVersion=" + gameVersion + ", mapId=" + mapId + ", gameMode=" + gameMode + ", participants="
				+ participants + "]";
	}
}
