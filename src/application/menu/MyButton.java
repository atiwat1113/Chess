package application.menu;

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
import javafx.scene.text.FontWeight;

public class MyButton extends Button{
	
	private static final Color color = new Color((double) 180 / 255, (double) 180 / 255, (double) 180 / 255, 1);
	
	public MyButton(String text) { // decorate button here.
		super(text);
		this.setPrefSize(200, 50);
		//this.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
		this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		setListener();
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
		//this.setFont(Font.loadFont(Resource.ROMAN_FONT, 30));
		System.out.println(Resource.ROMAN_FONT);
	}
	
	private void setMouseExitedTextFont() {
		//this.setFont(Font.loadFont(Resource.ROMAN_FONT, 20));
	}
}
