package application;

import application.menu.*;
import application.console.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	public void start(Stage primaryStage) {
		
		MenuPane menu = new MenuPane();
		SelectModePane selectMode = new SelectModePane();
		GamePane gamePane = new GamePane();
		PromotionPane promotionPane = new PromotionPane();
		SettingButton setting = new SettingButton();		
		
		AppManager.setMenuPane(menu);
		AppManager.setSelectModePane(selectMode);
		AppManager.setGamePane(gamePane);
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		
	
		Scene scene = new Scene(menu);
		
		AppManager.setScene(scene);
	
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
