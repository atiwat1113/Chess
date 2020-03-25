package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.Board;
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
	public Point[] moveList(Board board) {
		Point[] checkPoint = {new Point(1,2),new Point(2,1),new Point(2,-1),new Point(1,-2),
				new Point(-1,-2),new Point(-2,-1),new Point(-2,1),new Point(-1,2)};
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : checkPoint) {
			if(board.getEntity(p) == null || board.getEntity(p).getSide() != side) {
				returnPoint.add(p);
			}
		}
		return (Point[]) returnPoint.toArray();
	}

}
