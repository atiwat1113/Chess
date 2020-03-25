package entity;

import java.awt.Point;

import entity.base.Entity;
import logic.Side;
import logic.Sprites;

public class Knight extends Entity{

	public Knight(Point p, Side side) {
		super(p,side);
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_KNIGHT;
		}
		return Sprites.W_KNIGHT;
	}

	@Override
	public Point[] moveList() {
		// TODO Auto-generated method stub
		return null;
	}

}
