package com.ssafy.lolbody.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="summoners")
public class SummonerDto {
	@Id
	private String id;
	private String accountId;
	private String puuid;
	private String name;
	private String subName;
	private int profileIconId;
	private long revisionDate;
	private int summonerLevel;
	
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPuuid() {
		return puuid;
	}
	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProfileIconId() {
		return profileIconId;
	}
	public void setProfileIconId(int profileIconId) {
		this.profileIconId = profileIconId;
	}
	public long getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(long revisionDate) {
		this.revisionDate = revisionDate;
	}
	public int getSummonerLevel() {
		return summonerLevel;
	}
	public void setSummonerLevel(int summonerLevel) {
		this.summonerLevel = summonerLevel;
	}
	@Override
	public String toString() {
		return "SummonerDto [id=" + id + ", accountId=" + accountId + ", puuid=" + puuid + ", name=" + name
				+ ", subName=" + subName + ", profileIconId=" + profileIconId + ", revisionDate=" + revisionDate
				+ ", summonerLevel=" + summonerLevel + "]";
	}
}
