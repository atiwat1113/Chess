package application;

import application.board.*;
import application.console.*;
import application.menu.MenuPane;
import application.menu.SelectModePane;
import application.menu.SettingMenu;
import application.menu.TimeSelectPane;
import entity.base.Entity;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Side;

import java.awt.Point;

import Resource.Resource;

public class AppManager {

	private static Stage stage;
	private static Scene scene;
	private static MenuPane menuPane;
	private static SettingMenu settingMenu;
	private static SelectModePane selectModePane;
	private static TimeSelectPane timeSelectPane;
	private static GamePane gamePane;
	private static BoardPane boardPane;
	private static PromotionPane promotionPane;
	private static SettingPane setting;
	private static String gameType;
	private static int spareTime;
	private static PlayerStatusDisplay whiteDisplay;
	private static PlayerStatusDisplay blackDisplay;
	private static boolean rotateStatus;
	private static Canvas canvas;

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

	public static void showSettingMenu() {
		scene.setRoot(settingMenu);
		stage.sizeToScene();
	}

	public static void showSelectMode() {
		scene.setRoot(selectModePane);
		stage.sizeToScene();
	}

	public static void showTimeSelect() {
		scene.setRoot(timeSelectPane);
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

	public static void setSelectModePane(SelectModePane selectModePane) {
		AppManager.selectModePane = selectModePane;
	}

	public static void setSettingMenu(SettingMenu settingMenu) {
		AppManager.settingMenu = settingMenu;
	}

	public static void setTimeSelectPane(TimeSelectPane timeSelectPane) {
		AppManager.timeSelectPane = timeSelectPane;
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
		whiteDisplay.rotateDisplay();
		blackDisplay.rotateDisplay();
		gamePane.rotateStatusDisplay();
	}

	public static boolean getRotateStatus() {
		return rotateStatus;
	}

	public static int getSpareTime() {
		return spareTime;
	}

	public static void setSpareTime(int spareTime) {
		AppManager.spareTime = spareTime;
	}

	public static void setWhiteDisplay(PlayerStatusDisplay whiteDisplay) {
		AppManager.whiteDisplay = whiteDisplay;
	}

	public static void setBlackDisplay(PlayerStatusDisplay blackDisplay) {
		AppManager.blackDisplay = blackDisplay;
	}

	public static PlayerStatusDisplay getStatusDisplay(Side side) {
		if (side == Side.BLACK)
			return blackDisplay;
		else
			return whiteDisplay;

	}

	public static void setRotateStatus(boolean rotateStatus) {
		AppManager.rotateStatus = rotateStatus;
	}

	public static void stopTimer() {
		try {
			whiteDisplay.stop();
			blackDisplay.stop();
			SoundManager.stopClockTick();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		;
	}

	public static void setSliderStyle() {
		settingMenu.getBgmSlider().lookup(".slider").setStyle("-fx-pref-width:300;");
		settingMenu.getBgmSlider().lookup(".track")
				.setStyle(String.format(
						"-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);" + "-fx-pref-height:10;",
						(int) settingMenu.getBgmSlider().getValue(), (int) settingMenu.getBgmSlider().getValue()));
		settingMenu.getBgmSlider().lookup(".thumb").setStyle("-fx-pref-height: 30;" + "-fx-prefer-width: 5;");

		settingMenu.getSfxSlider().lookup(".slider").setStyle("-fx-pref-width:300;");
		settingMenu.getSfxSlider().lookup(".track")
				.setStyle(String.format(
						"-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);" + "-fx-pref-height:10;",
						(int) settingMenu.getSfxSlider().getValue(), (int) settingMenu.getSfxSlider().getValue()));
		settingMenu.getSfxSlider().lookup(".thumb").setStyle("-fx-pref-height: 30;" + "-fx-prefer-width: 5;");
	}

	public static void moveAnimation(Point start, Point end, Entity entity) {

		canvas = new Canvas();
		canvas.setWidth(60);
		canvas.setHeight(60);
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//SoundManager.playWrongSelected();
				AppManager.displayMessage("It's not your piece.");
				removeCanvas();
			}
		});
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(new Image(entity.getSymbol()), 0, 0, 60, 60);

		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setFromX(start.getY() * 60);
		transition.setFromY(start.getX() * 60);
		transition.setToX(end.getY() * 60);
		transition.setToY(end.getX() * 60);
		transition.setNode(canvas);
		transition.play();
		boardPane.getChildren().add(canvas);
		

	}

	public static void removeCanvas() {
		if (boardPane.getChildren().contains(canvas))
			boardPane.getChildren().remove(canvas);
	}
}
