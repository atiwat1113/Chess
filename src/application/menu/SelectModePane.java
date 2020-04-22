package application.menu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import Resource.Resource;
import application.AppManager;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SelectModePane extends VBox{

	private static final Image background = new Image(Resource.BACKGROUND);
	private String font;
	
		public SelectModePane() {
			this.setAlignment(Pos.CENTER);
			this.setSpacing(7);
			this.setPrefSize(750, 500);
			setBackgroundWithImage();
			
			try {
				font = URLDecoder.decode(Resource.ROMAN_FONT,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			
			Label select = new Label("Select Mode");
			select.setFont(Font.loadFont(font, 30));
			select.setTextFill(Color.BLACK);
			
			SelectModeButton normal = new SelectModeButton("Normal");
			SelectModeButton atomic = new SelectModeButton("Atomic");
			SelectModeButton kingOfTheHill = new SelectModeButton("King of the hill");
			SelectModeButton threeCheck = new SelectModeButton("Three check");
			SelectModeButton chess960 = new SelectModeButton("Chess960");
			SelectModeButton horde = new SelectModeButton("Horde");
			MyButton returnBtn = new MyButton("Return to Menu");
			setReturnBtnListener(returnBtn);
			
			this.getChildren().addAll(select,normal,atomic,kingOfTheHill,threeCheck,chess960,horde,returnBtn);
			
		}
		
		private void setBackgroundWithImage() {
			BackgroundSize bgSize = new BackgroundSize(750, 500, false, false, false, false);
			BackgroundImage bgImg = new BackgroundImage(background, null, null, null, bgSize);
			BackgroundImage[] bgImgA = { bgImg };
			this.setBackground(new Background(bgImgA));
		}
		
		private void setReturnBtnListener(MyButton returnBtn) {
			returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					AppManager.showMenu();
				}
			});
		}
}
