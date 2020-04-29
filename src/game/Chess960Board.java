package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.King;
import entity.Rook;
import entity.base.Entity;
import entity.base.HaveCastling;
import logic.GameController;
import logic.Side;

public class Chess960Board extends NormalBoard {
	public Chess960Board(String[][] map) {
		super(map);
	}

	// 01234567
	// black r---k--r
	// ..kr-rk.
	// white r---k--r
	public ArrayList<Point> castingPoint(Side side) {// finished
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Entity king = getKing(side);
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
		if (isRightCastling(king, side, rightRook) != null)
			returnPoint.add(isRightCastling(king, side, rightRook));
		if (isLeftCastling(king, side, leftRook) != null)
			returnPoint.add(isLeftCastling(king, side, leftRook));
		return returnPoint;
	}

	public boolean isCastlingPoint(Side side, Point point) {// finished
		Entity entity = getEntity(point);
		return (entity instanceof Rook && entity.getSide() == side);
	}

	public int min(int i1, int i2, int i3, int i4) {
		return Math.min(i1, Math.min(i2, Math.min(i3, i4)));
	}

	public int max(int i1, int i2, int i3, int i4) {
		return Math.max(i1, Math.max(i2, Math.max(i3, i4)));
	}

	public boolean isFree(Point start, Point stop, Side side) {// king -> king
		for (int i = Math.min(start.y, start.y); i <= Math.max(start.y, start.y); i++) {
			Point point = new Point(start.x, i);
			if (isEatenPoint(point, side)) {
				return false;
			}
		}
		return true;
	}

	public boolean isNull(Point oldKing, Point oldRook, Point newKing, Point newRook) {
		if (oldKing.x != oldRook.x && oldRook.x != newKing.x && newKing.x != newRook.x) {
			System.out.println("error");
		}
		for (int i = min(oldKing.y, oldRook.y, newKing.y, newRook.y); i <= max(oldKing.y, oldRook.y, newKing.y,
				newRook.y); i++) {
			Point point = new Point(oldKing.x, i);
			if (point.equals(oldRook) || point.equals(oldKing))
				continue;
			if (getEntity(point) != null)
				return false;
		}
		return true;
	}

	public Point isRightCastling(Entity king, Side side, Point rightRook) {
		if (rightRook == null)
			return null;
		int s = (side == Side.BLACK) ? 0 : 7;
		Point newKing = new Point(s, 6);
		Point newRook = new Point(s, 5);
		if (isNull(king.getPoint(), rightRook, newKing, newRook) && isFree(king.getPoint(), newKing, side)) {
			return rightRook;
		}
		return null;
	}

	public Point isLeftCastling(Entity king, Side side, Point leftRook) {
		if (leftRook == null)
			return null;
		int s = (side == Side.BLACK) ? 0 : 7;
		Point newKing = new Point(s, 2);
		Point newRook = new Point(s, 3);
		boolean a1 = isNull(king.getPoint(), leftRook, newKing, newRook);
		boolean a2 = isFree(king.getPoint(), newKing, side);
		if (a1 && a2) {
			return leftRook;
		}
		return null;
	}

//  01234567
//black r---k--r
//      ..kr-rk.
//white r---k--r
	public void castling(Side side, Point oldPoint, Point newPoint) {
		Entity moveEntity = getEntity(oldPoint);
		Entity rook = getEntity(newPoint);
		remove(oldPoint);
		remove(newPoint);
		Point moveKing, moveRook;
		int s = (side == Side.BLACK) ? 0 : 7;
		if (oldPoint.y > newPoint.y) {
			moveKing = new Point(s, 2);
			moveRook = new Point(s, 3);
		} else {
			moveKing = new Point(s, 6);
			moveRook = new Point(s, 5);
		}
		moveEntity.setPoint(moveKing);
		addEntity(moveEntity, moveKing);
		rook.setPoint(moveRook);
		addEntity(rook, moveRook);
	}

}
