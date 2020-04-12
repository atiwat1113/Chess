package application;

import java.awt.Point;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import logic.Cell;
import logic.GameController;
import logic.Sprites;

public class BoardCell extends Pane {

	private Cell myCell;
	private Point p;
	private Color color;
	private boolean isClicked;

	public BoardCell(Cell cell, Point p, Color color) {
		this.myCell = cell;
		this.p = p;
		this.color = color;
		this.isClicked = false;
		this.setPrefSize(100, 100);
		this.setMinSize(100, 100);
		this.setPadding(new Insets(8));
		if (hasEntity()) this.setBackgroundTileColor(new Image(this.myCell.getEntity().getSymbol()));
		else setBackgroundTileColor();

	}

	public boolean hasEntity() {
		// TODO Auto-generated method stub
		return !this.myCell.IsEmpty();
	}

	public void setBackgroundTileColor() {
		// TODO Auto-generated method stub
		this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void setBackgroundTileColor(Image image) {
		// TODO Auto-generated method stub
		BackgroundFill bgFill = new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = { bgFill };
		BackgroundSize bgSize = new BackgroundSize(100, 100, false, false, false, false);
		BackgroundImage bgImg = new BackgroundImage(image, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { bgImg };
		this.setBackground(new Background(bgFillA, bgImgA));
	}

	public void setBackgroundTileColor(Image redDot, Image entity) {
		// TODO Auto-generated method stub
		BackgroundFill bgFill = new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = { bgFill };
		BackgroundSize bgSize = new BackgroundSize(100, 100, false, false, false, false);
		BackgroundImage redDotImg = new BackgroundImage(redDot, null, null, null, bgSize);
		BackgroundImage entityImg = new BackgroundImage(entity, null, null, null, bgSize);
		BackgroundImage[] bgImgA = { entityImg, redDotImg };
		this.setBackground(new Background(bgFillA, bgImgA));
	}

	public void update() {
		if (hasEntity()) this.setBackgroundTileColor(new Image(this.myCell.getEntity().getSymbol()));
		else setBackgroundTileColor();
		this.isClicked = false;
	}
	
	public Cell getMyCell() {
		return myCell;
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

	
	
}






/*
 * if (hasEntity()) { if (!isClicked) { for (BoardCell bc :
 * BoardPane.boardCellList) { if
 * (this.myCell.getEntity().moveList(GameController.getBoard()).contains(bc.getP
 * ())) if (bc.hasEntity()) bc.setBackgroundTileColor(new
 * Image(Sprites.WALKPATH)); else bc.setBackgroundTileColor(new
 * Image(Sprites.WALKPATH), new Image(bc.getMyCell().getEntity().getSymbol()));
 * } this.isClicked = true; } else { for (BoardCell bc :
 * BoardPane.boardCellList) { if
 * (this.myCell.getEntity().moveList(GameController.getBoard()).contains(bc.getP
 * ())) if (bc.hasEntity()) bc.setBackgroundTileColor(new
 * Image(bc.getMyCell().getEntity().getSymbol())); else
 * bc.setBackgroundTileColor(); } this.isClicked = false; } }
 */