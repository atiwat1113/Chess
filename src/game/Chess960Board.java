package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.King;
import entity.Rook;
import entity.base.Entity;

import logic.Side;

public class Chess960Board extends NormalBoard {
	public Chess960Board(String[][] map) {
		super(map);
	}
	public boolean isCastlingPoint(Side side, Point point) {
		Entity entity = getEntity(point);
		return (entity instanceof Rook && entity.getSide() == side);
	}
	public ArrayList<Point> castingPoint(Side side) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Entity king = getKing(side);
		int s = (side == Side.BLACK) ? 0 : 7;
		if (!((King) king).isNeverMove()) {
			return returnPoint;
		}
		Point rightRook = null;
		Point leftRook = null;
		Point temp = king.getPoint();
		while (true) {
			temp = addPoint(temp, new Point(0, 1));
			if (!isInBoard(temp))
				break;
			if (getEntity(temp) instanceof Rook) {
				rightRook = temp;
				break;
			}
		}
		temp = king.getPoint();
		while (true) {
			temp = addPoint(temp, new Point(0, -1));
			if (!isInBoard(temp))
				break;
			if (getEntity(temp) instanceof Rook) {
				leftRook = temp;
				break;
			}
		}
		if (rightRook != null) {
			Point newKing = new Point(s, 6);
			Point newRook = new Point(s, 5);
			if (isNull(king.getPoint(), rightRook, newKing, newRook) && isFree(king.getPoint(), newKing, side)) {
				returnPoint.add(rightRook);
			}
		}
		if (leftRook != null) {
			Point newKing = new Point(s, 2);
			Point newRook = new Point(s, 3);
			if (isNull(king.getPoint(), leftRook, newKing, newRook) && isFree(king.getPoint(), newKing, side)) { 
				returnPoint.add(leftRook);
			}
		}
		return returnPoint;
	}
}
