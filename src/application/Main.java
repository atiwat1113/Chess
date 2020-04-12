package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{
	public void start(Stage primaryStage) {

		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(400);
		BoardPane boardPane = new BoardPane();
		Text turn = boardPane.getTurnText();
		root.getChildren().addAll(turn,boardPane);
		root.setAlignment(Pos.CENTER);	
	
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
