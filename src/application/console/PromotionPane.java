package application.console;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;;

public class PromotionPane extends VBox{
	
	private Text text;
	private VBox promotion;
	private HBox selection;
	
	public PromotionPane() {
		this.setPrefSize(160,100);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setAlignment(Pos.TOP_LEFT);
		
		promotion = new VBox();
		promotion.setAlignment(Pos.TOP_LEFT);
		promotion.setSpacing(7);
		
		selection = new HBox();
		selection.setAlignment(Pos.CENTER_LEFT);
		selection.setSpacing(2);
		
		Label label = new Label("Select promotion");
		label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		//change color label
		PromotionButton queen = new PromotionButton("Q");
		PromotionButton rook = new PromotionButton("R");
		PromotionButton bishop = new PromotionButton("B");
		PromotionButton knight = new PromotionButton("K");
		
		text = new Text();
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		text.setFill(Color.RED.darker());
		
		selection.getChildren().addAll(queen,rook,bishop,knight);
		promotion.getChildren().addAll(label,selection);
		
		this.getChildren().add(text);
	}
	
	public void hidePromotionPane() {
		this.getChildren().remove(this.getChildren().indexOf(promotion));
//		canvas = new Canvas(150,150);
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//		gc.setFill(color);
//		gc.fillRect(0, 0, 150, 150);
//		this.getChildren().add(canvas);
	}
	
	public void showPromotionPane() {
		this.getChildren().add(promotion);
		for (Node pb : selection.getChildren()) {
			((PromotionButton) pb).setBackgroundWithImage();
		}
		//this.getChildren().remove(1);
	}

	public void setMessage(String message) {
		this.text.setText(message);
	}
}
