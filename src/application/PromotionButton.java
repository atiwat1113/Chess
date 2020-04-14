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
	}
	
	public void setListener(BoardPane boardPane) {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				boardPane.setPromotionPiece(text);
				AppManager.hidePromotion();
				GameController.promotion(boardPane.getPromotionPiece());
				boardPane.setMoved(true);
				boardPane.setCurrentSelectedPoint(null);
				boardPane.updateBoard(boardPane.getCurrenntSelectedBoardCell());
				boardPane.getCurrenntSelectedBoardCell().update();
			}
		});
	}
}
