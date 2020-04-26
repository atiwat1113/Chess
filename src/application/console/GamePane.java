package application.console;

import Resource.Resource;
import application.AppManager;
import application.board.*;
import application.console.*;
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

public class GamePane extends HBox{
	
	public GamePane() {
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPadding(new Insets(10));
		this.setPrefHeight(500);
		this.setSpacing(20);	
		this.setAlignment(Pos.CENTER);
	}
	
	public static VBox getConsole(BoardPane boardPane, PromotionPane promotionPane, SettingPane setting) {
		Label gameMode = new Label("Game mode : " + AppManager.getGameType());
		Text turn = boardPane.getTurnText();
		VBox console = new VBox();
		HBox topBox = new HBox();
		
		gameMode.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		gameMode.setTextFill(Color.BLACK);
		
		topBox.getChildren().addAll(promotionPane, setting);
		topBox.setSpacing(10);
		
		console.getChildren().addAll(topBox,gameMode,turn);
		console.setPrefHeight(500);
		console.setSpacing(10);
		return console;
	}
	
}
