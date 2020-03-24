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
	public Board(String[][] map) { // W B --- K Q R B Kn P --- 0
		int column = map[0].length;
		whiteKing = null;
		blackKing = null;
		whitePieces = new ArrayList<Entity>();
		blackPieces = new ArrayList<Entity>();
		int row = map.length;
		setWidth(column);
		setHeight(row);
		cellmap = new Cell[row][column];
		for(int i=0;i<row;i++) {// Point (y,x) => (i,j)
			for(int j=0;j<column;j++) {
				Point p = new Point(i,j);
				switch (map[i][j]) {
				case "WK":
					whiteKing = new King(p,Side.WHITE);
					addEntity(whiteKing,p);
					break;
				case "WQ":
					addEntity(new Queen(p,Side.WHITE),p);
					break;
				//case "WR": ---------------------------------------------------------
				case "0":
					addEntity(null, p);
					break;
				default:
					System.out.println("Error");//-------------------------------
				}
			}
		}
	}
	public void remove(Point p) {
		addEntity(null, p);
	}
	public boolean addEntity(Entity e,Point p) {
		return cellmap[p.x][p.y].setEntity(e);
	}
	public Entity getEntity(Point p) {
		return cellmap[p.x][p.y].getEntity();
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
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
