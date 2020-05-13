package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import entity.King;
import entity.Rook;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class Chess960Board extends Board implements CheckMateAble {
	/*
	 * this game is opening with random board and still have castling
	 * random board conditions
	 * 1. The bishops must be placed on opposite-color squares.
	 * 2. The king must be placed on a square between the rooks.
	 */

	public Chess960Board(String[][] map) {
		super(map);
	}

	// win
	public boolean isWin(Side side) {
		return winByCheckmate(side);
	}

	public boolean winByCheckmate(Side side) {
		Entity king = getKing(side);
		if (king == null)
			return false;
		if (!isEatenPoint(king.getPoint(), side)) {
			return false;
		}
		return drawCannotMove(side);
	}

	// draw
	public boolean isDraw(Side side) {
		return drawCannotMove(side);
	}

	public boolean drawCannotMove(Side side) {
		ArrayList<Entity> allEntity = getAllPieces(side);
		for (Entity entity : allEntity) {
			if (entity == null)
				continue;
			ArrayList<Point> moveList = moveList(entity.getPoint());
			if (moveList.size() != 0)
				return false;
		}
		return true;
	}

	// check
	public boolean isCheck(Side side) {
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}

	@Override
	public boolean isCastlingPoint(Side side, Point point) {
		Entity entity = getEntity(point);
		return (entity instanceof Rook && entity.getSide() == side);
	}

	@Override
	protected ArrayList<Point> castingPoint(Side side) {
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
