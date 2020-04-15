package application;

public class AppManager {

	private static PromotionPane promotionPane;
	private static BoardPane boardPane;
	private static SettingButton setting;
	
	
	public static void hidePromotion() {
		AppManager.promotionPane.hidePromotionPane();
	}
	
	public static void showPromotion() {
		AppManager.promotionPane.showPromotionPane();
	}

	public static void setPromotionListener(String text) {
		boardPane.promotion(text);
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

	public static SettingButton getSetting() {
		return setting;
	}

	public static void setSetting(SettingButton setting) {
		AppManager.setting = setting;
	}
	
	public static void displayMessage(String message) {
		//----------------------------------------------------------------------------------------
	}
	
}
