package application.board;

import java.awt.Point;
import application.AppManager;
import game.base.Games;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import logic.Cell;

public class BoardCell extends Pane {

	private Cell myCell;
	private Point p;
	private Color color;
	private boolean isClicked; // true if this board cell has an entity and is clicked.
	private boolean moveable; // true if this board cell is able to move to when an entity is clicked.

	public BoardCell(Cell cell, Point p, Color color) {
		this.myCell = cell;
		this.p = p;
		this.color = color;
		this.isClicked = false;
		this.moveable = false;
		this.setPrefSize(60, 60);
		this.setMinSize(60, 60);
		this.setPadding(new Insets(8));
		if (hasEntity())
			this.setBackgroundTileColor(new Image(this.myCell.getEntity().getSymbol()));
		else
			setBackgroundTileColor();
		if (AppManager.getGameType().equals(Games.KINGOFTHEHILL)) {
			if ((p.x == 3 && p.y == 3) || (p.x == 4 && p.y == 3) || (p.x == 3 && p.y == 4) || (p.x == 4 && p.y == 4)) {
				this.setBorder(
						new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			}
		}

	}

	public boolean hasEntity() {
		return !this.myCell.IsEmpty();
	}

	public void setBackgroundTileColor() {
		this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void setBackgroundTileColor(Image image) {
		BackgroundFill bgFill = new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = { bgFill };
		BackgroundSize bgSize = new BackgroundSize(60, 60, false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(image, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgFillA, bgImgA));
	}

	public void setBackgroundTileColor(Image redDot, Image entity) {
		BackgroundFill bgFill = new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = { bgFill };
		BackgroundSize bgSize = new BackgroundSize(60, 60, false, false, false, false);
		BackgroundImage redDotImg = new BackgroundImage(redDot, null, null, null, bgSize);
		BackgroundImage entityImg = new BackgroundImage(entity, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { entityImg, redDotImg };
		this.setBackground(new Background(bgFillA, bgImgA));
	}

	public void update() {
		if (hasEntity())
			this.setBackgroundTileColor(new Image(this.myCell.getEntity().getSymbol()));
		else
			setBackgroundTileColor();
		this.isClicked = false;
		this.moveable = false;
	}

	// getter and setter
	// -------------------------------------------------------------------------------------

	public void setMyCell(Cell myCell) {
		this.myCell = myCell;
	}

	public Cell getMyCell() {
		return myCell;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public Point getP() {
		return p;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

}
