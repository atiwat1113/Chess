package entity;

import java.awt.Point;
import java.util.ArrayList;
import entity.base.Entity;
import logic.*;
import game.base.Board;

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
	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : board.getKnightWalk()) {
			Point check = new Point(this.p.x+p.x,this.p.y+p.y);
			if (!board.isInBoard(check)) {
				continue;
			}
			if(board.getEntity(check) == null || board.getEntity(check).getSide() != side) {
				returnPoint.add(check);
			}
		}
		return returnPoint;
	}

	public boolean isFromPawn() {
		return isFromPawn;
	}
	
}
