package application.menu;

import application.*;
import game.base.Games;
import application.board.*;
import application.console.GamePane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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
		case "Chess960" :
			this.gameType = Games.CHESS960;
			break;
		case "Horde" :
			this.gameType = Games.HORDE;
			break;
		default :
		}
		
		setListener();
	}

	private void setListener() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				startGame();
			}
		});
	}
	
	private void startGame() {
		this.playClickingSound();
		GamePane gamePane = new GamePane();
		AppManager.setGamePane(gamePane);
		AppManager.setGameType(gameType);
		AppManager.setBoardPane(new BoardPane(gameType));
		AppManager.setGamePaneNode();
		AppManager.showGamePane();
		AppManager.stopMenuBgm();
	}
}
