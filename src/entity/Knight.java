package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import logic.Side;
import game.base.Board;

public class Knight extends Entity {

	public Knight(Point p, Side side) {
		super(p, side);
	}

	public String getSymbol() {
		return (this.side == Side.BLACK) ? Sprites.B_KNIGHT : Sprites.W_KNIGHT;
	}

	public String getHighlightSymbol() {
		return (this.side == Side.BLACK) ? Sprites.HIGHLIGHT_B_KNIGHT : Sprites.HIGHLIGHT_W_KNIGHT;
	}

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

	public String toString() {
		return "Knight [point=" + point + ", side=" + side + "]";
	}

}
