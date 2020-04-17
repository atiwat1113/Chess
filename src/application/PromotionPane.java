package application;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;;

public class PromotionPane extends StackPane{
	
	private Text text;
	private VBox promotion;
	
	public PromotionPane() {
		this.setPrefSize(160,80);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setAlignment(Pos.CENTER);
		
		promotion = new VBox();
		promotion.setAlignment(Pos.TOP_CENTER);
		promotion.setSpacing(2);
		
		HBox selection = new HBox();
		selection.setAlignment(Pos.CENTER);
		selection.setSpacing(2);
		
		Label label = new Label("Select promotion");
		//change color label
		PromotionButton queen = new PromotionButton("Q");
		PromotionButton rook = new PromotionButton("R");
		PromotionButton bishop = new PromotionButton("B");
		PromotionButton knight = new PromotionButton("K");
		
		text = new Text();
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
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
		this.getChildren().remove(this.getChildren().indexOf(text));
		promotion.getChildren().add(text);
		this.getChildren().add(promotion);
		//this.getChildren().remove(1);
	}

	public void setMessage(String message) {
		this.text.setText(message);
	}
	
	
}
