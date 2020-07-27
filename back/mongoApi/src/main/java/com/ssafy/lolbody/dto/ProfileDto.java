package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
public class ProfileDto {
	@Id
	private String summonerId;
	private List<ProfileReferenceDto> profiles;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<ProfileReferenceDto> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<ProfileReferenceDto> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "ProfileDto [summonerId=" + summonerId + ", profiles=" + profiles + "]";
	}

}
