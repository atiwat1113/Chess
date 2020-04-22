package application.menu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import Resource.Resource;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class MyButton extends Button{
	
	private static final Color color = new Color((double) 180 / 255, (double) 180 / 255, (double) 180 / 255, 1);
	private String font;
	
	public MyButton(String text) { // decorate button here.
		super(text);
		this.setPrefSize(300, 50);
		this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		setListener();
		
		try {
			font = URLDecoder.decode(Resource.ROMAN_FONT,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
		this.setFont(Font.loadFont(font, 20));
		this.setTextFill(Color.BLACK);
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
		this.setFont(Font.loadFont(font, 23));
		//System.out.println(Resource.ROMAN_FONT);
	}
	
	private void setMouseExitedTextFont() {
		this.setFont(Font.loadFont(font, 20));
	}
}
