package application.console;

import Resource.Resource;
import application.AppManager;
import application.board.*;
import application.console.*;
import application.console.GamePane.Console;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameController;
import logic.Side;

public class GamePane extends HBox{
	
	private Console console;
	
	public GamePane(BoardPane boardPane) {
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPadding(new Insets(10));
		this.setPrefHeight(500);
		this.setSpacing(20);	
		this.setAlignment(Pos.CENTER);		
		
		console = new Console();
		
		this.getChildren().addAll(boardPane,console);
	}
	
	class Console extends VBox {
		public Console() {
			Label gameMode = new Label("Game mode : " + AppManager.getGameType());
			HBox topBox = new HBox();
			PlayerStatusDisplay whiteDisplay = new PlayerStatusDisplay(Side.WHITE);
			PlayerStatusDisplay blackDisplay = new PlayerStatusDisplay(Side.BLACK);
			
			whiteDisplay.rotateDisplay();
			
			AppManager.setWhiteDisplay(whiteDisplay);
			AppManager.setBlackDisplay(blackDisplay);
			
			gameMode.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
			gameMode.setTextFill(Color.BLACK);
			
			topBox.getChildren().addAll(AppManager.getPromotionPane(), AppManager.getSetting());
			topBox.setSpacing(10);
			
			this.getChildren().addAll(topBox,gameMode,blackDisplay,whiteDisplay);
			this.setPrefHeight(500);
			this.setAlignment(Pos.TOP_CENTER);
			this.setSpacing(30);
		}
		
		public void rotateStatusDisplay() {
			this.getChildren().add(this.getChildren().remove(2));
			//System.out.println("rotate");
		}
	}
	
	public void rotateStatusDisplay() {
		console.rotateStatusDisplay();
	}
}
