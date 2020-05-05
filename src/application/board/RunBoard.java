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
import logic.Side;

public class RunBoard extends Application {
	public void start(Stage primaryStage) {
		String gameType = Games.ATOMIC;
		PromotionPane promotionPane = new PromotionPane();
		SettingPane setting = new SettingPane();

		
		AppManager.setRotateStatus(false);
	
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		AppManager.setGameType(gameType);
		//AppManager.setGamePane(gamePane);
		AppManager.setBoardPane(new BoardPane(gameType));
		GamePane gamePane = new GamePane(AppManager.getBoardPane());
		AppManager.setGamePane(gamePane);
		AppManager.setSpareTime(0);
		Scene scene = new Scene(AppManager.getGamePane());
		AppManager.setScene(scene);
		AppManager.showGamePane();
		AppManager.getStatusDisplay(Side.WHITE).startTurn();
		
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
