package application;

import game.base.Games;

public class SelectModeButton extends MyButton{

	private String gameType;
	
	public SelectModeButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		switch(text) {
		case "Normal" :
			this.gameType = Games.NORMAL;
			break;
		case "Atomic" :
			this.gameType = Games.ATOMIC;
			break;
		case "King of the hill" :
			this.gameType = Games.KINGOFTHEHILL;
			break;
		case "Three check" :
			this.gameType = Games.THREECHECK;
			break;
		case "Anti chess" :
			this.gameType = Games.ANTICHESS;
			break;
		case "Chess960" :
			this.gameType = Games.CHESS960;
			break;
		case "Horde" :
			this.gameType = Games.HORDE;
		}
	}

}
