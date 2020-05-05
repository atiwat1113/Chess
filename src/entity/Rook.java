package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import entity.base.Castling;
import logic.Side;
import game.base.Board;

public class Rook extends Entity implements Castling {
	private boolean neverMove;// for castling

	public Rook(Point p, Side side) {
		super(p, side);
		this.neverMove = true;
	}

	public String getSymbol() {
		return (this.side == Side.BLACK) ? Sprites.B_ROOK : Sprites.W_ROOK;
	}

	public String getHighlightSymbol() {
		return (this.side == Side.BLACK) ? Sprites.HIGHLIGHT_B_ROOK : Sprites.HIGHLIGHT_W_ROOK;
	}

	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
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
		return "Rook [point=" + point + ", side=" + side + "]";
	}

	// getter and setter
	public void setNeverMove() {
		this.neverMove = false;
	}

	public boolean isNeverMove() {
		return neverMove;
	}

}
