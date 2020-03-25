package entity.base;

import java.awt.*;
import java.util.ArrayList;

import logic.Board;
import logic.Side;

public abstract class Entity { // private --> protected
	protected Point p;
	protected boolean isMove;
	protected Side side;

	public Entity(Point p, Side side) {// Point (y,x)
		this.p = p;
		this.side = side; // should be set since creating an entity.
		isMove = false;
	}

	public abstract int getSymbol();

	public abstract Point[] moveList(Board board);
	
	public ArrayList<Point> moveList(Board board, Point point, Point vector) {
		ArrayList<Point> ret = new ArrayList<Point>();
		Point nextPoint = new Point(point.x + vector.x, point.y + vector.y);
		if (outOfBoard(board, point)) return ret;
		if (board.getEntity(point)==null ) {
			ret = moveList( board, nextPoint, vector);
			ret.add(point);
		}else if (board.getEntity(nextPoint).getSide() != side) {
			ret = new ArrayList<Point>();
			ret.add(point);
		}
		return ret;//same side
	}
	
	public boolean move(Board board , Point p) { // all Entity's move are the same.
		for (Point moveablePoint : this.moveList(board)) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				board.remove(p);
				return true;
			}
		}
		return false;
	}

	public Point[] eatList(Board board) { //all Entity's canEat() are the same.
		ArrayList<Point> eatablePoint = new ArrayList<Point>();
		for (Point moveablePoint : this.moveList(board)) {
			if (board.getEntity(moveablePoint) != null
					&& board.getEntity(moveablePoint).getSide() != this.side) {
				eatablePoint.add(moveablePoint);
			}
		}
		return (Point[]) eatablePoint.toArray();
	}
	
	public boolean outOfBoard(Board board,Point P) {
		if (P.x < board.getHeight() && P.x >= 0) return false;
		if (P.y < board.getWidth() && P.y >= 0) return false;
		return true;
	}
	public void remove() {// ----------------------------------------

	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public Side getSide() {
		return side;
	}

}
