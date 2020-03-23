package logic;

import entity.base.Entity;
import entity.*;
import java.awt.*;

public class Board{
	private Cell[][] cellmap;
	private int width;
	private int height;
	//private Game game;//enum game
	public Board(String[][] map) { // K Q R B Kn P
		int column = map[0].length;
		int row = map.length;
		setWidth(column);
		setHeight(row);
		cellmap = new Cell[row][column];
		for(int i=0;i<row;i++) {// Point (y,x) => (i,j)
			for(int j=0;j<column;j++) {
				switch (map[i][j]) {
				case "K":
					addEntity(new King(new Point(i,j)),i,j);
					break;
				//case "Q": ---------------------------------------------------------
				default:
					System.out.println("Error");//-------------------------------
				}
			}
		}
	}
	public boolean addEntity(Entity e,int y,int x) {
		return cellmap[y][x].setEntity(e);
	}
	public Entity getEntity(int x,int y) {
		return cellmap[y][x].getEntity();
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
}
