package application;

import application.menu.*;

import application.board.*;
import application.console.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import logic.Side;
import Resource.Resource;

public class AppManager {

	private static Stage stage;
	private static Scene scene;
	private static MenuPane menuPane;
	private static SelectModePane selectModePane;
	private static GamePane gamePane;
	private static BoardPane boardPane;
	private static PromotionPane promotionPane;
	private static SettingPane setting;
	private static String gameType;
	private static PlayerStatusDisplay whiteDisplay;
	private static PlayerStatusDisplay blackDisplay;
	private static boolean clickSoundStatus;
	private static boolean rotateStatus;
	private static MediaPlayer bgm = new MediaPlayer(new Media(Resource.GAME_MENU));
	
	
	public static void setStage(Stage stage) {
		AppManager.stage = stage;
	}
	
	public static void setScene(Scene scene) {
		AppManager.scene = scene;
	}
	
	public static void setMenuPane(MenuPane menuPane) {
		AppManager.menuPane = menuPane;
	}

	public static void showMenu() {
		scene.setRoot(menuPane);
		stage.sizeToScene();
	}
	
	public static void hidePromotion() {
		AppManager.promotionPane.hidePromotionPane();
	}
	
	public static void showPromotion() {
		AppManager.promotionPane.showPromotionPane();
	}

	public static void setGamePaneNode() {
		gamePane.getChildren().addAll(boardPane,GamePane.getConsole(boardPane, promotionPane, setting));
	}
	
	public static void showSelectMode() {
		scene.setRoot(selectModePane);
		stage.sizeToScene();
	}
	
	public static void showGamePane() {
		scene.setRoot(gamePane);
		stage.sizeToScene();
	}

	public static void setGamePane(GamePane gamePane) {
		AppManager.gamePane = gamePane;
	}

	public static void setPromotionListener(String text) {
		boardPane.promotion(text);
	}
	
	public static BoardPane getBoardPane() {
		return boardPane;
	}

	public static void setBoardPane(BoardPane boardPane) {
		AppManager.boardPane = boardPane;
	}

	public static PromotionPane getPromotionPane() {
		return promotionPane;
	}

	public static void setPromotionPane(PromotionPane promotionPane) {
		AppManager.promotionPane = promotionPane;
	}

	public static SettingPane getSetting() {
		return setting;
	}

	public static void setSetting(SettingPane setting) {
		AppManager.setting = setting;
	}
	
	public static SelectModePane getSelectModePane() {
		return selectModePane;
	}

	public static void setSelectModePane(SelectModePane selectModePane) {
		AppManager.selectModePane = selectModePane;
	}

	public static void displayMessage(String message) {
		promotionPane.setMessage(message);
	}

	public static String getGameType() {
		return gameType;
	}

	public static void setGameType(String gameType) {
		AppManager.gameType = gameType;
	}
	
	
	public static void rotateBoard() {
		boardPane.rotateBoard();
	}

	public static boolean getClickSoundStatus() {
		return clickSoundStatus;
	}

	public static void setClickSoundStatus(boolean clickSoundStatus) {
		AppManager.clickSoundStatus = clickSoundStatus;
	}
	
	public static boolean getRotateStatus() {
		return rotateStatus;
	}

	public static void setWhiteDisplay(PlayerStatusDisplay whiteDisplay) {
		AppManager.whiteDisplay = whiteDisplay;
	}

	public static void setBlackDisplay(PlayerStatusDisplay blackDisplay) {
		AppManager.blackDisplay = blackDisplay;
	}

	public static PlayerStatusDisplay getStatusDisplay(Side side) {
		if(side == Side.BLACK) return blackDisplay;
		else return whiteDisplay;
		
	}
	
	public static void setRotateStatus(boolean rotateStatus) {
		AppManager.rotateStatus = rotateStatus;
	}

	public static void playMenuBgm() {
		Thread thread = new Thread(() -> {
			try {
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						bgm.play();
						bgm.setCycleCount(-1);
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
	}
	
	
	public static void stopMenuBgm() {
		bgm.stop();
	}
	
	public static void setMenuBgmVolume(double volume) {
		bgm.setVolume(volume);
	}
}
