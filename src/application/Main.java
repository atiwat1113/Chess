package application;

import game.base.Games;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{
	public void start(Stage primaryStage) {
		
		HBox root = new HBox();
		root.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(400);
		BoardPane boardPane = new BoardPane(Games.NORMAL);//------------------------
		PromotionPane promotionPane = new PromotionPane();
		SettingButton setting = new SettingButton();
		root.getChildren().addAll(boardPane,getConsole(boardPane, promotionPane, setting));
		root.setAlignment(Pos.CENTER);	
		
		AppManager.setPromotionPane(promotionPane);
		AppManager.setBoardPane(boardPane);
		//AppManager.setSetting(setting);---------------------------------------------
		AppManager.hidePromotion();
		
	
		Scene scene = new Scene(root);
		
	
		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
		}

	public static void main(String[] args) {
		launch(args);
	}
	private static VBox getConsole(BoardPane boardPane, PromotionPane promotionPane, SettingButton setting) {
		Text turn = boardPane.getTurnText();
		VBox console = new VBox();
		HBox topBox = new HBox();
		topBox.getChildren().addAll(promotionPane, setting);
		console.getChildren().addAll(topBox,turn);
		return console;
	}
}
