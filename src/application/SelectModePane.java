package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SelectModePane extends VBox{

		public SelectModePane() {
			this.setAlignment(Pos.CENTER);
			this.setSpacing(7);
			this.setPrefSize(750, 500);
			
			Label select = new Label("Select Mode");
			select.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			
			SelectModeButton normal = new SelectModeButton("Normal");
			SelectModeButton atomic = new SelectModeButton("Atomic");
			SelectModeButton kingOfTheHill = new SelectModeButton("King of the hill");
			SelectModeButton threeCheck = new SelectModeButton("Three check");
			SelectModeButton antiChess = new SelectModeButton("Anti chess");
			SelectModeButton chess960 = new SelectModeButton("Chess960");
			SelectModeButton horde = new SelectModeButton("Horde");
			
			this.getChildren().addAll(select,normal,atomic,kingOfTheHill,threeCheck,antiChess,chess960,horde);
			
		}
}
