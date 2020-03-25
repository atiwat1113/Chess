package game.base;

import logic.*;
import entity.base.Entity;
import java.awt.*;

public abstract class GameController {
	protected static Board board;
	protected static Side side;
	
	public static void IntializeMap(String[][] map,int px,int py) {
		side = Side.WHITE;
		board = new Board(map);
	}
	
	public static boolean isGameWin(Side side) {// have to Override
		return true;
	}
	
	public static boolean isCheck(Side side) {
		Entity king = board.getKing(getAnotherSide(side));
		if (king == null) return true;
		Point pointKing = king.getP();
		return canEat(side,pointKing);
	}
	
	public static boolean canEat(Side side, Point point) {// null point also can eat
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
	
	public static void nextTurn() {
		if (side == Side.BLACK) {
			side = Side.WHITE;
		}else {
			side = Side.BLACK;
		}
	}
	
	public static Point[] click(Point p) {
		if (board.getEntity(p)!=null) {
			return board.getEntity(p).moveList();
		}
		return null;
	}
	
	public static boolean move(Point p1,Point p2) { //move from p1 to p2
		if (click(p1)==null) return false;
		// move ------------------------------------------------------------------------------------
		return true;
	}
	
	public static Board getBoard() {
		return board;
	}

	public static Side getSide() {
		return side;
	}
	
	public static Side getAnotherSide(Side side) {
		if (side == Side.WHITE) return Side.BLACK;
		return Side.WHITE;
	}
}
