package logic;

import java.awt.*;
import game.base.Board;
import game.base.Games;

import java.util.ArrayList;
import java.util.Scanner;
import logic.*;
import entity.base.Entity;
import myException.*;

public class PrintBoard {
	
	private static final String[] blackRow = {Sprites.B_ROOK,Sprites.B_KNIGHT,Sprites.B_BISHOP,Sprites.B_QUEEN,Sprites.B_KING,Sprites.B_BISHOP,Sprites.B_KNIGHT,Sprites.B_ROOK};
	private static final String[] blackPawn = {Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN,Sprites.B_PAWN};
	private static final String[] blank = {Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK,Sprites.BLANK};
	private static final String[] whitePawn = {Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN,Sprites.W_PAWN};
	private static final String[] whiteRow = {Sprites.W_ROOK,Sprites.W_KNIGHT,Sprites.W_BISHOP,Sprites.W_QUEEN,Sprites.W_KING,Sprites.W_BISHOP,Sprites.W_KNIGHT,Sprites.W_ROOK};
	private static final String[][] normalBoard = {blackRow, blackPawn, blank, blank, blank, blank, whitePawn, whiteRow};
	private static final String[][] noPawnBoard = {blackRow, blank, blank, blank, blank, blank, blank, whiteRow};
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		GameController.InitializeMap(normalBoard, Games.NORMAL);// ---------------------------------
		print(GameController.getBoard());
		while (true) {
			System.out.print("" + GameController.getTurn() + " turn ");
			String walkfrom = kb.nextLine();
			if (GameController.isWin()) {
				System.out.println("" + GameController.getTurn() + " win");
				break;
			}
			if (walkfrom.charAt(0) == 'q')
				break;
			if (walkfrom.charAt(0) == 'p')
				print(GameController.getBoard());
			if (stringToPoint(walkfrom) == null) {
				System.out.println("Don't have this point.\nPick again!!");
				continue;
			}
			try {
				GameController.isTurn(stringToPoint(walkfrom), GameController.getTurn());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Pick again!!");
				continue;
			}
			Point piece = stringToPoint(walkfrom);
			print(GameController.getBoard());
			ArrayList<Point> moveList = GameController.moveList(piece);
			printMove(moveList);

			String walkTo = kb.nextLine();
			if (stringToPoint(walkTo) == null) {
				System.out.println("Don't have this point.\nPick again!!");
				continue;
			}
			Point moveTo = stringToPoint(walkTo);
			if (!GameController.move(piece, moveTo)) {
				System.out.println("Pick again!!");
				continue;
			}
			if (GameController.isWin()) {// --------------------------
				System.out.println("" + GameController.getAnotherSide(GameController.getTurn()) + "Check!!");
			}
			GameController.nextTurn();
			System.out.println("Next turn");
			print(GameController.getBoard());
		}
	}

	public static void printMove(ArrayList<Point> pointList) {
		String s1 = "abcdefgh";
		String s2 = "87654321";
		System.out.print("Move to : ");
		for (Point p : pointList) {
			System.out.print("(" + s1.charAt(p.y) + s2.charAt(p.x) + ") ");
		}
		System.out.println();
	}

	public static void print(Board board) {
		System.out.println("  -a---b---c---d---e---f---g---h--");
		for (int i = 0; i < 8; i++) {
			System.out.print("" + (8 - i) + " ");
			for (int j = 0; j < 8; j++) {
				if (board.getEntity(new Point(i, j)) == null) {
					System.out.print("---|");
					continue;
				}
				String pr = board.getEntity(new Point(i, j)).getSymbol().substring(0, 4);
				if (pr.substring(2, 4).equals("Kn")) pr=pr.substring(0, 2)+"N";
				System.out.print(pr.substring(0, 3) + "|");
			}
			System.out.println();
		}
	}

	public static Point stringToPoint(String str) {
		if (str.length() != 2)
			return null;
		String s1 = "abcdefgh";
		String s2 = "87654321";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// System.out.println(""+s1.charAt(i)+s2.charAt(j));
				if (s1.charAt(j) == str.charAt(0) && s2.charAt(i) == str.charAt(1))
					return new Point(i, j);
			}
		}
		return null;
	}
	/*
	 * public static boolean move(Point piece, Point moveTo, Board board) { Point[]
	 * canMoveList = board.getEntity(piece).moveList(board); for(Point p :
	 * canMoveList) { if(p.x==moveTo.x && p.y==moveTo.y) {
	 * board.getEntity(piece).move(board,moveTo); return true; } } return false; }
	 */
}
