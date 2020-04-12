package entity;

import java.awt.Point;
import java.util.ArrayList;
import entity.base.Entity;
import logic.*;
import game.base.Board;

public class Knight extends Entity {
	private boolean isFromPawn;

	public Knight(Point p, Side side) {
		super(p, side);
		isFromPawn = false;
	}

	public Knight(Point p, Side side, Boolean isFromPawn) {
		super(p, side);
		this.isFromPawn = isFromPawn;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_KNIGHT;
		}
		return Sprites.W_KNIGHT;
	}

	@Override
	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : Board.getKnightWalk()) {
			Point check = new Point(this.point.x + p.x, this.point.y + p.y);
			if (!board.isInBoard(check)) {
				continue;
			}
			if (board.getEntity(check) == null || board.getEntity(check).getSide() != side) {
				returnPoint.add(check);
			}
		}
		return returnPoint;
	}

	public boolean isFromPawn() {
		return isFromPawn;
	}
	
	public String toString() {
		return "Knight [point=" + point + ", side=" + side + "]";
	}

	@Override
	public String getHighlightSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.HIGHLIGHT_B_KNIGHT;
		}
		return Sprites.HIGHLIGHT_W_KNIGHT;
	}
}
