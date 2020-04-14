package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import logic.GameController;

public class PromotionButton extends Button {
	
	private String text;
	
	public PromotionButton(String text) {
		super(text);
		this.text = text;
		this.setListener();
	}
	
	public void setListener() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				AppManager.hidePromotion();
				AppManager.setPromotionListener(text);
			}
		});
	}
}
