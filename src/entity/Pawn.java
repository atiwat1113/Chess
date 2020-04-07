package entity;

import java.awt.Point;
import java.util.ArrayList;
import entity.base.Entity;
import logic.*;
import game.base.Board;

public class Pawn extends Entity {
	private boolean twoMove;

	public Pawn(Point p, Side side) {
		super(p, side);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_PAWN;
		}
		return Sprites.W_PAWN;
	}

	@Override
	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		if (side == Side.WHITE) {
			Point nextPoint = new Point(point.x - 1, point.y);
			if (point.x == 0) {
				System.out.println("Error Pawn");
				return returnPoint;
			} else if (board.getEntity(nextPoint) != null) {
				return returnPoint;
			}
			if (point.x == 6) {
				returnPoint.add(new Point(4, point.y));
			}
			returnPoint.add(nextPoint);
		} else {
			Point nextPoint = new Point(point.x + 1, point.y);
			if (point.x == 7) {
				System.out.println("Error Pawn");
				return returnPoint;
			} else if (board.getEntity(nextPoint) != null) {
				return returnPoint;
			}
			if (point.x == 1) {
				returnPoint.add(new Point(3, point.y));
			}
			returnPoint.add(nextPoint);
		}
		for (Point p : eatList(board)) {
			returnPoint.add(p);
		}
		return returnPoint;
	}

	
	public ArrayList<Point> eatList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Point[] eatPoint = new Point[2];
		if (side == Side.WHITE) {
			eatPoint[0] = new Point(point.x - 1, point.y + 1);
			eatPoint[1] = new Point(point.x - 1, point.y - 1);
		} else {
			eatPoint[0] = new Point(point.x + 1, point.y + 1);
			eatPoint[1] = new Point(point.x + 1, point.y - 1);
		}
		for (Point pp : eatPoint) {
			if (!board.isInBoard(pp))
				continue;
			if (board.getEntity(pp) == null)
				continue;
			if (board.getEntity(pp).getSide() == GameController.getAnotherSide(side)) {
				returnPoint.add(pp);
			}
		}
		return returnPoint;
	}
	
	public String toString() {
		return "Pawn [point=" + point + ", side=" + side + "]";
	}
}
