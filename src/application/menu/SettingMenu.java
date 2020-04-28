package application.menu;

import Resource.Resource;
import application.AppManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingMenu extends VBox {

	private Slider bgmSlider;
	private Slider soundEffectSlider;

	public SettingMenu() {
		this.setPrefSize(750, 600);
		this.setSpacing(40);
		this.setAlignment(Pos.CENTER);

		bgmSlider = new Slider();
		bgmSlider.setMaxWidth(200);

		soundEffectSlider = new Slider();
		soundEffectSlider.setMaxWidth(200);

		Label setting = new Label("Setting");
		setting.setFont(Font.loadFont(Resource.ROMAN_FONT, 30));

		MyButton returnBtn = new MyButton("Return to Menu", 20);
		setReturnBtnListener(returnBtn);

		bgmSlider.setValue(AppManager.getMenuBgmVolume() * 100);
		soundEffectSlider.setValue(AppManager.getSoundEffectVolume() * 100);
		setBgmSliderListener();
		setSoundEffectSliderListener();

		setBackgroundWithImage();
		this.getChildren().addAll(setting, bgmSlider, soundEffectSlider, returnBtn);
	}

	private void setBgmSliderListener() {
		bgmSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				AppManager.setMenuBgmVolume(bgmSlider.getValue() / 100);
				// System.out.println(bgmSlider.getValue()/100);
				String style = String.format("-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);"
						+ "-fx-pref-height:10;",
                      (int) bgmSlider.getValue(), (int) bgmSlider.getValue());
				bgmSlider.lookup(".track").setStyle(style);
			}
		});

		bgmSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AppManager.playClickingSound();
				
			}
		});
	}

	private void setSoundEffectSliderListener() {
		soundEffectSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				AppManager.setSoundEffectVolume(soundEffectSlider.getValue() / 100);
				// System.out.println(soundEffectSlider.getValue() / 100);
				String style = String.format("-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);"
						+ "-fx-pref-height:10;",
	                      (int) soundEffectSlider.getValue(), (int) soundEffectSlider.getValue());
					soundEffectSlider.lookup(".track").setStyle(style);
			}
		});

		soundEffectSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AppManager.playClickingSound();
			}
		});
	}

	private void setReturnBtnListener(MyButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AppManager.playClickingSound();
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

	public Slider getBgmSlider() {
		return bgmSlider;
	}

	public Slider getSoundEffectSlider() {
		return soundEffectSlider;
	}
	
	
}
