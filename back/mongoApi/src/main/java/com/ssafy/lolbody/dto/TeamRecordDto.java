package com.ssafy.lolbody.dto;

import java.util.List;

public class TeamRecordDto {
	private boolean win;
	private int team;
	private List<PlayerRecordDto> teammate;

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public List<PlayerRecordDto> getTeammate() {
		return teammate;
	}

	public void setTeammate(List<PlayerRecordDto> teammate) {
		this.teammate = teammate;
	}

	@Override
	public String toString() {
		return "TeamRecordDto [win=" + win + ", team=" + team + ", teammate=" + teammate + "]";
	}
}
