package application;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Insets;;

public class PromotionPane extends StackPane{
	
	private static final Color color = new Color((double)1,(double)1,(double)1,0.5);
			//new Color((double)200/255,(double)200/255,(double)200/255,0.5);
	private Canvas canvas;
	VBox promotion;
	
	public PromotionPane() {
		this.setPrefSize(110,40);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		promotion = new VBox();
		HBox selection = new HBox();
		Label label = new Label("Select promotion");
		//change color label
		PromotionButton queen = new PromotionButton("Q");
		PromotionButton rook = new PromotionButton("R");
		PromotionButton bishop = new PromotionButton("B");
		PromotionButton knight = new PromotionButton("K");
		
		selection.getChildren().addAll(queen,rook,bishop,knight);
		promotion.getChildren().addAll(label,selection);
		this.getChildren().add(promotion);
	}
	
	public void hidePromotionPane() {
		this.getChildren().remove(0);
//		canvas = new Canvas(150,150);
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//		gc.setFill(color);
//		gc.fillRect(0, 0, 150, 150);
//		this.getChildren().add(canvas);
	}
	
	public void showPromotionPane() {
		this.getChildren().add(promotion);
		//this.getChildren().remove(1);
	}
}
