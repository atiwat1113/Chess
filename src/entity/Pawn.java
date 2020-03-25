package entity;

import java.awt.Point;

import entity.base.Entity;
import logic.*;

public class Pawn extends Entity {

	public Pawn(Point p, Side side) {
		super(p, side);
	}

	@Override
	public Point getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_PAWN;
		}
		return Sprites.W_PAWN;
	}

	@Override
	public Point[] moveList(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
