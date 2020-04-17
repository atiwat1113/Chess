package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MenuPane extends VBox{
	
	public MenuPane() {
		this.setPrefSize(400, 400);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		
		Label title = new Label("Chess Game");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
		
		MyButton playButton = new MyButton("Play");
		MyButton exitButton = new MyButton("Exit");
		
		setPlayButtonListener(playButton);
		setExitButtonListener(exitButton);
		
		this.getChildren().addAll(title,playButton,exitButton);
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
