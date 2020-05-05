package application.menu;

import Resource.Resource;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class MyButton extends Button {

	protected String font;
	protected double fontSize;

	public MyButton(String text, double fontSize) { // decorate button here.
		super(text);
		this.fontSize = fontSize;
		this.setPrefSize(340, 60);
		this.setAlignment(Pos.CENTER);
		setBackgroundWithImage(new Image(Resource.BUTTON_FRAME));
		setListener();

		this.setFont(Font.loadFont(Resource.ROMAN_FONT, this.fontSize));
		this.setTextFill(Color.BLACK);

	}

	protected void setListener() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setMouseEnteredTextFont();
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setMouseExitedTextFont();
			}
		});
	}

	protected void setMouseEnteredTextFont() {
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize + 2));
		// System.out.println(Resource.ROMAN_FONT);
		setBackgroundWithImage(new Image(Resource.HIGHLIGHT_BUTTON_FRAME));
	}

	protected void setMouseExitedTextFont() {
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize));
		setBackgroundWithImage(new Image(Resource.BUTTON_FRAME));

	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setBackgroundWithImage(Image img) {
		BackgroundSize bgSize = new BackgroundSize(this.getPrefWidth(), this.getPrefHeight(), false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(img, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgImgA));
	}
}
