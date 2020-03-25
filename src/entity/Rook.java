package entity;

import java.awt.Point;

import entity.base.Entity;
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
	public Point[] moveList() {
		// TODO Auto-generated method stub
		return null;
	}

}
