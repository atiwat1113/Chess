package logic;

import entity.base.Entity;
import game.base.*;
import myException.NullEntityException;
import myException.NullPointException;
import myException.WrongPieceException;
import java.awt.*;
import java.util.*;

public abstract class GameController {
	protected static Board board;
	protected static Side turn;
	protected static Point size;

	public static void InitializeMap(String[][] map) {
		turn = Side.WHITE;
		size = new Point(map.length,map[0].length);
		board = new NormalBoard(map);
	}

	public static boolean isWin() {
		return board.isWin(turn);
	}

	public static ArrayList<Point> moveList(Point p) {
		return board.moveList(p);
	}

	public static boolean isTurn(Point p, Side turn) throws Exception {// ----------------------throw exception
		if (p == null) {
			throw new NullPointException("Don't have this point.");
		}
		if (board.getEntity(p) == null) {
			throw new NullEntityException("It is empty.");
		}
		if (board.getEntity(p).getSide() != turn) {
			throw new WrongPieceException("It's not your piece.");
		}
		return true;
	}

	public static void nextTurn() {
		if (turn == Side.BLACK) {
			turn = Side.WHITE;
		} else {
			turn = Side.BLACK;
		}
	}

	public static boolean move(Point p1, Point p2) {// move from p1 to p2
		return board.move(p1, p2, moveList(p1));
	}

	public static Board getBoard() {
		return board;
	}

	public static Side getTurn() {
		return turn;
	}

	public static Side getAnotherSide(Side side) {
		if (side == Side.WHITE)
			return Side.BLACK;
		return Side.WHITE;
	}
}
