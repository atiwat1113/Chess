package application;

import application.menu.*;

import application.board.*;
import application.console.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GameController;
import logic.Side;
import Resource.Resource;

public class AppManager {

	private static Stage stage;
	private static Scene scene;
	private static MenuPane menuPane;
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
	private static boolean clickSoundStatus;
	private static boolean rotateStatus;
	private static MediaPlayer clickingSound = new MediaPlayer(new Media(Resource.BUTTON_CLICK));
	private static MediaPlayer bgm = new MediaPlayer(new Media(Resource.GAME_MENU));
	private static MediaPlayer clockTick = new MediaPlayer(new Media(Resource.CLOCK_TICKING));
	private static MediaPlayer entitySelected = new MediaPlayer(new Media(Resource.ENTITY_SELECTED));
	private static MediaPlayer wrongSelected = new MediaPlayer(new Media(Resource.WRONG_SELECTED));
	private static MediaPlayer promotionSound = new MediaPlayer(new Media(Resource.PROMOTION_SOUND));

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

	public static boolean getClickSoundStatus() {
		return clickSoundStatus;
	}

	public static void setClickSoundStatus(boolean clickSoundStatus) {
		AppManager.clickSoundStatus = clickSoundStatus;
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

	public static void playClickingSound() {
		if (AppManager.getClickSoundStatus()) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							clickingSound.play();
							//clickingSound = new MediaPlayer(sound);
							clickingSound.seek(new Duration(0));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}
	
	public static void playEntitySelected() {
		if (AppManager.getClickSoundStatus()) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							entitySelected.play();
							//clickingSound = new MediaPlayer(sound);
							entitySelected.seek(new Duration(0));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}
	
	public static void playWrongSelected() {
		if (AppManager.getClickSoundStatus()) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							wrongSelected.play();
							//clickingSound = new MediaPlayer(sound);
							wrongSelected.seek(new Duration(0));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}
	
	public static void playPromotionSound() {
		if (AppManager.getClickSoundStatus()) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							promotionSound.play();
							//clickingSound = new MediaPlayer(sound);
							promotionSound.seek(new Duration(0));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}
	
	public static void playMenuBgm() {
		bgm.setCycleCount(-1);
		Thread thread = new Thread(() -> {
			try {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						bgm.play();
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
	
	public static void playClockTick() {
		clockTick.setCycleCount(-1);
		Thread thread = new Thread(() -> {
			try {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						clockTick.play();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
	}
	
	public static void stopClockTick() {
		if(clockTick.getStatus().equals(MediaPlayer.Status.PLAYING))
			clockTick.stop();
	}
	
	public static void stopTimer() {
		try {
			whiteDisplay.stop();
			blackDisplay.stop();
			stopClockTick();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		};
	}
	
	public static void setSoundEffectVolume(double volume) {
		clockTick.setVolume(volume);
		clickingSound.setVolume(volume);
		entitySelected.setVolume(volume*1.25);
		wrongSelected.setVolume(volume*0.70);
		promotionSound.setVolume(volume*0.25);
	}
}
