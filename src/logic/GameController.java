package logic;

import game.*;
import game.base.*;
import myException.IsPromotingException;
import myException.WrongPieceException;
import java.awt.*;
import java.util.*;
import Resource.Sprites;

public class GameController {
	private static Board board;
	private static Side turn;
	private static Point size;
	private static Point promotionPoint;
	private static Side promotionSide;
	private static final String[] blackRow = { "BRook", "BKnight", "BBishop", "BQueen", "BKing", "BBishop", "BKnight",
			"BRook" };
	private static final String[] blackPawn = { "BPawn", "BPawn", "BPawn", "BPawn", "BPawn", "BPawn", "BPawn",
			"BPawn" };
	private static final String[] blank = { Sprites.BLANK, Sprites.BLANK, Sprites.BLANK, Sprites.BLANK, Sprites.BLANK,
			Sprites.BLANK, Sprites.BLANK, Sprites.BLANK };
	private static final String[] fourWhitePawn = { Sprites.BLANK, "WPawn", "WPawn", Sprites.BLANK, Sprites.BLANK,
			"WPawn", "WPawn", Sprites.BLANK };
	private static final String[] whitePawn = { "WPawn", "WPawn", "WPawn", "WPawn", "WPawn", "WPawn", "WPawn",
			"WPawn" };
	private static final String[] whiteRow = { "WRook", "WKnight", "WBishop", "WQueen", "WKing", "WBishop", "WKnight",
			"WRook" };
	private static final String[][] normalBoard = { blackRow, blackPawn, blank, blank, blank, blank, whitePawn,
			whiteRow };
	private static final String[][] hordeBoard = { blackRow, blackPawn, blank, fourWhitePawn, whitePawn, whitePawn,
			whitePawn, whitePawn };

	public static void InitializeMap(String gameType) {
		promotionPoint = null;
		String[][] map = normalBoard;
		turn = Side.WHITE;
		size = new Point(map.length, map[0].length);
		switch (gameType) {
		case Games.NORMAL:
			board = new NormalBoard(map);
			break;
		case Games.ATOMIC:
			board = new AtomicBoard(map);
			break;
		case Games.KINGOFTHEHILL:
			board = new KingOfTheHillBoard(map);
			break;
		case Games.THREECHECK:
			board = new ThreeCheckBoard(map);
			break;
		case Games.CHESS960:
			int[] key = randomKey();
			String[] randomBlack = { blackRow[key[0]], blackRow[key[1]], blackRow[key[2]], blackRow[key[3]],
					blackRow[key[4]], blackRow[key[5]], blackRow[key[6]], blackRow[key[7]] };
			String[] randomWhite = { whiteRow[key[0]], whiteRow[key[1]], whiteRow[key[2]], whiteRow[key[3]],
					whiteRow[key[4]], whiteRow[key[5]], whiteRow[key[6]], whiteRow[key[7]] };
			// write random pieces--------------------------------------------
			String[][] randomBoard = { randomBlack, blackPawn, blank, blank, blank, blank, whitePawn, randomWhite };
			map = randomBoard;
			board = new Chess960Board(map);
			break;
		case Games.HORDE:
			map = hordeBoard;
			board = new HordeBoard(map);
			break;
		default:
			System.out.println("wrong game type");
		}
	}

	public static int[] randomKey() {// "BRook","BKnight","BBishop","BQueen","BKing"
		// for random map in chess960
		int[] returnKey = { -1, -1, -1, -1, -1, -1, -1, -1 };
		Random ran = new Random();
		int ranInt;
		ranInt = ran.nextInt(4);
		returnKey[ranInt * 2] = 2;
		ranInt = ran.nextInt(4);
		returnKey[ranInt * 2 + 1] = 2;
		ranInt = ran.nextInt(6);
		returnKey = put(returnKey, 3, ranInt);
		ranInt = ran.nextInt(5);
		returnKey = put(returnKey, 1, ranInt);
		ranInt = ran.nextInt(4);
		returnKey = put(returnKey, 1, ranInt);
		returnKey = put(returnKey, 0, 0);
		returnKey = put(returnKey, 4, 0);
		returnKey = put(returnKey, 0, 0);
		return returnKey;
	}

	public static int[] put(int[] l, int value, int index) {
		// for randomKey
		int c = -1;
		for (int i = 0; i < size.y; i++) {
			if (l[i] == -1)
				c++;
			if (c == index) {
				l[i] = value;
				break;
			}
		}
		return l;
	}

	public static Cell[][] getDisplayCellMap() {
		return board.getCellMap();
	}

	// win, draw and check
	public static boolean isWin() {
		return board.isWin(turn);
	}

	public static boolean isDraw() {
		return board.isDraw(turn);
	}

	public static boolean isCheck() {
		if (board instanceof CheckMateAble)
			return ((CheckMateAble) board).isCheck(turn);
		return false;
	}

	// move
	public static void startAnimation(Point oldPoint, Point newPoint) {
		board.startAnimation(oldPoint, newPoint);
	}

	public static void continueMove() {
		board.continueMove();
	}

	// moveList
	public static ArrayList<Point> moveList(Point p) {
		return board.moveList(p);
	}

	// promotion
	public static void setPromotion(Point point, Side side) {
		promotionPoint = point;
		promotionSide = side;
	}

	public static boolean isPromotion() {
		return !(promotionPoint == null);
	}

	public static void promotion(String piece) {
		board.promotion(promotionPoint, promotionSide, piece);
		promotionPoint = null;
	}

	// turn
	public static boolean isTurn(Point p, Side turn) throws Exception {
		if (board.getEntity(p).getSide() != turn) {
			throw new WrongPieceException("It's not your piece.");
		}
		if (isPromotion()) {
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

	public static Side getTurn() {
		return turn;
	}

	// other
	public static Side getAnotherSide(Side side) {
		if (side == Side.WHITE)
			return Side.BLACK;
		return Side.WHITE;
	}

	public static int[] getCheckNumber() {
		if (board instanceof ThreeCheckBoard) {
			return ((ThreeCheckBoard) board).getCheckNumber();
		}
		return null;
	}
}
