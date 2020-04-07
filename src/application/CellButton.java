package application;

import logic.*;
import java.awt.Point;
import entity.base.Entity;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;


public class CellButton extends Button {

	private Cell cell;
	private Point point;

	public CellButton(Entity e,Point point){
		super();
		this.point = point;
		//this.setPadding(new Insets(5));
		if(point.x+point.y%2==0) {
			this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		}else {
			this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));	
		Image image = getImage();
		ImageView imageView = new ImageView(image);
		//imageView.setFitHeight(48);
		//imageView.setFitWidth(48);
		this.setGraphic(imageView);				

	}
	public Image getImage() {
		String imageUrl="" + cell.getEntity().getSymbol();
		return new Image(imageUrl);	
	}
	public void highlight() {
		this.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void unhighlight() {
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	public Point getPoint() {
		return point;
	}
	
//	private void setTooltip() {
//		Tooltip	tooltip = new Tooltip();
//		tooltip.setFont(new Font(12));
//		tooltip.setText(item.getItemName() + item.getPriceText() + item.getIncomeText());
//		this.setOnMouseMoved((MouseEvent e) -> {
//			if (item != null)
//			tooltip.show(this, e.getScreenX(), e.getScreenY()+10);
//		});
//		this.setOnMouseExited((MouseEvent e) -> {
//			tooltip.hide();
//		});		
//	}

	

}
