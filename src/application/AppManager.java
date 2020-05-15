package application;

import application.board.*;
import application.console.*;
import application.menu.MenuPane;
import application.menu.SelectModePane;
import application.menu.SettingMenu;
import application.menu.TimeSelectPane;
import entity.base.Entity;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GameController;
import logic.Side;
import java.awt.Point;

public class AppManager { // for Classes to communicate with each other.

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
	private static Canvas transitionCanvas;

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

	public static void setPromotionListener(String text) {
		boardPane.promotion(text);
	}

	// display message when player make something wrong
	public static void displayMessage(String message) {
		promotionPane.setMessage(message);
	}

	public static void rotateBoard() {
		boardPane.rotateBoard();
		whiteDisplay.rotateDisplay();
		blackDisplay.rotateDisplay();
		gamePane.rotateStatusDisplay();
	}

	// decorate volume slider in setting menu
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

	public static void stopTimer() {
		try {
			whiteDisplay.stop();
			blackDisplay.stop();
			SoundManager.stopClockTick();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		;
	}

	// play the animation when entity is moving
	public static void startAnimation(Point start, Point end, Entity entity) {
		transitionCanvas = new Canvas();
		TranslateTransition transition = new TranslateTransition();

		if (rotateStatus && GameController.getTurn().equals(Side.BLACK)) {
			start = new Point(7 - start.x, 7 - start.y);
			end = new Point(7 - end.x, 7 - end.y);
		}

		transitionCanvas.setWidth(60);
		transitionCanvas.setHeight(60);
		GraphicsContext gc = transitionCanvas.getGraphicsContext2D();
		gc.drawImage(new Image(entity.getSymbol()), 0, 0, 60, 60);

		transition.setDuration(Duration.seconds(0.3));
		transition.setFromX(start.getY() * 60);
		transition.setFromY(start.getX() * 60);
		transition.setToX(end.getY() * 60);
		transition.setToY(end.getX() * 60);
		transition.setNode(transitionCanvas);
		transition.play();
		boardPane.getChildren().add(transitionCanvas);

	}

	public static void startCastlingAnimation(Point startKing, Point endKing, Entity king, Point startRook, Point endRook,
			Entity rook) {
		startAnimation(startKing, endKing, king);
		startAnimation(startRook, endRook, rook);
	}

	// remove the transition canvas when the animation is end
	public static void removeTransitionCanvas() {
		ObservableList<Node> removedCanvas = FXCollections.observableArrayList();
		for (Node n : boardPane.getChildren()) {
			if (n instanceof Canvas) {
				removedCanvas.add(n);
			}
		}
		for (Node n : removedCanvas) {
			boardPane.getChildren().remove(n);
		}
	}
	
	public static void updateSettingMenu() {
		settingMenu.update();
	}

	// getter and setter
	// ----------------------------------------------------------------------------------------

	public static void setStage(Stage stage) { //used in Main
		AppManager.stage = stage;
	}

	public static void setScene(Scene scene) { //used in Main
		AppManager.scene = scene;
	}

	public static void setMenuPane(MenuPane menuPane) { //used in Main
		AppManager.menuPane = menuPane;
	}

	public static void setGamePane(GamePane gamePane) { //used in TimeSelectButton startGame()
		AppManager.gamePane = gamePane;
	}

	public static GamePane getGamePane() { //used in BoardPane showEndGameWindow()
		return gamePane;
	}

	public static BoardPane getBoardPane() { //used in PlayerStatusDiplay, TimeSelectButton, and SettingPane
		return boardPane;
	}

	public static void setBoardPane(BoardPane boardPane) { //used in SelectModeButton setGame()
		AppManager.boardPane = boardPane;
	}

	public static PromotionPane getPromotionPane() { //used in GamePane inner class Console
		return promotionPane;
	}

	public static void setPromotionPane(PromotionPane promotionPane) { //used in Main
		AppManager.promotionPane = promotionPane;
	}

	public static SettingPane getSetting() { //used in GamePane inner class Console
		return setting;
	}

	public static void setSetting(SettingPane setting) { //used in TimeSelectButton startGame()
		AppManager.setting = setting;
	}

	public static void setSelectModePane(SelectModePane selectModePane) { //used in Main
		AppManager.selectModePane = selectModePane;
	}

	public static void setSettingMenu(SettingMenu settingMenu) { //used in Main
		AppManager.settingMenu = settingMenu;
	}

	public static void setTimeSelectPane(TimeSelectPane timeSelectPane) { //used in Main
		AppManager.timeSelectPane = timeSelectPane;
	}

	public static String getGameType() { //used in BoardCell and GamePane inner class Console
		return gameType;
	}

	public static void setGameType(String gameType) { //used in SelectModeButton setGame()
		AppManager.gameType = gameType;
	}

	public static boolean getRotateStatus() { //used in BoardPane updateBoard()
		return rotateStatus;
	}

	public static int getSpareTime() { //used in PlayerStatusDisplay
		return spareTime;
	}

	public static void setSpareTime(int spareTime) { //used in TimeSelectButton startGame()
		AppManager.spareTime = spareTime;
	}

	public static void setWhiteDisplay(PlayerStatusDisplay whiteDisplay) { //used in GamePane inner class Console
		AppManager.whiteDisplay = whiteDisplay;
	}

	public static void setBlackDisplay(PlayerStatusDisplay blackDisplay) { //used in GamePane inner class Console
		AppManager.blackDisplay = blackDisplay;
	}

	public static PlayerStatusDisplay getStatusDisplay(Side side) { //used in BoardPane, SettingPane, and TimeSelectButton
		if (side == Side.BLACK) {
			return blackDisplay;
		} else {
			return whiteDisplay;
		}

	}

	public static void setRotateStatus(boolean rotateStatus) { //used in SettingPane rotation listener
		AppManager.rotateStatus = rotateStatus;
	}

}
