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

	public abstract Point getSymbol();

	public abstract ArrayList<Point> moveList(Board board);
	
	public ArrayList<Point> moveList(Board board, Point point, Point vector) {//------
		ArrayList<Point> ret = new ArrayList<Point>();
		Point nextPoint = new Point(point.x + vector.x, point.y + vector.y);
<<<<<<< HEAD
<<<<<<< HEAD
		if (nextPoint==null || !isInBoard(board, nextPoint)) ret.add(point);
		else if (board.getEntity(point)==null ) {
||||||| ff0bc55
		if (outOfBoard(board, point)) return ret;
		if (board.getEntity(point)==null ) {
=======
		if (nextPoint==null || outOfBoard(board, nextPoint)) ret.add(point);
		else if (board.getEntity(point)==null ) {
>>>>>>> cfeafa9424e13860b36f1ece38f0c692d563b9c1
||||||| ff0bc55
		if (outOfBoard(board, point)) return ret;
		if (board.getEntity(point)==null ) {
=======
		if (nextPoint==null || outOfBoard(board, nextPoint)) ret.add(point);
		else if (board.getEntity(point)==null ) {
>>>>>>> cfeafa9424e13860b36f1ece38f0c692d563b9c1
			ret = moveList( board, nextPoint, vector);
			ret.add(point);
		}else if (board.getEntity(nextPoint).getSide() != side) {
			ret.add(point);
			ret.add(nextPoint);
		}
		return ret;//same side
	}
		
	public ArrayList<Point> eatList(Board board) { 
		ArrayList<Point> eatablePoint = new ArrayList<Point>();
		for (Point moveablePoint : this.moveList(board)) {
			if (board.getEntity(moveablePoint) != null &&
				board.getEntity(moveablePoint).getSide() != this.side) {
				eatablePoint.add(moveablePoint);
			}
		}
		return eatablePoint;
	}
	
	public boolean move(Board board , Point p) throws NullPointException { 
		if (p==null) throw new NullPointException("");
		Point oldPoint = this.p;
		for (Point moveablePoint : this.moveList(board)) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				board.remove(oldPoint);
				return true;
			}
		}
		return false;
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
