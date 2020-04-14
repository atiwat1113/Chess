package application;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class AppManager {

	private static PromotionPane promotionPane;
	
	public static void hidePromotion() {
		AppManager.promotionPane.hidePromotionPane();
	}
	
	public static void showPromotion() {
		AppManager.promotionPane.showPromotionPane();
	}


	public static PromotionPane getPromotionPane() {
		return promotionPane;
	}

	public static void setPromotionPane(PromotionPane promotionPane) {
		AppManager.promotionPane = promotionPane;
	}
	
	
}
