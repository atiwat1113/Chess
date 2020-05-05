package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import logic.Side;
import game.base.Board;

public class Bishop extends Entity {

	public Bishop(Point p, Side side) {
		super(p, side);
	}

	public String getSymbol() {
		return (this.side == Side.BLACK) ? Sprites.B_BISHOP : Sprites.W_BISHOP;
	}

	public String getHighlightSymbol() {
		return (this.side == Side.BLACK) ? Sprites.HIGHLIGHT_B_BISHOP : Sprites.HIGHLIGHT_W_BISHOP;
	}

	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : moveList(board, point, new Point(1, 1)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(1, -1)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(-1, 1)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(-1, -1)))
			returnPoint.add(p);
		return returnPoint;
	}

	public String toString() {
		return "Bishop [point=" + point + ", side=" + side + "]";
	}

}
