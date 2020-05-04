package application;

import Resource.Resource;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {

//	private static MediaPlayer clickingSound = new MediaPlayer(new Media(Resource.BUTTON_CLICK));
//	private static MediaPlayer bgm = new MediaPlayer(new Media(Resource.GAME_MENU));
//	private static MediaPlayer clockTick = new MediaPlayer(new Media(Resource.CLOCK_TICKING));
//	private static MediaPlayer wrongSelected = new MediaPlayer(new Media(Resource.WRONG_SELECTED));
//	private static MediaPlayer promotionSound = new MediaPlayer(new Media(Resource.PROMOTION_SOUND));
//	private static MediaPlayer winning = new MediaPlayer(new Media(Resource.WINNING_SOUND));
	private static boolean soundEffectStatus;

	public static boolean getSoundEffectStatus() {
		return soundEffectStatus;
	}

	public static void setSoundEffectStatus(boolean soundEffectStatus) {
		SoundManager.soundEffectStatus = soundEffectStatus;
	}

	public static void playClickingSound() {
		if (soundEffectStatus) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub

							clickingSound.play();
							// clickingSound = new MediaPlayer(sound);
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

	public static void playWrongSelected() {
		if (soundEffectStatus) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub

							wrongSelected.play();
							// clickingSound = new MediaPlayer(sound);
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
		if (soundEffectStatus) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub

							promotionSound.play();
							// clickingSound = new MediaPlayer(sound);
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

	public static void playWinningSound() {
		if (soundEffectStatus) {
			Thread thread = new Thread(() -> {
				try {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub

							winning.play();
							// clickingSound = new MediaPlayer(sound);
							winning.seek(new Duration(0));
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

	public static void stopMenuBgm() {
		if (bgm.getStatus().equals(MediaPlayer.Status.PLAYING))
			bgm.stop();
	}

	public static double getMenuBgmVolume() {
		return bgm.getVolume();
	}

	public static void setMenuBgmVolume(double volume) {
		bgm.setVolume(volume);
	}

	public static void playClockTick() {
		if (soundEffectStatus && !clockTick.getStatus().equals(MediaPlayer.Status.PLAYING)) {
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
	}

	public static void stopClockTick() {
		if (clockTick.getStatus().equals(MediaPlayer.Status.PLAYING))
			clockTick.stop();
	}

	public static double getSoundEffectVolume() {
		return clickingSound.getVolume();
	}

	public static void setSoundEffectVolume(double volume) {
		clockTick.setVolume(volume);
		clickingSound.setVolume(volume);
		wrongSelected.setVolume(volume * 0.70);
		promotionSound.setVolume(volume * 0.25);
		winning.setVolume(volume);
	}
}
