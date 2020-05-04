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
import logic.Side;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


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
	private static Canvas transitionCanvas;
	private static Point oldRookCastlingPoint;
	private static Point newRookCastlingPoint;
	private static boolean isCastling;
	private static Entity rookEntity;
	private static ArrayList<Point> explosionPointList = new ArrayList<Point>();
	private static boolean isEnPassnt;
	private static Point enPassantPawnPoint;

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

	public static GamePane getGamePane() {
		return gamePane;
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

	public static void startAnimation(Point start, Point end, Entity entity) {
		transitionCanvas = new Canvas();
		transitionCanvas.setWidth(60);
		transitionCanvas.setHeight(60);
		GraphicsContext gc = transitionCanvas.getGraphicsContext2D();
		gc.drawImage(new Image(entity.getSymbol()), 0, 0, 60, 60);

		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setFromX(start.getY() * 60);
		transition.setFromY(start.getX() * 60);
		transition.setToX(end.getY() * 60);
		transition.setToY(end.getX() * 60);
		transition.setNode(transitionCanvas);
		transition.play();
		boardPane.getChildren().add(transitionCanvas);

	}
	
	public static void startCastlingAnimation(Point startKing, Point endKing, Entity king, Point startRook, Point endRook, Entity rook) {
		startAnimation(startKing, endKing, king);
		startAnimation(startRook, endRook, rook);
	}

	public static void removeTransitionCanvas() {
		ObservableList<Node> removedCanvas = FXCollections.observableArrayList();
		for (Node n : boardPane.getChildren()) {
			if (n instanceof Canvas)
				removedCanvas.add(n);
		}
		for (Node n : removedCanvas) {
			boardPane.getChildren().remove(n);
		}
	}

	public static boolean isCastling() {
		return isCastling;
	}

	public static void setCastling(boolean isCastling) {
		AppManager.isCastling = isCastling;
	}
	
	public static void setRookCastlingPoint(Point oldPoint,Point newPoint,Entity rook) {
		oldRookCastlingPoint = oldPoint;
		newRookCastlingPoint = newPoint;
		rookEntity = rook;
		isCastling = true;
	}

	public static Point getOldRookCastlingPoint() {
		return oldRookCastlingPoint;
	}
	
	public static Point getOldRotateRookCastlingPoint() {
		return new Point(7-oldRookCastlingPoint.x,7-oldRookCastlingPoint.y);
	}

	public static Point getNewRookCastlingPoint() {
		return newRookCastlingPoint;
	}
	
	public static Point getNewRotateRookCastlingPoint() {
		return new Point(7-newRookCastlingPoint.x,7-newRookCastlingPoint.y);
	}

	public static Entity getRookEntity() {
		return rookEntity;
	}

	public static void removeExplosionPoint(Point p) {
		explosionPointList.remove(p);
	}
	
	public static void addExplosionPoint(Point p) {
		explosionPointList.add(p);
	}

	public static ArrayList<Point> getExplosionPointList() {
		return explosionPointList;
	}

	public static boolean isEnPassnt() {
		return isEnPassnt;
	}

	public static void setEnPassant(boolean isEnPassnt,Point p) {
		AppManager.isEnPassnt = isEnPassnt;
		enPassantPawnPoint = p;
	}

	public static Point getEnPassantPawnPoint() {
		return enPassantPawnPoint;
	}
	
	
	
}
