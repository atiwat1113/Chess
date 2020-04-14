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
		if (isRightCastling(board)) returnPoint.add(new Point(s,6));
		if (isLeftCastling(board)) returnPoint.add(new Point(s,2));
		return returnPoint;
	}
	public boolean isCastlingPoint(Board board, Point point) {
		int s=7;
		if (side == Side.BLACK) {
			s=0;
		}
		if (isRightCastling(board) && point.equals(new Point(s,6))) return true;
		if (isLeftCastling(board) && point.equals(new Point(s,2))) return true;
		return false;
	}
	public boolean isRightCastling(Board board) {
		int s=7;
		if (side == Side.BLACK) {
			s=0;
		}
		int[] s1 = {4,5,6};
		int[] s2 = {5,6};
		for(int e : s1) {
			if(board.isEatenPoint(new Point(s,e), side)) {
				return false;
			}
		}
		for(int e : s2) {
			if(board.getEntity(new Point(s,e))!=null) {
				return false;
			}
		}
		if (board.getEntity(new Point(s,7)) != null && board.getEntity(new Point(s,7)) instanceof Rook) {
			if(((HaveCastling) board.getEntity(new Point(s,7))).isNeverMove()) return true;
		}
//		try {
//			if(((HaveCastling) board.getEntity(new Point(s,7))).isNeverMove()) {
//				return true;//new Point(s,6);
//			}
//		}finally {}
		return false;
	}
	public boolean isLeftCastling(Board board) {
		int s=7;
		if (side == Side.BLACK) {
			s=0;
		}
		int[] s1 = {2,3,4};
		int[] s2 = {1,2,3};
		for(int e : s1) {
			if(board.isEatenPoint(new Point(s,e), side)) {
				return false;
			}
		}
		for(int e : s2) {
			if(board.getEntity(new Point(s,e))!=null) {
				return false;
			}
		}
		if (board.getEntity(new Point(s,0)) != null && board.getEntity(new Point(s,7)) instanceof Rook) {
			if(((HaveCastling) board.getEntity(new Point(s,0))).isNeverMove()) return true;
		}
//		try {
//			if(((HaveCastling) board.getEntity(new Point(s,0))).isNeverMove()) {
//				return true;//new Point(s,2);
//			}
//		}finally {}
		return false;
	}
	public void moveRook(Board board, Point point) {
		ArrayList<Point> moveList = new ArrayList<Point>();
		if (point.equals(new Point(0,2))) {
			moveList.add(new Point(0,3));
			board.move(new Point(0,0),new Point(0,3),moveList);
		}else if (point.equals(new Point(0,6))) {
			moveList.add(new Point(0,5));
			board.move(new Point(0,7),new Point(0,5),moveList);
		}else if (point.equals(new Point(7,2))) {
			moveList.add(new Point(7,3));
			board.move(new Point(7,0),new Point(7,3),moveList);
		}else if (point.equals(new Point(7,6))) {
			moveList.add(new Point(7,5));
			board.move(new Point(7,7),new Point(7,5),moveList);
		}
	}
	@Override
	public String getHighlightSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.HIGHLIGHT_B_KING;
		}
		return Sprites.HIGHLIGHT_W_KING;
	}
}
