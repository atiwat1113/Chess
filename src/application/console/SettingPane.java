package application.console;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SettingPane extends VBox{
	public SettingPane() {
		setPrefSize(140, 65);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, 
//				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER_LEFT);
		
		MyCheckBox rotation = new MyCheckBox("rotate");
		MyCheckBox clickSound = new MyCheckBox("click sound");
		
		this.getChildren().addAll(rotation,clickSound);
	}
	
	class MyCheckBox extends CheckBox {
		
		public MyCheckBox(String text) {
			super(text);
			this.setFont(new Font(15));
			
		}
	}
}
