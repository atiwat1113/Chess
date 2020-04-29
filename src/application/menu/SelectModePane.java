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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SelectModePane extends VBox {

	private static final Image background = new Image(Resource.BACKGROUND);

	public SelectModePane() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setPrefSize(750, 600);
		setBackgroundWithImage();

		HBox selectBox = new HBox();
		VBox left = new VBox();
		VBox right = new VBox();

		selectBox.setAlignment(Pos.CENTER);
		selectBox.setSpacing(25);
		left.setAlignment(Pos.CENTER);
		left.setSpacing(12);
		right.setAlignment(Pos.CENTER);
		right.setSpacing(12);

		Label select = new Label("Select Mode");
		select.setFont(Font.loadFont(Resource.ROMAN_FONT, 35));
		select.setTextFill(Color.BLACK);

		SelectModeButton normal = new SelectModeButton("Normal", 20);
		SelectModeButton atomic = new SelectModeButton("Atomic", 20);
		SelectModeButton kingOfTheHill = new SelectModeButton("King of the hill", 20);
		SelectModeButton threeCheck = new SelectModeButton("Three check", 20);
		SelectModeButton chess960 = new SelectModeButton("Chess960", 20);
		SelectModeButton horde = new SelectModeButton("Horde", 20);
		MyButton returnBtn = new MyButton("Return to Menu", 20);
		setReturnBtnListener(returnBtn);

		left.getChildren().addAll(normal, atomic, kingOfTheHill);
		right.getChildren().addAll(threeCheck, chess960, horde);
		selectBox.getChildren().addAll(left, right);
		this.getChildren().addAll(select, selectBox, returnBtn);

	}

	private void setBackgroundWithImage() {
		BackgroundSize bgSize = new BackgroundSize(this.getPrefWidth(), this.getPrefHeight(), false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(background, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgImgA));
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
}
