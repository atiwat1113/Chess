package application.board;

import application.AppManager;
import application.console.GamePane;
import application.console.PromotionPane;
import application.console.SettingPane;
import application.menu.MenuPane;
import application.menu.SelectModePane;
import application.menu.SettingMenu;
import application.menu.TimeSelectPane;
import game.base.Games;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunBoard extends Application {
	public void start(Stage primaryStage) {
		String gameType = Games.NORMAL;
		Scene scene = null;
		SettingMenu settingMenu = new SettingMenu();
		TimeSelectPane timeSelect = new TimeSelectPane();
		PromotionPane promotionPane = new PromotionPane();
		SettingPane setting = new SettingPane();
		
		AppManager.setRotateStatus(true);
		AppManager.setScene(scene);
		AppManager.setSettingMenu(settingMenu);
		AppManager.setTimeSelectPane(timeSelect);
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		AppManager.setGameType(gameType);
		//AppManager.setGamePane(gamePane);
		AppManager.setBoardPane(new BoardPane(gameType));
		AppManager.setSpareTime(30);
		AppManager.showGamePane();
		primaryStage.setTitle("Chess");
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
