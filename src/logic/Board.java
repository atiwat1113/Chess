package logic;

import entity.base.Entity;
import entity.*;
import java.awt.*;
import java.util.ArrayList;

public class Board{
	private Cell[][] cellmap;
	private ArrayList<Entity> whitePieces, blackPieces;//have to add in remove, addEntity
	private Entity whiteKing, blackKing;
	
	private int width;
	private int height;
	//private Game game;//enum game
	public Board(String[][] map) { 
		int column = map[0].length;
		int row = map.length;
		whiteKing = null;
		blackKing = null;
		whitePieces = new ArrayList<Entity>();
		blackPieces = new ArrayList<Entity>();
		width = column;
		height = row;
		cellmap = new Cell[row][column];
		for(int i=0;i<row;i++) {// Point (y,x) => (i,j)
			for(int j=0;j<column;j++) {
				Point p = new Point(i,j);
				cellmap[i][j] = new Cell();
				switch (map[i][j]) {// W B --- K Q R B N P --- 0
				case "WK":
					whiteKing = new King(p,Side.WHITE);
					addEntity(whiteKing,p);
					break;
				case "WQ":
					addEntity(new Queen(p,Side.WHITE),p);
					break;
				case "WR":
					addEntity(new Rook(p,Side.WHITE),p);
					break;
				case "WB":
					addEntity(new Bishop(p,Side.WHITE),p);
					break;
				case "WN":
					addEntity(new Knight(p,Side.WHITE),p);
					break;
				case "WP":
					addEntity(new Pawn(p,Side.WHITE),p);
					break;
				case "BK":
					blackKing = new King(p,Side.BLACK);
					addEntity(blackKing,p);
					break;
				case "BQ":
					addEntity(new Queen(p,Side.BLACK),p);
					break;
				case "BR":
					addEntity(new Rook(p,Side.BLACK),p);
					break;
				case "BB":
					addEntity(new Bishop(p,Side.BLACK),p);
					break;
				case "BN":
					addEntity(new Knight(p,Side.BLACK),p);
					break;
				case "BP":
					addEntity(new Pawn(p,Side.BLACK),p);
					break;
				case "--":
					break;
				default:
					System.out.println("Error");
				}
			}
		}
	}
	public boolean move (Point p1, Point p2) {
		if (p2 == null || !isInBoard(p2)) return false;
		return getEntity(p1).move(this, p2);
	}
	public void remove(Point p) {
		addEntity(null, p);
	}
	public boolean addEntity(Entity e,Point p) {
		return cellmap[p.x][p.y].setEntity(e);
	}
	public Entity getEntity(Point p) {
		if (!isInBoard(p) || cellmap[p.x][p.y].IsEmpty()) {
			return null;
		}
		return cellmap[p.x][p.y].getEntity();
	}
	public boolean isInBoard(Point p) {
		if (p.x<0 || p.x>height) return false;
		if (p.y<0 || p.y>width) return false;
		return true;
	}
	public Entity[] getAllPieces(Side side){
		if (side == Side.WHITE) {
			return (Entity[]) whitePieces.toArray();
		}
		return (Entity[]) blackPieces.toArray();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Entity getKing(Side side) {
		if (side == Side.WHITE) return whiteKing;
		return blackKing;
	}
	public Entity getAnotherKing(Side side) {
		if (side == Side.BLACK) return whiteKing;
		return blackKing;
	}
}
