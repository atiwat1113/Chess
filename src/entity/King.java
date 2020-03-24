package entity;

import java.awt.Point;

import entity.base.Entity;
import logic.Side;
import logic.Sprites;

public class King extends Entity {
	private boolean neverMove;// for castling

	public King(Point p, Side side) {
		super(p, side);
		neverMove = true;
	}

	@Override
	public int getSymbol() {
		if (this.side == Side.BLACK) {
			return Sprites.B_KING;
		}
		return Sprites.W_KING;
	}

	@Override
	public Point[] canMove() {
		return null;
	}

}
