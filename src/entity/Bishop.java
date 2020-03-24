package entity;

import java.awt.Point;

import entity.base.Entity;
import logic.Side;
import logic.Sprites;

public class Bishop extends Entity{

		public Bishop(Point p,Side side) {
			super(p,side);
		}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.Black) {
			return Sprites.B_BISHOP;
		}
		return Sprites.W_BISHOP;
	}

	@Override
	public Point[] canMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
