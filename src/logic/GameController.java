package logic;

import game.*;
import game.base.Board;
import game.base.Games;
import myException.IsPromotingException;
import myException.NullEntityException;
import myException.NullPointException;
import myException.WrongPieceException;
import java.awt.*;
import java.util.*;

import application.AppManager;

public abstract class GameController {
	protected static Board board;
	protected static Side turn;
	protected static Point size;
	protected static Point promotionPoint;
	protected static Side promotionSide;
	private static final String[] blackRow = {"BRook","BKnight","BBishop","BQueen","BKing","BBishop","BKnight","BRook"};
	private static final String[] blackPawn = {"BPawn","BPawn","BPawn","BPawn","BPawn","BPawn","BPawn","BPawn"};
	private static final String[] blank = {Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK};
	private static final String[] fourWhitePawn = {Sprites.BLANK,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.BLANK,Sprites.BLANK,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.BLANK};
	private static final String[] whitePawn = {"WPawn","WPawn","WPawn","WPawn","WPawn","WPawn","WPawn","WPawn"};
	private static final String[] whiteRow = {"WRook","WKnight","WBishop","WQueen","WKing","WBishop","WKnight","WRook"};
	private static final String[][] normalBoard = {blackRow, blackPawn, blank, blank, blank, blank, whitePawn, whiteRow};
	private static final String[][] hordeBoard = {blackRow, blackPawn, blank, fourWhitePawn, whitePawn, whitePawn, whitePawn, whitePawn};
	private static final String[][] noPawnBoard = {blackRow, blank, blank, blank, blank, blank, blank, whiteRow};
	
	public static void InitializeMap(String gameType) {
		promotionPoint = null;
		String[][] map = normalBoard;
		switch (gameType) {
		case Games.NORMAL:
			//map = noPawnBoard;
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
	public static Cell[][] getDisplayCellMap(){
		return board.getCellMap();
	}
	public static boolean isWin() {
		return board.isWin(turn);
	}
	public static boolean isDraw() {
		return board.isDraw(turn);
	}

	public static ArrayList<Point> moveList(Point p) {
		return board.moveList(p);
	}
	
	public static void setPromotion(Point point, Side side) {
		promotionPoint=point;
		promotionSide=side;
	}
	
	public static boolean isPromotion() {
		return !(promotionPoint==null);
	}
	
	public static void promotion(String piece){
		board.promotion(promotionPoint, promotionSide, piece);
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
		if (AppManager.getBoardPane().isPromoted()) {
			throw new IsPromotingException("Please select your promotion");
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
	public static void printPointList(ArrayList<Point> pointList) {
		for (Point point : pointList) {
			System.out.print(print(point));
		}
		System.out.println();
	}
	public static String print(Point p) {//for debug
		String pp=""+p;
		if (pp == "null") return pp;
		int s1,s2,s3,s4;
		s1=17;
		s2=pp.indexOf(",",s1);
		s3=s2+3;
		s4=pp.indexOf("]",s3);
		return "("+pp.substring(s1,s2)+","+pp.substring(s3,s4)+")";
	}
}
