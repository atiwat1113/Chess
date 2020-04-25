package application.console;

import application.AppManager;
import application.menu.MyButton;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
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
import logic.GameController;
import logic.Side;

public class SettingPane extends VBox {
	public SettingPane() {
		setPrefSize(200, 100);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, 
//				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setSpacing(15);
		this.setAlignment(Pos.TOP_LEFT);

		MyCheckBox rotation = new MyCheckBox("rotate");
		MyCheckBox clickSound = new MyCheckBox("click sound");
		ReturnButton returnBtn = new ReturnButton("return to menu");

		clickSound.setSelected(true);
		setRotationListener(rotation);
		setClickSoundListener(clickSound);
		setReturnBtnListener(returnBtn);

		this.getChildren().addAll(rotation, clickSound, returnBtn);
	}

	class MyCheckBox extends CheckBox {

		public MyCheckBox(String text) {
			super(text);
			this.setFont(new Font(18));

		}
	}

	class ReturnButton extends MyButton {
		
		private Color color = new Color((double) 195 / 255, (double) 195 / 255, (double) 195 / 255, 1);

		public ReturnButton(String text) {
			super(text);
			this.setFontSize(14);
			this.setFont(Font.loadFont(this.font, 14));
			this.setPrefWidth(250);
			
		}
		
	
		
	}

	private void setRotationListener(MyCheckBox rotation) {
		rotation.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (rotation.isSelected()) {
					// System.out.println("selected");
					AppManager.setRotate(true);
				} else {
					// System.out.println("deselected");
					AppManager.setRotate(false);
				}
				if (GameController.getTurn() == Side.BLACK) {
					// System.out.println(GameController.getTurn());
					AppManager.rotateBoard();
				}
				AppManager.getBoardPane().updateBoard();
			}
		});
	}

	private void setClickSoundListener(MyCheckBox clickSound) {
		clickSound.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (clickSound.isSelected()) {
					// System.out.println("selected");
					AppManager.setClickSoundStatus(true);
				} else {
					// System.out.println("deselected");
					AppManager.setClickSoundStatus(false);
				}
			}
		});
	}
	
	private void setReturnBtnListener(ReturnButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				returnBtn.playClickingSound();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Return to menu");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to return to menu?");
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						AppManager.showMenu();
					}
				});
			}
		});
	}
}
