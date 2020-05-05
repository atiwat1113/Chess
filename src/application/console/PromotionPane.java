package application.console;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import Resource.Resource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;;

public class PromotionPane extends VBox {

	private Text text;
	private VBox promotion;
	private HBox selection;

	public PromotionPane() {
		promotion = new VBox();
		selection = new HBox();
		Label label = new Label("Select promotion");
		text = new Text();
		PromotionButton queen = new PromotionButton("Q");
		PromotionButton rook = new PromotionButton("R");
		PromotionButton bishop = new PromotionButton("B");
		PromotionButton knight = new PromotionButton("K");

		this.setPrefSize(200, 100);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setAlignment(Pos.TOP_LEFT);
		this.setSpacing(15);

		promotion.setAlignment(Pos.TOP_LEFT);
		promotion.setSpacing(15);

		selection.setAlignment(Pos.CENTER_LEFT);
		selection.setSpacing(4);

		label.setFont(Font.loadFont(Resource.ROMAN_FONT, 15));

		text.setFont(Font.loadFont(Resource.ROMAN_FONT, 14));
		text.setFill(Color.DARKRED);

		selection.getChildren().addAll(queen, rook, bishop, knight);
		promotion.getChildren().addAll(label, selection);

		this.getChildren().add(text);
	}

	public void hidePromotionPane() {
		this.getChildren().remove(this.getChildren().indexOf(promotion));
	}

	public void showPromotionPane() {
		this.getChildren().add(promotion);
		for (Node pb : selection.getChildren()) {
			((PromotionButton) pb).setBackgroundWithImage();
		}
	}

	public void setMessage(String message) {
		this.text.setText(message);
	}
}
