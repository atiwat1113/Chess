package application.menu;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import Resource.Resource;
import application.AppManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
	private Media sound;
	private MediaPlayer clickingSound;
	private double fontSize;
	
	public MyButton(String text,double fontSize) { // decorate button here.
		super(text);
		this.fontSize = fontSize;
		this.setPrefSize(300, 50);
		this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), new Insets(3))));
		setListener();
		
//		try {
//			font = URLDecoder.decode(Resource.ROMAN_FONT,"UTF-8");
//			soundUrl = URLDecoder.decode(Resource.BUTTON_CLICK,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
		
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, this.fontSize));
		this.setTextFill(Color.BLACK);
		
		sound = new Media(Resource.BUTTON_CLICK);
		clickingSound = new MediaPlayer(sound);
		clickingSound.setVolume(0.6);
		
	}
	
	public void playClickingSound() {
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
	
	private void setListener() {
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
	
	private void setMouseEnteredTextFont() {
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize+2));
		this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), Insets.EMPTY)));
		//System.out.println(Resource.ROMAN_FONT);
	}
	
	private void setMouseExitedTextFont() {
		this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize));
		this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), new Insets(2))));
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
}
