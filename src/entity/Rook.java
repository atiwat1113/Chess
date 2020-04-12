package entity;

import java.awt.Point;
import java.util.ArrayList;
import entity.base.*;
import logic.*;
import game.base.Board;

public class Rook extends Entity implements HaveCastling {

	private boolean neverMove;
	private boolean isFromPawn;

	public Rook(Point p, Side side) {
		super(p, side);
		this.neverMove = true;
		isFromPawn = false;
	}

	public Rook(Point p, Side side, Boolean isFromPawn) {
		super(p, side);
		this.neverMove = true;
		this.isFromPawn = isFromPawn;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.B_ROOK;
		}
		return Sprites.W_ROOK;
	}

	@Override
	public ArrayList<Point> moveList(Board board) {
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		for (Point p : moveList(board, point, new Point(1, 0)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(0, 1)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(-1, 0)))
			returnPoint.add(p);
		for (Point p : moveList(board, point, new Point(0, -1)))
			returnPoint.add(p);
		return returnPoint;
	}

	public boolean isFromPawn() {
		return isFromPawn;
	}

	public String toString() {
		return "Rook [point=" + point + ", side=" + side + "]";
	}
	
	public boolean isNeverMove() {
		return neverMove;
	}
	public void setNeverMove() {
		this.neverMove = false;
	}

	@Override
	public String getHighlightSymbol() {
		// TODO Auto-generated method stub
		if (this.side == Side.BLACK) {
			return Sprites.HIGHLIGHT_B_ROOK;
		}
		return Sprites.HIGHLIGHT_W_ROOK;
	}
}
