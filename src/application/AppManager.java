package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import logic.GameController;

public class AppManager {

	private static PromotionPane promotionPane;
	private static BoardPane boardPane;
	
	
	
	public static void hidePromotion() {
		AppManager.promotionPane.hidePromotionPane();
	}
	
	public static void showPromotion() {
		AppManager.promotionPane.showPromotionPane();
	}

	public static void setPromotionListener(String text) {
		boardPane.setPromotionPiece(text);
		GameController.promotion(boardPane.getPromotionPiece());
		boardPane.setMoved(true);
		boardPane.setPromoted(false);
		boardPane.setCurrentSelectedPoint(null);
		GameController.nextTurn();
		boardPane.updateBoard(boardPane.getCurrenntSelectedBoardCell());
		boardPane.getCurrenntSelectedBoardCell().update();
		boardPane.getTurnText().setText(GameController.getTurn().toString() + " TURN");
	}
	
	public static BoardPane getBoardPane() {
		return boardPane;
	}

	public static void setBoardPane(BoardPane boardPane) {
		AppManager.boardPane = boardPane;
	}

	public static PromotionPane getPromotionPane() {
		return promotionPane;
	}

	public static void setPromotionPane(PromotionPane promotionPane) {
		AppManager.promotionPane = promotionPane;
	}
	
	
}
