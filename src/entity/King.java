package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.*;
import logic.*;
import game.base.Board;

public class King extends Entity implements HaveCastling {
	private boolean neverMove;// for castling

	public King(Point p, Side side) {
		super(p, side);
		neverMove = true;
	}

	@Override
	public String getSymbol() {
		if (this.side == Side.BLACK) {
			return Sprites.B_KING;
		}
		return Sprites.W_KING;
	}

	@Override
	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : Board.getKingWalk()) {
			Point check = new Point(this.point.x + p.x, this.point.y + p.y);
			if (!board.isInBoard(check)) {
				continue;
			}
			if (board.getEntity(check) == null || board.getEntity(check).getSide() != side) {
				returnPoint.add(check);
			}
		}
//		for(Point p:castingPoint(board)) {
//			returnPoint.add(p);
//		}
		return returnPoint;
	}
	
	public String toString() {
		return "King [point=" + point + ", side=" + side + "]";
	}
	
	public void setNeverMove() {
		this.neverMove = false;
	}
	
	public boolean isNeverMove() {
		return neverMove;
	}
	
	public ArrayList<Point> castingPoint(Board board) {//White 7,4 7,0 7,7 // Black 0,4 0,0 0,7
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		int s=7;
		if (side == Side.BLACK) {
			s=0;
		}
		if(!point.equals(new Point(s,4))||!neverMove) {
			return returnPoint;
		}
		if (rightCastlingPoint(board, s)!=null) returnPoint.add(rightCastlingPoint(board, s));
		if (leftCastlingPoint(board, s)!=null) returnPoint.add(leftCastlingPoint(board, s));
		return returnPoint;
	}
	public Point rightCastlingPoint(Board board, int s) {
		int[] s1 = {4,5,6,7};
		int[] s2 = {5,6};
		for(int e : s1) {
			if(board.isEatenPoint(new Point(s,e), side)) {
				return null;
			}
		}
		for(int e : s2) {
			if(board.getEntity(new Point(s,e))!=null) {
				return null;
			}
		}
		try {
			if(((HaveCastling) board.getEntity(new Point(s,7))).isNeverMove()) {
				return new Point(s,6);
			}
		}finally {}
		return null;
	}
	public Point leftCastlingPoint(Board board, int s) {
		int[] s1 = {0,2,3,4};
		int[] s2 = {1,2,3};
		for(int e : s1) {
			if(board.isEatenPoint(new Point(s,e), side)) {
				return null;
			}
		}
		for(int e : s2) {
			if(board.getEntity(new Point(s,e))!=null) {
				return null;
			}
		}
		try {
			if(((HaveCastling) board.getEntity(new Point(s,0))).isNeverMove()) {
				return new Point(s,2);
			}
		}finally {}
		return null;
	}
}
