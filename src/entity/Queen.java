package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.Board;
import logic.Side;
import logic.Sprites;

public class Queen extends Entity{

		public Queen (Point p,Side side) {
			super(p,side);
		}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_QUEEN;
		}
		return Sprites.W_QUEEN;
	}

	@Override
	public Point[] moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : moveList(board, p, new Point(1,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(1,-1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,-1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(1,0))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(0,1))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(-1,0))) returnPoint.add(p);
		for (Point p : moveList(board, p, new Point(0,-1))) returnPoint.add(p);
		return (Point[]) returnPoint.toArray();
	}

}
