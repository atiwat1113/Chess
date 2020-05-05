package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import logic.Side;
import game.base.Board;

public class Queen extends Entity {

	public Queen(Point p, Side side) {
		super(p, side);
	}

	public String getSymbol() {
		return (this.side == Side.BLACK) ? Sprites.B_QUEEN : Sprites.W_QUEEN;
	}

	public String getHighlightSymbol() {
		return (this.side == Side.BLACK) ? Sprites.HIGHLIGHT_B_QUEEN : Sprites.HIGHLIGHT_W_QUEEN;
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
		for (Point p : moveList(board, point, new Point(1, 0)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(0, 1)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(-1, 0)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(0, -1)))
			returnPoint.add(p);
		return returnPoint;
	}

	public String toString() {
		return "Queen [point=" + point + ", side=" + side + "]";
	}

}
