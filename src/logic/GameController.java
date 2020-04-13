package logic;

import entity.base.Entity;
import game.*;
import game.base.Board;
import game.base.Games;
import myException.NullEntityException;
import myException.NullPointException;
import myException.WrongPieceException;
import java.awt.*;
import java.util.*;

public abstract class GameController {
	protected static Board board;
	protected static Side turn;
	protected static Point size;
	private static final String[] blackRow = {Sprites.B_ROOK,Sprites.B_KNIGHT,Sprites.B_BISHOP,Sprites.B_QUEEN,Sprites.B_KING,Sprites.B_BISHOP,Sprites.B_KNIGHT,Sprites.B_ROOK};
	private static final String[] blackPawn = {Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN};
	private static final String[] blank = {Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK};
	private static final String[] fourWhitePawn = {Sprites.BLANK,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.BLANK,Sprites.BLANK,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.BLANK};
	private static final String[] whitePawn = {Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN};
	private static final String[] whiteRow = {Sprites.W_ROOK,Sprites.W_KNIGHT,Sprites.W_BISHOP,Sprites.W_QUEEN,Sprites.W_KING,Sprites.W_BISHOP,Sprites.W_KNIGHT,Sprites.W_ROOK};
	private static final String[][] normalBoard = {blackRow, blackPawn, blank, blank, blank, blank, whitePawn, whiteRow};
	private static final String[][] hordeBoard = {blackRow, blackPawn, blank, fourWhitePawn, whitePawn, whitePawn, whitePawn, whitePawn};
	
	//private static final String[][] noPawnBoard = {blackRow, blank, blank, blank, blank, blank, blank, whiteRow};
	
	public static void InitializeMap(String gameType) {
		String[][] map = normalBoard;
		switch (gameType) {
		case Games.NORMAL:
			board = new NormalBoard(map);
			break;
		case Games.ATOMIC:
			board = new OtherBoard(map);
			break;
		case Games.KINGOFTHEHILL:
			board = new OtherBoard(map);
			break;
		case Games.THREECHECK:
			board = new OtherBoard(map);
			break;
		case Games.ANTICHESS:
			board = new OtherBoard(map);
			break;
		case Games.CHESS960:
			String[] randomBlack, randomWhite;
			randomBlack = randomWhite = blank;
			//write random pieces--------------------------------------------
			String[][] randomBoard = {randomBlack, blackPawn, blank, blank, blank, blank, whitePawn, randomWhite};
			map = randomBoard;
			board = new OtherBoard(map);
			break;
		case Games.HORDE:
			map = hordeBoard;
			board = new OtherBoard(map);
			break;
		default:
			System.out.println("wrong game type");
		}
		turn = Side.WHITE;
		size = new Point(map.length,map[0].length);
	}
	public static Cell[][] getDisplayCellMap(boolean isRotate){
		if (turn == Side.WHITE || !isRotate) {
			return board.getCellMap();
		}
		Cell[][] returnCellMap = new Cell[size.x][size.y];
		Cell[][] normalCellMap = board.getCellMap();
		for (int i = 0; i<size.x; i++) {
			for(int j = 0;j<size.y; j++) {
				returnCellMap[i][j] = normalCellMap[7-i][7-j];
			}
		}
		return returnCellMap;
	}
	public static boolean isWin() {
		return board.isWin(turn);
	}

	public static ArrayList<Point> moveList(Point p, boolean isRotate) {
		if (turn == Side.WHITE || !isRotate) {
			return board.moveList(p);
		}
		ArrayList<Point> returnCellMap = new ArrayList<Point>();
		for (Point point : board.moveList(p)) {
			returnCellMap.add(new Point(7-point.x,7-point.y));
		}
		return returnCellMap;
	}

	public static boolean isTurn(Point p, Side turn) throws Exception {
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
		return board.move(p1, p2, moveList(p1,false));
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
