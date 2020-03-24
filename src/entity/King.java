package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.Board;
import logic.Side;
import logic.Sprites;

public class King extends Entity{
	private boolean neverMove;//for castling
	public King(Point p,Side side) {
		super(p,side);
		neverMove = true;
	}
	public int getSymbol() {
		if (this.side == Side.Black) {
			return Sprites.B_KING;
		}
		return Sprites.W_KING;
	}
	public boolean move(Point p) {//--------------------
		for (Point moveablePoint : this.canMove()) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				return true;
			}
		}
		return false;
	}
	public Point[] canMove() {
		return null;
	}
	public Point[] canEat(Board board) {
		ArrayList<Point> eatablePoint = new ArrayList<Point>();
		for (Point moveablePoint : this.canMove()) {
			if (board.getEntity(moveablePoint.x, moveablePoint.y) != null && board.getEntity(moveablePoint.x, moveablePoint.y).getSide() != this.side) {
				eatablePoint.add(moveablePoint);
			}
		}
		return (Point[]) eatablePoint.toArray();
	}
}
