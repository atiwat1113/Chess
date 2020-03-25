package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.Board;
import logic.Side;
import logic.Sprites;

public class Knight extends Entity{
	private boolean isFromPawn;
	
	public Knight(Point p, Side side) {
		super(p,side);
		isFromPawn=false;
	}
	
	public Knight(Point p,Side side, Boolean isFromPawn) {
		super(p,side);
		this.isFromPawn=isFromPawn;
	}
	
	@Override
	public Point getSymbol() {
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
			Point check = new Point(this.p.x+p.x,this.p.y+p.y);
			if (!isInBoard(board, check)) {
				continue;
			}
			if(board.getEntity(check) == null || board.getEntity(check).getSide() != side) {
				returnPoint.add(check);
				System.out.println(""+check.x+" "+check.y);//--------------------------------------------------------
			}
		}
		return (Point[]) returnPoint.toArray();
	}

	public boolean isFromPawn() {
		return isFromPawn;
	}
	
}
