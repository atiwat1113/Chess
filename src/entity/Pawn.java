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
	public Point getSymbol() {
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
			Point nextPoint = new Point(p.x - 1, p.y);
			if (p.x == 0) {
				System.out.println("Error Pawn");
				return returnPoint;
			} else if (board.getEntity(nextPoint) != null) {
				return returnPoint;
			}
			if (p.x == 6) {
				returnPoint.add(new Point(4, p.y));
			}
			returnPoint.add(nextPoint);
		} else {
			Point nextPoint = new Point(p.x + 1, p.y);
			if (p.x == 7) {
				System.out.println("Error Pawn");
				return returnPoint;
			} else if (board.getEntity(nextPoint) != null) {
				return returnPoint;
			}
			if (p.x == 1) {
				returnPoint.add(new Point(3, p.y));
			}
			returnPoint.add(nextPoint);
		}
		for (Point p : eatList(board)) {
			returnPoint.add(p);
		}
		return returnPoint;
	}

	@Override
	public ArrayList<Point> eatList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Point[] point = new Point[2];
		if (side == Side.WHITE) {
			point[0] = new Point(p.x - 1, p.y + 1);
			point[1] = new Point(p.x - 1, p.y - 1);
		} else {
			point[0] = new Point(p.x + 1, p.y + 1);
			point[1] = new Point(p.x + 1, p.y - 1);
		}
		for (Point pp : point) {
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

}
