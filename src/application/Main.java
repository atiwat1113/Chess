package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application{
	public void start(Stage primaryStage) {

		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(400);
		BoardPane boardPane = new BoardPane();
		root.getChildren().add(boardPane);
			
	
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
