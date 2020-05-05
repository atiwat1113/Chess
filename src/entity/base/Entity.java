package entity.base;

import java.awt.*;
import java.util.ArrayList;
import game.base.Board;
import logic.Side;

public abstract class Entity { // private --> protected
	protected Point point;
	final protected Side side;

	public Entity(Point point, Side side) {// Point (y,x)
		this.point = point;
		this.side = side;
	}

	public abstract String getSymbol();

	public abstract String getHighlightSymbol();

	public abstract ArrayList<Point> moveList(Board board);

	protected ArrayList<Point> moveList(Board board, Point point, Point vector) {
		// add movePoint in vector direction for rook, bishop and queen
		ArrayList<Point> returnList = new ArrayList<Point>();
		Point nextPoint = Board.addPoint(point, vector);
		if (!board.isInBoard(nextPoint)) {
			return returnList;
		}
		if (board.getEntity(nextPoint) == null) {
			returnList = moveList(board, nextPoint, vector);
			returnList.add(nextPoint);
			return returnList;
		} else if (board.getEntity(nextPoint).getSide() == side) {
			return returnList;
		} else if (board.getEntity(nextPoint).getSide() != side) {
			returnList.add(nextPoint);
			return returnList;
		}
		System.out.println("Error");
		return null;
	}

	public Point getPoint() {
		return point;
	}

	public Side getSide() {
		return side;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public abstract String toString();
}
