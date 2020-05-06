package application.menu;

import Resource.Resource;
import application.AppManager;
import application.SoundManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingMenu extends VBox {

	private Slider bgmSlider;
	private Label bgmValue;
	private SoundButton bgmSoundButton;
	private Slider sfxSlider;
	private Label sfxValue;
	private SoundButton sfxSoundButton;

	public SettingMenu() {
		HBox bgmPane = new HBox();
		HBox bgmBox = new HBox();
		bgmSlider = new Slider();
		Label bgmLabel = new Label("BGM");

		HBox sfxPane = new HBox();
		HBox sfxBox = new HBox();
		sfxSlider = new Slider();
		Label sfxLabel = new Label("SFX");

		Label setting = new Label("Setting");
		MyButton returnBtn = new MyButton("Return to Menu", 20);

		this.setPrefSize(750, 600);
		this.setSpacing(55);
		this.setAlignment(Pos.CENTER);

		// ---------------------------------------------------------------------------------------------------------

		// BGM Pane

		bgmPane.setAlignment(Pos.CENTER);
		bgmPane.setPrefWidth(490);

		bgmBox.setPrefWidth(490);
		bgmBox.setSpacing(10);
		bgmBox.setAlignment(Pos.CENTER_LEFT);

		bgmSlider.setValue(SoundManager.getMenuBgmVolume() * 100);

		bgmValue = new Label(String.format("%.1f", bgmSlider.valueProperty().getValue()));
		bgmValue.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		
		bgmSoundButton = new SoundButton("bgm", bgmSlider, bgmValue);

		bgmLabel.setFont(Font.loadFont(Resource.ROMAN_FONT, 25));

		bgmBox.getChildren().addAll(bgmLabel, bgmSoundButton, bgmSlider, bgmValue);
		bgmPane.getChildren().add(bgmBox);

		// ---------------------------------------------------------------------------------------------------------

		// SFX Pane

		sfxPane.setAlignment(Pos.CENTER);
		sfxPane.setPrefWidth(475);

		sfxBox.setPrefWidth(475);
		sfxBox.setSpacing(10);
		sfxBox.setAlignment(Pos.CENTER_LEFT);
		sfxBox.setTranslateX(10);

		sfxSlider.setValue(SoundManager.getSoundEffectVolume() * 100);

		sfxValue = new Label(String.format("%.1f", sfxSlider.valueProperty().getValue()));
		sfxValue.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		
		sfxSoundButton = new SoundButton("sfx", sfxSlider, sfxValue);

		sfxLabel.setFont(Font.loadFont(Resource.ROMAN_FONT, 25));

		sfxBox.getChildren().addAll(sfxLabel, sfxSoundButton, sfxSlider, sfxValue);
		sfxPane.getChildren().add(sfxBox);

		// ---------------------------------------------------------------------------------------------------------

		setting.setFont(Font.loadFont(Resource.ROMAN_FONT, 35));

		setReturnBtnListener(returnBtn);
		setBgmSliderListener();
		setSoundEffectSliderListener();
		setBackgroundWithImage();
		this.getChildren().addAll(setting, bgmPane, sfxPane, returnBtn);
	}

	// --------------------------------------------------------------------------------------

	class SoundButton extends Button {

		private boolean isMuted;

		public SoundButton(String sound, Slider slider, Label value) {
			this.setPrefSize(40, 40);
			this.setAlignment(Pos.CENTER);
			setBackgroundImageByVolume(slider);
			isMuted = false;

			this.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					SoundManager.playClickingSound();
					if (isMuted) {
						isMuted = false;
						if (sound.equals("bgm")) {
							SoundManager.setMenuBgmVolume(slider.getValue() / 100);
						} else {
							SoundManager.setSoundEffectStatus(true);
						}
						setBackgroundImageByVolume(slider);
						slider.setDisable(false);
						value.setDisable(false);
					} else {
						isMuted = true;
						if (sound.equals("bgm")) {
							SoundManager.setMenuBgmVolume(0);
						} else {
							SoundManager.setSoundEffectStatus(false);
						}
						setBackgroundWithImage(new Image(Resource.MUTE_VOLUME_IMAGE));
						slider.setDisable(true);
						value.setDisable(true);
					}
				}
			});

		}

		public void setBackgroundImageByVolume(Slider slider) {
			if (slider.getValue() >= 70) {
				setBackgroundWithImage(new Image(Resource.HIGH_VOLUME_IMAGE));
			} else if (slider.getValue() >= 35) {
				setBackgroundWithImage(new Image(Resource.MID_VOLUME_IMAGE));
			} else if (slider.getValue() >= 15) {
				setBackgroundWithImage(new Image(Resource.LOW_VOLUME_IMAGE));
			} else {
				setBackgroundWithImage(new Image(Resource.VERY_LOW_VOLUME_IMAGE));
			}
		}

		private void setBackgroundWithImage(Image img) {
			BackgroundSize bgSize = new BackgroundSize(this.getPrefWidth(), this.getPrefHeight(), false, false, false, false);
			BackgroundImage bgImg = new BackgroundImage(img, null, null, null, bgSize);
			BackgroundImage[] bgImgA = { bgImg };
			this.setBackground(new Background(bgImgA));
		}

		public void setMuted(boolean isMuted) {
			this.isMuted = isMuted;
		}
		
		
	}
		
	// ---------------------------------------------------------------------------------------

	private void setBgmSliderListener() {
		bgmSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				SoundManager.setMenuBgmVolume(bgmSlider.getValue() / 100);
				bgmValue.setText(String.format("%.1f", bgmSlider.valueProperty().getValue()));
				bgmSoundButton.setBackgroundImageByVolume(bgmSlider);
				String style = String.format(
						"-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);" + "-fx-pref-height:10;",
						(int) bgmSlider.getValue(), (int) bgmSlider.getValue());
				bgmSlider.lookup(".track").setStyle(style);
			}
		});

		bgmSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SoundManager.playClickingSound();
			}
		});
	}

	private void setSoundEffectSliderListener() {
		sfxSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				SoundManager.setSoundEffectVolume(sfxSlider.getValue() / 100);
				sfxValue.setText(String.format("%.1f", sfxSlider.valueProperty().getValue()));
				sfxSoundButton.setBackgroundImageByVolume(sfxSlider);
				String style = String.format(
						"-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);" + "-fx-pref-height:10;",
						(int) sfxSlider.getValue(), (int) sfxSlider.getValue());
				sfxSlider.lookup(".track").setStyle(style);
			}
		});

		sfxSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SoundManager.playClickingSound();
			}
		});
	}

	private void setReturnBtnListener(MyButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SoundManager.playClickingSound();
				AppManager.showMenu();
			}
		});
	}

	private void setBackgroundWithImage() {
		BackgroundSize bgSize = new BackgroundSize(this.getPrefWidth(), this.getPrefHeight(), false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(new Image(Resource.BACKGROUND), null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgImgA));
	}

	public void update() {
		sfxSoundButton.setMuted(!SoundManager.getSoundEffectStatus());
		sfxSlider.setDisable(!SoundManager.getSoundEffectStatus());
		sfxValue.setDisable(!SoundManager.getSoundEffectStatus());
		if(SoundManager.getSoundEffectStatus()) {
			sfxSoundButton.setBackgroundImageByVolume(sfxSlider);
		} else {
			sfxSoundButton.setBackgroundWithImage(new Image(Resource.MUTE_VOLUME_IMAGE));
		}
		
	}
	
	public Slider getBgmSlider() {
		return bgmSlider;
	}

	public Slider getSfxSlider() {
		return sfxSlider;
	}

}
