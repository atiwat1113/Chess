package entity;

import java.awt.Point;
import java.util.ArrayList;
import entity.base.Entity;
import logic.*;

public class Bishop extends Entity{
	private boolean isFromPawn;
	
	public Bishop(Point p, Side side) {
		super(p,side);
		isFromPawn=false;
	}
	
	public Bishop(Point p,Side side, Boolean isFromPawn) {
		super(p,side);
		this.isFromPawn=isFromPawn;
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_BISHOP;
		}
		return Sprites.W_BISHOP;
	}

	@Override
	public Point[] moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : moveList(board, p, new Point(1,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(1,-1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,-1))) returnPoint.add(p);
		return (Point[]) returnPoint.toArray();
	}
	
	public boolean isFromPawn() {
		return isFromPawn;
	}

}
