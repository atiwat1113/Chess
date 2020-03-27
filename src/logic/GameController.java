package logic;

import entity.base.Entity;
import game.base.Board;
import myException.NullEntityException;
import myException.NullPointException;
import myException.WrongPieceException;

import java.awt.*;
import java.util.*;

public abstract class GameController {
	protected static Board board;
	protected static Side turn;
	
	public static void IntializeMap(String[][] map,int px,int py) {
		turn = Side.WHITE;
		board = new Board(map);
	}
	
	public static boolean isCheck(Side side) {
		Entity king = board.getKing(getAnotherSide(side));
		if (king == null) return true;
		Point pointKing = king.getP();
		return canBeEaten(side,pointKing);
	}
	
	public static ArrayList<Point> moveList(Point p) {
		return board.getEntity(p).moveList(board);
	}
	
	public static boolean canBeEaten(Side side, Point point) {// find good method name
		ArrayList<Entity> allEntity = board.getAllPieces(side);// Ex for black: white can eat this point?
		for (Entity e : allEntity) {
			ArrayList<Point> eatablePoint = e.eatList(board);
			for (Point p : eatablePoint) {
				if(p.x==point.x && p.y==point.y) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isTurn(Point p, Side turn) throws Exception{//----------------------throw exception
		if (p == null) {
			throw new NullPointException("Don't have this point.");
		}if(board.getEntity(p)==null) {
			throw new NullEntityException("It is empty.");
		}if (board.getEntity(p).getSide()!=turn) {
			throw new WrongPieceException("It's not your piece.");
		}
		return true;
	}
	
	public static void nextTurn() {
		if (turn == Side.BLACK) {
			turn = Side.WHITE;
		}else {
			turn = Side.BLACK;
		}
	}
	
	public static boolean move(Point p1,Point p2) { //move from p1 to p2
		return board.move(p1,p2);
	}
	
	public static Board getBoard() {
		return board;
	}

	public static Side getTurn() {
		return turn;
	}
	
	public static Side getAnotherSide(Side side) {
		if (side == Side.WHITE) return Side.BLACK;
		return Side.WHITE;
	}
}
