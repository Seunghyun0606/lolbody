package com.ssafy.lolbody.dto;

public class LeagueEntryDTO {
	String leagueId;
	String summonerId; // Player's encrypted summonerId.
	String summonerName;
	String queueType;
	String tier;
	String rank;
	int leaguePoints;
	int wins; // Winning team on Summoners Rift.
	int losses; // Losing team on Summoners Rift.
	boolean hotStreak;
	boolean veteran;
	boolean freshBlood;
	boolean inactive;
	
	
}
