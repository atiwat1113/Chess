package entity;

import java.awt.Point;
import java.util.ArrayList;

import Resource.Sprites;
import entity.base.Entity;
import entity.base.HaveCastling;
import logic.Side;
import game.base.Board;

public class King extends Entity implements HaveCastling {
	private boolean neverMove;// for castling

	public King(Point p, Side side) {
		super(p, side);
		neverMove = true;
	}

	public String getSymbol() {
		return (this.side == Side.BLACK) ? Sprites.B_KING : Sprites.W_KING;
	}

	public String getHighlightSymbol() {
		return (this.side == Side.BLACK) ? Sprites.HIGHLIGHT_B_KING : Sprites.HIGHLIGHT_W_KING;
	}

	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : Board.getKingWalk()) {
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
		return "King [point=" + point + ", side=" + side + "]";
	}

	// getter and setter
	public void setNeverMove() {
		this.neverMove = false;
	}

	public boolean isNeverMove() {
		return neverMove;
	}

}
