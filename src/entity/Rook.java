package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.Board;
import logic.Side;
import logic.Sprites;

public class Rook extends Entity {

	private boolean neverMove;

	public Rook(Point p, Side side) {
		super(p, side);
		this.neverMove = true;
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_ROOK;
		}
		return Sprites.W_ROOK;
	}

	@Override
	public Point[] moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : moveList(board, p, new Point(1,0))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(0,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,0))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(0,-1))) returnPoint.add(p);
		return (Point[]) returnPoint.toArray();
	}

}
