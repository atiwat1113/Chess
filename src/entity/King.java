package entity;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.*;
import game.base.Board;

public class King extends Entity {
	private boolean neverMove;// for castling

	public King(Point p, Side side) {
		super(p, side);
		neverMove = true;
	}

	@Override
	public Point getSymbol() {
		if (this.side == Side.BLACK) {
			return Sprites.B_KING;
		}
		return Sprites.W_KING;
	}

	@Override
	public ArrayList<Point> moveList(Board board) {

		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : board.getKingWalk()) {
			Point check = new Point(this.p.x + p.x, this.p.y + p.y);
			if (!board.isInBoard(check)) {
				continue;
			}
			if (board.getEntity(check) == null || board.getEntity(check).getSide() != side) {
				returnPoint.add(check);
			}
		}
		return returnPoint;
	}

}
