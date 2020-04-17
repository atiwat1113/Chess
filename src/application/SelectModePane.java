package application;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SelectModePane extends VBox{

		public SelectModePane() {
			Label select = new Label("Select Mode");
			
			SelectModeButton normal = new SelectModeButton("Normal");
			SelectModeButton atomic = new SelectModeButton("Atomic");
			SelectModeButton kingOfTheHill = new SelectModeButton("King of the hill");
			SelectModeButton threeCheck = new SelectModeButton("Three check");
			SelectModeButton antiChess = new SelectModeButton("Anti chess");
			SelectModeButton chess960 = new SelectModeButton("Chess960");
			SelectModeButton horde = new SelectModeButton("Horde");
			
		}
}
