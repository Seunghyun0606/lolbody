package com.ssafy.lolbody.dto;

public class MultiSearchDto {
	private UserCardReferenceDto userCard;
	private MatchResultDto matchResult;

	public UserCardReferenceDto getUserCard() {
		return userCard;
	}

	public void setUserCard(UserCardReferenceDto userCard) {
		this.userCard = userCard;
	}

	public MatchResultDto getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(MatchResultDto matchResult) {
		this.matchResult = matchResult;
	}

	@Override
	public String toString() {
		return "MultiSearchDto [userCard=" + userCard + ", matchResult=" + matchResult + "]";
	}

}
