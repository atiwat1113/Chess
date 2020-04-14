package application;

import game.base.Games;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{
	public void start(Stage primaryStage) {

		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(400);
		
		BoardPane boardPane = new BoardPane(Games.NORMAL);//------------------------
		Text turn = boardPane.getTurnText();
		PromotionPane promotionPane = new PromotionPane(boardPane);
		
		VBox console = new VBox();
		console.getChildren().addAll(promotionPane,turn);
		
		root.getChildren().addAll(boardPane,console);
		root.setAlignment(Pos.CENTER);	
		
		AppManager.setPromotionPane(promotionPane);
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
}
