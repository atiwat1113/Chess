package game.base;

import logic.Sprites;
import logic.Cell;
import logic.Side;
import entity.base.Entity;
import entity.*;
import java.awt.*;
import java.util.ArrayList;
//import java.lang.Math;

public abstract class Board {
	private Cell[][] cellmap;
	private Entity whiteKing, blackKing;
	protected static final Point[] knightWalk = { new Point(1, 2), new Point(2, 1), new Point(2, -1), new Point(1, -2),
			new Point(-1, -2), new Point(-2, -1), new Point(-2, 1), new Point(-1, 2) };
	protected static final Point[] KingWalk = { new Point(1, 1), new Point(1, 0), new Point(1, -1), new Point(0, -1), new Point(-1, -1),
			new Point(-1, 0), new Point(-1, 1), new Point(0, 1) };
	private int width, height;

	// private Game game;//enum game
	public Board(String[][] map) {
		int column = map[0].length;
		int row = map.length;
		whiteKing = null;
		blackKing = null;
		width = column;
		height = row;
		cellmap = new Cell[row][column];
		for (int i = 0; i < row; i++) {// Point (y,x) => (i,j)
			for (int j = 0; j < column; j++) {
				Point p = new Point(i, j);
				cellmap[i][j] = new Cell();
				switch (map[i][j]) {// W B --- K Q R B N P --- 0
				case Sprites.W_KING:
					whiteKing = new King(p, Side.WHITE);
					addEntity(whiteKing, p);
					break;
				case Sprites.W_QUEEN:
					addEntity(new Queen(p, Side.WHITE), p);
					break;
				case Sprites.W_ROOK:
					addEntity(new Rook(p, Side.WHITE), p);
					break;
				case Sprites.W_BISHOP:
					addEntity(new Bishop(p, Side.WHITE), p);
					break;
				case Sprites.W_KNIGHT:
					addEntity(new Knight(p, Side.WHITE), p);
					break;
				case Sprites.W_PAWN:
					addEntity(new Pawn(p, Side.WHITE), p);
					break;
				case Sprites.B_KING:
					blackKing = new King(p, Side.BLACK);
					addEntity(blackKing, p);
					break;
				case Sprites.B_QUEEN:
					addEntity(new Queen(p, Side.BLACK), p);
					break;
				case Sprites.B_ROOK:
					addEntity(new Rook(p, Side.BLACK), p);
					break;
				case Sprites.B_BISHOP:
					addEntity(new Bishop(p, Side.BLACK), p);
					break;
				case Sprites.B_KNIGHT:
					addEntity(new Knight(p, Side.BLACK), p);
					break;
				case Sprites.B_PAWN:
					addEntity(new Pawn(p, Side.BLACK), p);
					break;
				case Sprites.BLANK:
					break;
				default:
					System.out.println("Error create board");
				}
			}
		}
	}
	
	//iswin
	public abstract boolean isWin(Side side);
	
	//move
	public boolean move(Point oldPoint, Point newPoint, ArrayList<Point> moveList) {
		Entity moveEntity = this.getEntity(oldPoint);
		for (Point moveablePoint : moveList) {
			if (moveablePoint.equals(newPoint)) {
				remove(oldPoint);
				moveEntity.setPoint(newPoint);
				addEntity(moveEntity, newPoint);
				return true;
			}
		}
		return false;
	}
	//complete moveList to display
	protected abstract ArrayList<Point> removeCannotMovePoint(Point oldPoint, ArrayList<Point> movePoint);
	public ArrayList<Point> moveList(Point point) {
		Entity moveEntity = getEntity(point);
		ArrayList<Point> movePoint = moveEntity.moveList(this);
		return removeCannotMovePoint(point, movePoint);
	}
	
	//other
	public boolean isEatenPoint(Point point, Side side) {
		ArrayList<Entity> allEntity = getAllPieces(getAnotherSide(side));
		for (Entity e : allEntity) {
			ArrayList<Point> eatablePoint = e.moveList(this);
			for (Point p : eatablePoint) {
				if (p.x == point.x && p.y == point.y) {
					return true;
				}
			}
		}
		return false;
	}

	public void remove(Point p) {
		addEntity(null, p);
	}

	public boolean addEntity(Entity e, Point p) {
		return cellmap[p.x][p.y].setEntity(e);
	}

	public Entity getEntity(Point p) {
		if (!isInBoard(p) || cellmap[p.x][p.y].IsEmpty()) {
			return null;
		}
		return cellmap[p.x][p.y].getEntity();
	}

	public boolean isInBoard(Point p) {
		if (p.x < 0 || p.x >= height)
			return false;
		if (p.y < 0 || p.y >= width)
			return false;
		return true;
	}

	public ArrayList<Entity> getAllPieces(Side side) {
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Entity e = getEntity(new Point(i, j));
				if (e == null)
					continue;
				// System.out.println(e);
				if (e.getSide() == side) {
					returnList.add(e);
				}
			}
		}
		return returnList;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Entity getKing(Side side) {
		if (side == Side.WHITE)
			return whiteKing;
		return blackKing;
	}

	public Side getAnotherSide(Side side) {
		if (side == Side.BLACK)
			return Side.WHITE;
		return Side.BLACK;
	}

	public static Point[] getKnightWalk() {
		return knightWalk;
	}

	public static Point[] getKingWalk() {
		return KingWalk;
	}
	
	public static Point addPoint(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}
//	public Cell[][] getCellmap() {
//		return cellmap;
//	}
}
