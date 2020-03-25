package game.base;

import logic.*;
import entity.base.Entity;
import java.awt.*;

public abstract class GameController {
	protected static Board board;
	protected static Side turn;
	
	public static void IntializeMap(String[][] map,int px,int py) {
		turn = Side.WHITE;
		board = new Board(map);
	}
	
	public static Side isGameWin() {
		if (isGameWin(Side.BLACK)) return Side.BLACK;
		else if (isGameWin(Side.WHITE)) return Side.WHITE;
		return null;
	}
	
	public static boolean isGameWin(Side side) {// have to Override
		return false;
	}
	
	public static boolean isCheck(Side side) {
		Entity king = board.getKing(getAnotherSide(side));
		if (king == null) return true;
		Point pointKing = king.getP();
		return canEat(side,pointKing);
	}
	
	public static Point[] moveList(Point p) {
		return board.getEntity(p).moveList(board);
	}
	
	public static boolean canEat(Side side, Point point) {// Ex for black: white can eat this point?
		Entity[] allEntity = board.getAllPieces(side);
		for (Entity e : allEntity) {
			Point[] eatablePoint = e.eatList(board);
			for (Point p : eatablePoint) {
				if(p.x==point.x && p.y==point.y) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isTurn(Point p, Side turn) {
		if (p == null) {
			System.out.println("Don't have this point.");
			return false;
		}if(board.getEntity(p)==null) {
			System.out.println("It is empty.");
			return false;
		}if (board.getEntity(p).getSide()!=turn) {
			System.out.println("It's not you piece.");
			return false;
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
	
	public static Point[] click(Point p) {
		if (board.getEntity(p)!=null) {
			return board.getEntity(p).moveList(board);
		}
		return null;
	}
	
	public static boolean move(Point p1,Point p2) { //move from p1 to p2
		return board.getEntity(p1).move(board,p2);
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
