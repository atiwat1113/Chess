package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyButton extends Button{
	
	public MyButton(String text) { // decorate button here.
		super(text);
		this.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
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
		this.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
	}
	
	private void setMouseExitedTextFont() {
		this.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	}
}
