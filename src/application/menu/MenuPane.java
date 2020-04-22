package application.menu;

import Resource.Resource;
import application.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class MenuPane extends StackPane{
	
	private static final Image background = new Image(Resource.BACKGROUND);
	private static final Image icon = new Image(Resource.ICON);
	
	public MenuPane() {
		this.setPrefSize(750, 500);
		setBackgroundWithImage();
		
		VBox menu = new VBox();
		menu.setAlignment(Pos.CENTER);
		menu.setSpacing(15);
		menu.setTranslateY(-30);
		
		Canvas title = new Canvas();
		title.setHeight(250);
		title.setWidth(250);
		GraphicsContext gc = title.getGraphicsContext2D();
		setTitleImage(gc);
		
		MyButton playButton = new MyButton("Play");
		MyButton exitButton = new MyButton("Exit");
		
		setPlayButtonListener(playButton);
		setExitButtonListener(exitButton);
		
		menu.getChildren().addAll(title,playButton,exitButton);
		this.getChildren().add(menu);
	}
	
	private void setTitleImage(GraphicsContext gc) {
		gc.drawImage(icon, 0, 0, 250, 250);
	}
	
	private void setBackgroundWithImage() {
		BackgroundSize bgSize = new BackgroundSize(750, 500, false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(background, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgImgA));
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