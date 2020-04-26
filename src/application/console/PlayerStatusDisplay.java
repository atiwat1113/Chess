package application.console;

import application.AppManager;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.GameController;

public class PlayerStatusDisplay extends VBox{
		
		private Label turn;
		private int timePerTurn;
		private int spareTime;
		private Thread timerThread;
		private Text spareTimeText;
		private Text timePerTurnText;
		
		public PlayerStatusDisplay(String text) {
			turn = new Label(text);
			spareTime = 60;
			timePerTurn = 30;
			spareTimeText = new Text("01:00");
			timePerTurnText = new Text("00:" + timePerTurn);
			
			HBox timePane = new HBox();
			timePane.getChildren().addAll(spareTimeText,timePerTurnText);
			timePane.setSpacing(25);
			
			this.getChildren().addAll(turn,timePane);
		}
		
		public void rotateDisplay() {
			
		}
		
		public void startTurn() {
			turn.setText(GameController.getTurn()+" TURN");
			timerThread = new Thread(() -> {
				while(true) {
					try {
						
						Thread.sleep(1000);
						
						if (timePerTurn == 0) 
							spareTime -= 1;
						else 
							timePerTurn -= 1;
						if (spareTime < 0) {
							Platform.runLater(new Runnable(){
								@Override
								public void run() {
									// TODO Auto-generated method stub
									AppManager.getBoardPane().showEndGameWindow(GameController.getTurn() + " time out!\n" + GameController.getAnotherSide(GameController.getTurn()) + " Win!!!\nReturn to Menu");
								}
							});
							stop();
						}
						
						else 
							update();
					} catch (Exception e) {
						//e.printStackTrace();
						break;
					}
				}
			});
			timerThread.start();
		}
		
		public void endTurn() {
			try {
				stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			turn.setText(GameController.getTurn().toString());
			timePerTurn = 30;
			update();
		}
		
		public void stop() throws Exception {
			// TODO Auto-generated method stub
			this.timerThread.interrupt();
		}
		
		public void update() {
			if(spareTime < 60) {
				if(spareTime >= 10)
					spareTimeText.setText("00:" + spareTime);
				else
					spareTimeText.setText("00:0" + spareTime);
			}
			if(timePerTurn >= 10)
				timePerTurnText.setText("00:" + timePerTurn);
			else
				timePerTurnText.setText("00:0" + timePerTurn);
		}
		
		
	
}
