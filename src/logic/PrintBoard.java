package logic;

import java.awt.*;
import game.base.Board;
import java.util.ArrayList;
import java.util.Scanner;
import logic.*;
import entity.base.Entity;
import myException.*;

public class PrintBoard {
	private static String[] w_p = { "--", "WK", "WQ", "WB", "WN", "WR", "WP" };
	private static String[] b_p = { "--", "BK", "BQ", "BB", "BN", "BR", "BP" };

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String[][] nb = { { b_p[5], b_p[4], b_p[3], b_p[2], b_p[1], b_p[3], b_p[4], b_p[5] },
				{ b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6], b_p[6] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6], w_p[6] },
				{ w_p[5], w_p[4], w_p[3], w_p[2], w_p[1], w_p[3], w_p[4], w_p[5] } };
		String[][] ab = { { b_p[5], b_p[4], b_p[3], b_p[2], b_p[1], b_p[3], b_p[4], b_p[5] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0], b_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0], w_p[0] },
				{ w_p[5], w_p[4], w_p[3], w_p[2], w_p[1], w_p[3], w_p[4], w_p[5] } };
		GameController.IntializeMap(nb, 8, 8);// ---------------------------------
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
			printMove(GameController.moveList(piece));

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
			if (GameController.isCheck()) {// --------------------------
				System.out.println("" + GameController.getAnotherSide(GameController.getTurn()) + "Check!!");
			}
			GameController.nextTurn();
			System.out.println("Next turn");
			print(GameController.getBoard());
			if (GameController.isCheck()) {// -------------------------------
				System.out.println("" + GameController.getAnotherSide(GameController.getTurn()) + "Check!!");
			}
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
					System.out.print(" -- ");
					continue;
				}
				Point pr = board.getEntity(new Point(i, j)).getSymbol();
				if (pr.y == 0)
					System.out.print(" " + w_p[pr.x + 1] + " ");
				else if (pr.y == 1)
					System.out.print(" " + b_p[pr.x + 1] + " ");
				else
					System.out.println("Error");
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
