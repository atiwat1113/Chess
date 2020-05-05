package application.console;

import Resource.Resource;
import application.AppManager;
import application.SoundManager;
import application.menu.MyButton;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
		MyCheckBox rotation = new MyCheckBox("rotate");
		MyCheckBox soundEffect = new MyCheckBox("sound effect");
		ReturnButton returnBtn = new ReturnButton("return to menu", 14);	
		
		this.setPrefSize(200, 100);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
		this.setSpacing(15);
		this.setAlignment(Pos.TOP_LEFT);

		soundEffect.setSelected(SoundManager.getSoundEffectStatus());
		setRotationListener(rotation);
		setSoundEffectListener(soundEffect);
		setReturnBtnListener(returnBtn);

		this.getChildren().addAll(rotation, soundEffect, returnBtn);
	}

	class MyCheckBox extends CheckBox {

		public MyCheckBox(String text) {
			super(text);
			this.setFont(Font.loadFont(Resource.ROMAN_FONT, 15));
		}
	}

	class ReturnButton extends MyButton {

		private Color color = new Color((double) 195 / 255, (double) 195 / 255, (double) 195 / 255, 1);

		public ReturnButton(String text, double fontSize) {
			super(text, fontSize);
			this.setFont(Font.loadFont(Resource.ROMAN_FONT, 14));
			this.setPrefWidth(250);
			this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));
			this.setBorder(new Border(
					new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
			setListener();
		}

		@Override
		protected void setMouseEnteredTextFont() {
			this.setFont(Font.loadFont(Resource.ROMAN_FONT, this.fontSize-1));
		}

		@Override
		protected void setMouseExitedTextFont() {
			this.setFont(Font.loadFont(Resource.ROMAN_FONT, fontSize));
		}

	}

	private void setRotationListener(MyCheckBox rotation) {
		rotation.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (rotation.isSelected()) {
					AppManager.setRotateStatus(true);
				} else {
					AppManager.setRotateStatus(false);
				}
				if (GameController.getTurn() == Side.BLACK) {
					AppManager.rotateBoard();
				}
				AppManager.getBoardPane().updateBoard();
			}
		});
	}

	private void setSoundEffectListener(MyCheckBox clickSound) {
		clickSound.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (clickSound.isSelected()) {
					SoundManager.setSoundEffectStatus(true);
				} else {
					SoundManager.setSoundEffectStatus(false);
					SoundManager.stopClockTick();
				}
			}
		});
	}

	private void setReturnBtnListener(ReturnButton returnBtn) {
		returnBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				SoundManager.playClickingSound();
				
				alert.setTitle("Return to menu");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to return to menu?");
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						AppManager.displayMessage("");
						AppManager.stopTimer();
						AppManager.showMenu();
						SoundManager.playMenuBgm();
					}
				});
			}
		});
	}
}
