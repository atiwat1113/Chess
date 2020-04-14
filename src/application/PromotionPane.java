package application;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PromotionPane extends StackPane{
	
	private static final Color color = new Color((double)200/255,(double)200/255,(double)200/255,0.5);
	private Canvas canvas;
	
	public PromotionPane(BoardPane boardPane) {
		this.setPrefSize(150,150);
		VBox selection = new VBox();
		
		Label label = new Label("Select promotion");
		PromotionButton queen = new PromotionButton("Queen");
		PromotionButton rook = new PromotionButton("Rook");
		PromotionButton bishop = new PromotionButton("Bishop");
		PromotionButton knight = new PromotionButton("Knight");
		
		queen.setListener(boardPane);
		rook.setListener(boardPane);
		bishop.setListener(boardPane);
		knight.setListener(boardPane);
		
		selection.getChildren().addAll(label,queen,rook,bishop,knight);
		
		this.getChildren().add(selection);
	}
	
	public void hidePromotionPane() {
		canvas = new Canvas(150,150);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(color);
		gc.fillRect(0, 0, 150, 150);
		this.getChildren().add(canvas);
		
	}
	
	public void showPromotionPane() {
		this.getChildren().remove(1);
	}
}
