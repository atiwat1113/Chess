package application.menu;

import Resource.Resource;
import application.AppManager;
import application.SoundManager;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimeSelectPane extends VBox {

	public TimeSelectPane() {
		Label select = new Label("Select Time Limit");
		TimeSelectButton thirtyMin = new TimeSelectButton("30 Minutes", 20);
		TimeSelectButton fifteenMin = new TimeSelectButton("15 Minutes", 20);
		TimeSelectButton fiveMin = new TimeSelectButton("5 Minutes", 20);
		TimeSelectButton noLimit = new TimeSelectButton("No time limit", 20);
		MyButton returnBtn = new MyButton("Return to Select Mode", 16);

		this.setPrefSize(750, 600);
		this.setSpacing(14);
		this.setAlignment(Pos.CENTER);
		setBackgroundWithImage();

		select.setFont(Font.loadFont(Resource.ROMAN_FONT, 35));
		select.setTextFill(Color.BLACK);

		setReturnBtnListener(returnBtn);

		this.getChildren().addAll(select, thirtyMin, fifteenMin, fiveMin, noLimit, returnBtn);
	}

	private void setBackgroundWithImage() {
		BackgroundSize bgSize = new BackgroundSize(this.getPrefWidth(), this.getPrefHeight(), false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(new Image(Resource.BACKGROUND), null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgImgA));
	}

	private void setReturnBtnListener(MyButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SoundManager.playClickingSound();
				AppManager.showSelectMode();
			}
		});
	}
}
