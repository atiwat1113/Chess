package logic;

import game.*;
import game.base.Board;
import game.base.CheckMateAble;
import game.base.Games;
import myException.IsPromotingException;
import myException.NullEntityException;
import myException.NullPointException;
import myException.WrongPieceException;
import java.awt.*;
import java.util.*;
import java.util.Random;

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
	private static final String[] fourWhitePawn = {Sprites.BLANK,"WPawn","WPawn",Sprites.BLANK,Sprites.BLANK,"WPawn","WPawn",Sprites.BLANK};
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
			board = new AtomicBoard(map);
			break;
		case Games.KINGOFTHEHILL:
			board = new KingOfTheHillBoard(map);
			break;
		case Games.THREECHECK:
			board = new OtherBoard(map);
			break;
		case Games.ANTICHESS:
			board = new OtherBoard(map);
			break;
		case Games.CHESS960:
			int[] key = randomKey();
			String[] randomBlack = {blackRow[key[0]], blackRow[key[1]], blackRow[key[2]], blackRow[key[3]], blackRow[key[4]], blackRow[key[5]], blackRow[key[6]], blackRow[key[7]]};
			String[] randomWhite = {whiteRow[key[0]], whiteRow[key[1]], whiteRow[key[2]], whiteRow[key[3]], whiteRow[key[4]], whiteRow[key[5]], whiteRow[key[6]], whiteRow[key[7]]};
			//write random pieces--------------------------------------------
			String[][] randomBoard = {randomBlack, blackPawn, blank, blank, blank, blank, whitePawn, randomWhite};
			map = randomBoard;
			board = new Chess960Board(map);
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
	public static int[] randomKey() {//"BRook","BKnight","BBishop","BQueen","BKing"
		int[] returnKey = {-1,-1,-1,-1,-1,-1,-1,-1};
		Random ran = new Random();
		int ranInt;
		ranInt = ran.nextInt(4);
		returnKey[ranInt*2]=2;
		ranInt = ran.nextInt(4);
		returnKey[ranInt*2+1]=2;
		ranInt = ran.nextInt(6);
		put(returnKey,3,ranInt);
		ranInt = ran.nextInt(5);
		put(returnKey,1,ranInt);
		ranInt = ran.nextInt(4);
		put(returnKey,1,ranInt);
		put(returnKey,0,0);
		put(returnKey,4,0);
		put(returnKey,0,0);
		return returnKey;
	}
	public static int[] put(int[] l, int value, int index) {
		int c = -1;
		for (int i = 0; i<size.y; i++) {
			if (l[i]==-1) c++;
			if (c==index) l[i]=value;
		}
		return l;
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
	public static boolean isCheck() {
		if (board instanceof CheckMateAble) return ((CheckMateAble) board).isCheck(turn);
		return false;
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
			throw new IsPromotingException("Select your promotion");
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

	public static void move(Point p1, Point p2) {// move from p1 to p2
		for (Point movePoint : moveList(p1)) {
			if (movePoint.equals(p2)) {
				board.move(p1, p2);
			}
		}
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
