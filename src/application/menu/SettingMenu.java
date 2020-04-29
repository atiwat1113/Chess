package application.menu;

import Resource.Resource;
import application.AppManager;
import application.SoundManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingMenu extends VBox {

	private Slider bgmSlider;
	private Label bgmValue;
	private Slider sfxSlider;
	private Label sfxValue;

	public SettingMenu() {
		this.setPrefSize(750, 600);
		this.setSpacing(55);
		this.setAlignment(Pos.CENTER);

		// ---------------------------------------------------------------------------------------------------------
		
		//BGM Pane
		HBox bgmPane = new HBox();
		bgmPane.setAlignment(Pos.CENTER);
		bgmPane.setPrefWidth(290);
		HBox bgmBox = new HBox();
		bgmBox.setPrefWidth(290);
		bgmBox.setSpacing(10);
		bgmBox.setAlignment(Pos.CENTER_LEFT);
		bgmSlider = new Slider();
		bgmSlider.setMaxWidth(200);
		bgmSlider.setValue(SoundManager.getMenuBgmVolume() * 100);
		Label bgmLabel = new Label("BGM");
		bgmValue = new Label(String.format("%.1f", bgmSlider.valueProperty().getValue()));
		bgmLabel.setFont(Font.loadFont(Resource.ROMAN_FONT, 25));
		bgmValue.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		bgmBox.getChildren().addAll(bgmLabel,bgmSlider,bgmValue);
		bgmPane.getChildren().add(bgmBox);
		

		// ---------------------------------------------------------------------------------------------------------
		
		//SFX Box
		HBox sfxPane = new HBox();
		sfxPane.setAlignment(Pos.CENTER);
		sfxPane.setPrefWidth(290);
		HBox sfxBox = new HBox();
		sfxBox.setPrefWidth(290);
		sfxBox.setSpacing(10);
		sfxBox.setAlignment(Pos.CENTER_LEFT);
		sfxBox.setTranslateX(16);
		sfxSlider = new Slider();
		sfxSlider.setMaxWidth(200);
		sfxSlider.setValue(SoundManager.getSoundEffectVolume() * 100);
		Label sfxLabel = new Label("SFX");
		sfxValue = new Label(String.format("%.1f", sfxSlider.valueProperty().getValue()));
		sfxLabel.setFont(Font.loadFont(Resource.ROMAN_FONT, 25));
		sfxValue.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		sfxBox.getChildren().addAll(sfxLabel,sfxSlider,sfxValue);
		sfxPane.getChildren().add(sfxBox);

		// ---------------------------------------------------------------------------------------------------------
		
		Label setting = new Label("Setting");
		setting.setFont(Font.loadFont(Resource.ROMAN_FONT, 35));

		MyButton returnBtn = new MyButton("Return to Menu", 20);
		setReturnBtnListener(returnBtn);
	
		
		setBgmSliderListener();
		setSoundEffectSliderListener();

		setBackgroundWithImage();
		this.getChildren().addAll(setting, bgmPane, sfxPane, returnBtn);
	}

	private void setBgmSliderListener() {
		bgmSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				SoundManager.setMenuBgmVolume(bgmSlider.getValue() / 100);
				bgmValue.setText(String.format("%.1f", bgmSlider.valueProperty().getValue()));
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
				SoundManager.playClickingSound();
				
			}
		});
	}

	private void setSoundEffectSliderListener() {
		sfxSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				SoundManager.setSoundEffectVolume(sfxSlider.getValue() / 100);
				sfxValue.setText(String.format("%.1f", sfxSlider.valueProperty().getValue()));
				// System.out.println(soundEffectSlider.getValue() / 100);
				String style = String.format("-fx-background-color: linear-gradient(to right, #2D819D %d%%, #CCCCCC %d%%);"
						+ "-fx-pref-height:10;",
	                      (int) sfxSlider.getValue(), (int) sfxSlider.getValue());
				sfxSlider.lookup(".track").setStyle(style);
			}
		});

		sfxSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				SoundManager.playClickingSound();
			}
		});
	}

	private void setReturnBtnListener(MyButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
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

	public Slider getBgmSlider() {
		return bgmSlider;
	}

	public Slider getSoundEffectSlider() {
		return sfxSlider;
	}
	
	
}
