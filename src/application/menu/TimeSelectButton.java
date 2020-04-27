package application.menu;

import application.AppManager;
import application.console.GamePane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import logic.Side;

public class TimeSelectButton extends MyButton{

	private int spareTime;
	
	public TimeSelectButton(String text,double fontSize) {
		super(text,fontSize);
		switch(text) {
		case "30 Minutes" :
			spareTime = 1800;
			break;
		case "15 Minutes" :
			spareTime = 900;
			break;
		case "5 Minutes" :
			spareTime = 300;
			break;
		case "No time limit" :
			spareTime = 0;
			break;
		default :
		}
		
		setSelectListener();
	}
	
	private void setSelectListener() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				startGame();
			}
		});
	}
	
	private void startGame() {
		AppManager.setSpareTime(spareTime);
		GamePane gamePane = new GamePane(AppManager.getBoardPane());
		AppManager.setGamePane(gamePane);
		AppManager.showGamePane();
		AppManager.getStatusDisplay(Side.WHITE).startTurn();
		AppManager.stopMenuBgm();
	}
}
