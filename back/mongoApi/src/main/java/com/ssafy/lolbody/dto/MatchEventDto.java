package com.ssafy.lolbody.dto;

import java.util.List;

public class MatchEventDto {
	private String laneType;
	private int skillSlot;
	private String ascendedType;
	private int creatorId;
	private int afterId;
	private String eventType;
	private String type;
	private String levelUpType;
	private String wardType;
	private int participantId;
	private String towerType;
	private int itemId;
	private int beforeId;
	private String pointCaptured;
	private String monsterType;
	private String monsterSubType;
	private int teamId;
	private MatchPositionDto position;
	private int killerId;
	private long timestamp;
	private List<Integer> assistingParticipantIds;
	private String buildingType;
	private int victimId;
	public String getLaneType() {
		return laneType;
	}
	public void setLaneType(String laneType) {
		this.laneType = laneType;
	}
	public int getSkillSlot() {
		return skillSlot;
	}
	public void setSkillSlot(int skillSlot) {
		this.skillSlot = skillSlot;
	}
	public String getAscendedType() {
		return ascendedType;
	}
	public void setAscendedType(String ascendedType) {
		this.ascendedType = ascendedType;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getAfterId() {
		return afterId;
	}
	public void setAfterId(int afterId) {
		this.afterId = afterId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevelUpType() {
		return levelUpType;
	}
	public void setLevelUpType(String levelUpType) {
		this.levelUpType = levelUpType;
	}
	public String getWardType() {
		return wardType;
	}
	public void setWardType(String wardType) {
		this.wardType = wardType;
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public String getTowerType() {
		return towerType;
	}
	public void setTowerType(String towerType) {
		this.towerType = towerType;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getBeforeId() {
		return beforeId;
	}
	public void setBeforeId(int beforeId) {
		this.beforeId = beforeId;
	}
	public String getPointCaptured() {
		return pointCaptured;
	}
	public void setPointCaptured(String pointCaptured) {
		this.pointCaptured = pointCaptured;
	}
	public String getMonsterType() {
		return monsterType;
	}
	public void setMonsterType(String monsterType) {
		this.monsterType = monsterType;
	}
	public String getMonsterSubType() {
		return monsterSubType;
	}
	public void setMonsterSubType(String monsterSubType) {
		this.monsterSubType = monsterSubType;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public MatchPositionDto getPosition() {
		return position;
	}
	public void setPosition(MatchPositionDto position) {
		this.position = position;
	}
	public int getKillerId() {
		return killerId;
	}
	public void setKillerId(int killerId) {
		this.killerId = killerId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public List<Integer> getAssistingParticipantIds() {
		return assistingParticipantIds;
	}
	public void setAssistingParticipantIds(List<Integer> assistingParticipantIds) {
		this.assistingParticipantIds = assistingParticipantIds;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	public int getVictimId() {
		return victimId;
	}
	public void setVictimId(int victimId) {
		this.victimId = victimId;
	}
	@Override
	public String toString() {
		return "MatchEventDto [laneType=" + laneType + ", skillSlot=" + skillSlot + ", ascendedType=" + ascendedType
				+ ", creatorId=" + creatorId + ", afterId=" + afterId + ", eventType=" + eventType + ", type=" + type
				+ ", levelUpType=" + levelUpType + ", wardType=" + wardType + ", participantId=" + participantId
				+ ", towerType=" + towerType + ", itemId=" + itemId + ", beforeId=" + beforeId + ", pointCaptured="
				+ pointCaptured + ", monsterType=" + monsterType + ", monsterSubType=" + monsterSubType + ", teamId="
				+ teamId + ", position=" + position + ", killerId=" + killerId + ", timestamp=" + timestamp
				+ ", assistingParticipantIds=" + assistingParticipantIds + ", buildingType=" + buildingType
				+ ", victimId=" + victimId + "]";
	}
}
