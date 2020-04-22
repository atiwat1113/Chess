package application.console;

import Resource.Sprites;
import application.AppManager;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import logic.GameController;
import logic.Side;

public class PromotionButton extends Button {
	
	private String text;
	private String imgURL;
	
	public PromotionButton(String text) {
		super();
		this.text = text;
		this.setPrefSize(30, 30);
		//this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		setBackgroundWithImage();
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
		
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				highlightButton();
			}
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				unhighlightButton();
			}
		});
	}
	
	private void highlightButton() {
		this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	private void unhighlightButton() {
		this.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	public void setBackgroundWithImage() {
		switch(text) {
		case "Q" :
			if(GameController.getTurn() == Side.BLACK) this.imgURL = Sprites.B_QUEEN;
			else this.imgURL = Sprites.W_QUEEN;
			break;
		case "R" :
			if(GameController.getTurn() == Side.BLACK) this.imgURL = Sprites.B_ROOK;
			else this.imgURL = Sprites.W_ROOK;
			break;
		case "B" :
			if(GameController.getTurn() == Side.BLACK) this.imgURL = Sprites.B_BISHOP;
			else this.imgURL = Sprites.W_BISHOP;
			break;
		case "K" :
			if(GameController.getTurn() == Side.BLACK) this.imgURL = Sprites.B_KNIGHT;
			else this.imgURL = Sprites.W_KNIGHT;
			break;
		default:
		}
		BackgroundFill bgFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = { bgFill };
		BackgroundSize bgSize = new BackgroundSize(30, 30, false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(new Image(imgURL), null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgFillA, bgImgA));
	}
}
