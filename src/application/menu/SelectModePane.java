package application.menu;

import Resource.BackgroundSprites;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SelectModePane extends VBox{

	private static final Image background = new Image(BackgroundSprites.BACKGROUND);
	
		public SelectModePane() {
			this.setAlignment(Pos.CENTER);
			this.setSpacing(7);
			this.setPrefSize(750, 500);
			setBackgroundWithImage();
			
			Label select = new Label("Select Mode");
			select.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			
			SelectModeButton normal = new SelectModeButton("Normal");
			SelectModeButton atomic = new SelectModeButton("Atomic");
			SelectModeButton kingOfTheHill = new SelectModeButton("King of the hill");
			SelectModeButton threeCheck = new SelectModeButton("Three check");
			SelectModeButton chess960 = new SelectModeButton("Chess960");
			SelectModeButton horde = new SelectModeButton("Horde");
			
			this.getChildren().addAll(select,normal,atomic,kingOfTheHill,threeCheck,chess960,horde);
			
		}
		
		private void setBackgroundWithImage() {
			BackgroundSize bgSize = new BackgroundSize(750, 500, false, false, false, false);
			BackgroundImage bgImg = new BackgroundImage(background, null, null, null, bgSize);
			BackgroundImage[] bgImgA = { bgImg };
			this.setBackground(new Background(bgImgA));
		}
}
