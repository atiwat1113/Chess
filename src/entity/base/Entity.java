package entity.base;

import java.awt.*;
import java.util.ArrayList;
import game.base.Board;
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
		ArrayList<Point> returnList = new ArrayList<Point>();
		Point nextPoint = addVector(point, vector);
		if (!board.isInBoard(nextPoint)) {
			return returnList;
		}
		if (board.getEntity(nextPoint) == null) {
			ArrayList<Point> pointList = moveList(board, nextPoint, vector);
			pointList.add(nextPoint);
			return pointList;
 		}else if (board.getEntity(nextPoint).getSide() == side) {
 			return returnList;
 		}
		else if (board.getEntity(nextPoint).getSide() != side) {
			returnList.add(nextPoint);
			return returnList;
		}
		System.out.println("Error");
		return null;
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
	
	/*public boolean moveEntity(Board board , Point p) { 
		Point oldPoint = this.p;
		for (Point moveablePoint : this.moveList(board)) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				board.remove(oldPoint);
				return true;
			}
		}
		return false;
	}*/
	
	public static Point addVector(Point p1, Point p2) {
		return new Point(p1.x+p2.x,p1.y+p2.y);
	}
	
	/*public void remove() {// ----------------------------------------

	}*/

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
