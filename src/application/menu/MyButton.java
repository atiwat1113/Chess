package application.menu;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import Resource.Resource;
import application.AppManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


public class MyButton extends Button{
	
	private static final Color color = new Color((double) 195 / 255, (double) 195 / 255, (double) 195 / 255, 1);
	protected String font;
	private String soundUrl;
	protected double fontSize;
	
	public MyButton(String text,double fontSize) { // decorate button here.
		super(text);
		this.fontSize = fontSize;
		this.setPrefSize(340, 60);
		this.setAlignment(Pos.CENTER);
		setBackgroundWithImage(new Image(Resource.BUTTON_FRAME));
		setListener();
		
//		try {
//			font = URLDecoder.decode(Resource.ROMAN_FONT,"UTF-8");
//			soundUrl = URLDecoder.decode(Resource.BUTTON_CLICK,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
		
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
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize+2));
		//System.out.println(Resource.ROMAN_FONT);
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
