package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import logic.*;
import game.base.Board;

public class Queen extends Entity {

	private boolean isFromPawn;

	public Queen(Point p, Side side) {
		super(p, side);
		isFromPawn = false;
	}

	public Queen(Point p, Side side, Boolean isFromPawn) {
		super(p, side);
		this.isFromPawn = isFromPawn;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_QUEEN;
		}
		return Sprites.W_QUEEN;
	}

	@Override
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

	public boolean isFromPawn() {
		return isFromPawn;
	}
	
	public String toString() {
		return "Queen [point=" + point + ", side=" + side + "]";
	}

	@Override
	public String getHighlightSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.HIGHLIGHT_B_QUEEN;
		}
		return Sprites.HIGHLIGHT_W_QUEEN;
	}
}
