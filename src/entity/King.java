package entity;

import java.awt.Point;

import entity.base.Entity;

public class King extends Entity{
	private boolean neverMove;//for castling
	public King(Point p) {
		super(p);
		neverMove = true;
	}
	public int getSymbol() {
		return 0;
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
