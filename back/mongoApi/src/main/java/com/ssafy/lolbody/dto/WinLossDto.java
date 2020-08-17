package com.ssafy.lolbody.dto;

public class WinLossDto {
	private int win;
	private int loss;

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	@Override
	public String toString() {
		return "WinLossDto [win=" + win + ", loss=" + loss + "]";
	}
}
