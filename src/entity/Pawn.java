package entity;

import java.awt.Point;

import entity.base.Entity;
import logic.Side;
import logic.Sprites;

public class Pawn extends Entity {

	public Pawn(Point p, Side side) {
		super(p, side);
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.Black) {
			return Sprites.B_PAWN;
		}
		return Sprites.B_PAWN;
	}

	@Override
	public Point[] canMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
