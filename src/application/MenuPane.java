package application;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class MenuPane extends VBox{
	
	public MenuPane() {
		MyButton playButton = new MyButton("Play");
		MyButton exitButton = new MyButton("Exit");
		
		setPlayButtonListener(playButton);
		setExitButtonListener(exitButton);
	}
	
	private void setExitButtonListener(MyButton exitButton) {
		exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exit");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to exit?");
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						System.exit(0);
					}
				});
			}
		});

	}
	
	private void setPlayButtonListener(MyButton playButton) {
		playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AppManager.showSelectMode();
			}
		});
	}
}
