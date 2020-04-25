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
		PromotionPane promotionPane = new PromotionPane();
		SettingPane setting = new SettingPane();		
		
		AppManager.setMenuPane(menu);
		AppManager.setSelectModePane(selectMode);
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		AppManager.setClickSoundStatus(true);
		AppManager.setMenuBgmVolume(0.3);
		AppManager.playMenuBgm();
		
	
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
