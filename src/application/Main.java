package application;

import application.menu.*;
import application.console.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage primaryStage) {

		SoundManager.setMenuBgmVolume(0.5);
		SoundManager.setSoundEffectVolume(0.5);
		
		MenuPane menu = new MenuPane();
		SelectModePane selectMode = new SelectModePane();
		SettingMenu settingMenu = new SettingMenu();
		TimeSelectPane timeSelect = new TimeSelectPane();
		PromotionPane promotionPane = new PromotionPane();
		SettingPane setting = new SettingPane();

		AppManager.setMenuPane(menu); 
		AppManager.setSelectModePane(selectMode);
		AppManager.setSettingMenu(settingMenu);
		AppManager.setTimeSelectPane(timeSelect);
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		SoundManager.setSoundEffectStatus(true);
		SoundManager.playMenuBgm();

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
