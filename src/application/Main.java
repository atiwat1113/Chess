package application;

import game.base.Games;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{
	public void start(Stage primaryStage) {
		
		MenuPane menu = new MenuPane();
		SelectModePane selectMode = new SelectModePane();
		GamePane gamePane = new GamePane();
		PromotionPane promotionPane = new PromotionPane();
		SettingButton setting = new SettingButton();		
		
		AppManager.setMenuPane(menu);
		AppManager.setSelectModePane(selectMode);
		AppManager.setGamePane(gamePane);
		AppManager.setPromotionPane(promotionPane);
		AppManager.setSetting(setting);
		AppManager.setStage(primaryStage);
		AppManager.hidePromotion();
		
	
		Scene scene = new Scene(menu);
		
		AppManager.setScene(scene);
	
		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
		}

	public static void main(String[] args) {
		launch(args);
	}
	
}
