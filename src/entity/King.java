package entity;

import java.awt.Point;

import entity.base.Entity;
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
		return true;
	}
	public Point[] canMove() {
		return null;
	}
	public Point[] canEat() {
		return null;
	}
}
