package logic;

import java.awt.*;
import java.util.Scanner;
import logic.*;
import entity.base.Entity;

public class PrintBoard {
	private static String[] w_p = {"--", "WK", "WQ", "WR", "WB", "WN", "WP"};
	private static String[] b_p = {"--", "BK", "BQ", "BR", "BB", "BN", "BP"};
	
	public static void main(String[]args) {
		Scanner kb = new Scanner(System.in);
		String[][] nb = {{b_p[3],b_p[5],b_p[4],b_p[2],b_p[1],b_p[4],b_p[5],b_p[3]},
						 {b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0]},
						 {b_p[6],b_p[6],b_p[6],b_p[6],b_p[6],b_p[6],b_p[6],b_p[6]},
						 {b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0],b_p[0]},
				 		 {w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0]},
						 {w_p[6],w_p[6],w_p[6],w_p[6],w_p[6],w_p[6],w_p[6],w_p[6]},
						 {w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0],w_p[0]},
						 {w_p[3],w_p[5],w_p[4],w_p[2],w_p[1],w_p[4],w_p[5],w_p[3]}};
		Board board = new Board(nb);
		System.out.println("Create board complete");
		print(board);
		Side turn = Side.WHITE;
		while (true) {
			System.out.print("" + turn + " turn ");
			String walkfrom = kb.nextLine();
			if (walkfrom.charAt(0)=='q') break;
			if (walkfrom.charAt(0)=='p') print(board);
			if (!isTurn(stringToPoint(walkfrom),turn,board)) {
				System.out.println("Pick again!!");
				continue;
			}
			Point piece = stringToPoint(walkfrom);
			String walkTo = kb.nextLine();
			//-----------------------------------------------------------------------------
			print(board);
		}
	}
	public static void print(Board board) {
		System.out.println("  -a---b---c---d---e---f---g---h--");
		for(int i=0; i<8; i++) {
			System.out.print(""+(8-i)+" ");
			for (int j=0; j<8; j++) {
				if (board.getEntity(new Point(i,j))==null) {
					System.out.print(" -- ");
					continue;
				}
				int pr = board.getEntity(new Point(i,j)).getSymbol();
				if (pr>0) System.out.print(" " + w_p[pr] + " ");
				else if (pr<0) System.out.print(" " + b_p[-pr] + " ");
				else System.out.println("Error");
			}
			System.out.println();
		}
	}
	
	public static boolean isTurn(Point p, Side turn, Board board) {
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
	
	public static Point stringToPoint(String str) {
		if (str.length()!=2) return null;
		String s1="abcdefgh";
		String s2="87654321";
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				//System.out.println(""+s1.charAt(i)+s2.charAt(j));
				if (s1.charAt(j)==str.charAt(0) && s2.charAt(i)==str.charAt(1))
					return new Point(i,j);
			}
		}
		return null;
	}
}
