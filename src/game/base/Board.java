package game.base;

import logic.Sprites;
import logic.Cell;
import logic.Side;
import entity.base.*;
import entity.*;
import java.awt.*;
import java.util.ArrayList;
//import java.lang.Math;
//remain: Promotion, draw, en passant
public abstract class Board {
	private Cell[][] cellMap;
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
		cellMap = new Cell[row][column];
		for (int i = 0; i < row; i++) {// Point (y,x) => (i,j)
			for (int j = 0; j < column; j++) {
				Point p = new Point(i, j);
				cellMap[i][j] = new Cell();
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
				if (moveEntity instanceof HaveCastling) ((HaveCastling) moveEntity).setNeverMove();
				if (moveEntity instanceof King) {
					if (((King) moveEntity).isCastlingPoint(this, newPoint)) {
						((King) moveEntity).moveRook(this, newPoint);
					}
				}
				remove(oldPoint);
				moveEntity.setPoint(newPoint);
				addEntity(moveEntity, newPoint);
				return true;
			}
		}
		return false;
	}
	//complete moveList to display
	public ArrayList<Point> moveList(Point point) {
		Entity moveEntity = getEntity(point);
		ArrayList<Point> movePoint = moveEntity.moveList(this);
		return removeCannotMovePoint(point, movePoint);
	}
	//moveList
		protected ArrayList<Point> removeCannotMovePoint(Point oldPoint, ArrayList<Point> movePoint){
			Side side = getEntity(oldPoint).getSide();
			if (getEntity(oldPoint) instanceof King) {
				for(int i = movePoint.size()-1; i>=0; i--) {
					if (checkCannotMovePoint(oldPoint,new Point(-1,-1),movePoint.get(i),side)) movePoint.remove(i);
				}
				for(Point p : ((King) getEntity(oldPoint)).castingPoint(this)) {
					movePoint.add(p);
				} //for castling
			}else {
				Point kingPoint = getKing(side).getPoint();
				for(int i = movePoint.size()-1; i>=0; i--) {
					if (checkCannotMovePoint(oldPoint,movePoint.get(i),kingPoint,side)) movePoint.remove(i);
				}
			}
			return movePoint;
		}
	public boolean checkCannotMovePoint(Point oldPoint, Point newPoint, Point kingPoint, Side side) {
		//System.out.println(print(oldPoint)+"->"+print(newPoint)+"K"+print(kingPoint));
		Point[] rookVector = { new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1) };
		Point[] bishopVector = { new Point(1, 1), new Point(-1, 1), new Point(1, -1), new Point(-1, -1) };
		for(Point point : knightWalk) {
			Point checkPoint = addPoint(kingPoint, point);
			if(!isInBoard(checkPoint)) continue;
			Entity interestingEntity = getEntity(checkPoint);
			if(interestingEntity==null) continue;
			if(interestingEntity.getSide()==getAnotherSide(side)&&interestingEntity instanceof Knight)
				return true;
		}
		for(Point point : KingWalk) {
			Point checkPoint = addPoint(kingPoint, point);
			if(!isInBoard(checkPoint)) continue;
			Entity interestingEntity = getEntity(checkPoint);
			if(interestingEntity==null) continue;
			if(interestingEntity.getSide()==getAnotherSide(side)&&interestingEntity instanceof King)
				return true;
		}
		for (Point vector : rookVector) {
			if (checkRook(kingPoint, vector, oldPoint, newPoint, side))
				return true;
		}
		for (Point vector : bishopVector) {// check bishop and queen
			if (checkBishop(kingPoint, vector, oldPoint, newPoint, side))
				return true;
		}
		return false;
	}
	public boolean checkRook(Point point, Point vector, Point oldPoint, Point newPoint, Side side) {
		//System.out.print(print(oldPoint)+"->"+print(newPoint));
		//System.out.print(print(newPoint));
		//System.out.print(":"+print(point)+"R"+print(vector));
		Point nextPoint = addPoint(point, vector);
		//if (vector.equals(new Point(0, 0))) {
		//	System.out.println("return wrong vecter");
		//	return false;
		//}
		if (!isInBoard(nextPoint)) {
		//	System.out.println("out of board");
			return false;
		}
		if (nextPoint.equals(newPoint)) {
		//	System.out.println("new point protect king");
			return false;
		}
		if (nextPoint.equals(oldPoint) || getEntity(nextPoint) == null) {
		//	System.out.println("go continue");
			return checkRook(nextPoint, vector, oldPoint, newPoint, side);
		}
		if (getEntity(nextPoint).getSide() != side) {
			if (getEntity(nextPoint) instanceof Rook || getEntity(nextPoint) instanceof Queen) {
		//		System.out.println("can eat");
				return true;
			}
		//	System.out.println(""+getEntity(nextPoint));
		//	System.out.println("Opposite side");
		//	return false;
		}
		//if (getEntity(nextPoint).getSide() == side) {
		//	System.out.println("Same side");
		//	return false;
		//}
		//System.out.println("Error checkRook");
		return false;
	}
	public boolean checkBishop(Point point, Point vector, Point oldPoint, Point newPoint, Side side) {
		//System.out.print(print(oldPoint)+"->"+print(newPoint));
		//System.out.print(print(newPoint));
		//System.out.print(":"+print(point)+"B"+print(vector));
		Point nextPoint = addPoint(point, vector);
		//if (vector.equals(new Point(0, 0))) {
		//	System.out.println("return wrong vecter");
		//	return false;
		//}
		if (!isInBoard(nextPoint)) {
		//	System.out.println("out of board");
			return false;
		}
		if (nextPoint.equals(newPoint)) {
		//	System.out.println("new point protect king");
			return false;
		}
		if (nextPoint.equals(oldPoint) || getEntity(nextPoint) == null) {
		//	System.out.println("go continue");
			return checkBishop(nextPoint, vector, oldPoint, newPoint, side);
		}
		if (getEntity(nextPoint).getSide() != side) {
			if (getEntity(nextPoint) instanceof Bishop || getEntity(nextPoint) instanceof Queen) {
		//		System.out.println("can eat");
				return true;
			}
		//	System.out.println(""+getEntity(nextPoint));
		//	System.out.println("Opposite side");
		//	return false;
		}
		//if (getEntity(nextPoint).getSide() == side) {
		//	System.out.println("Same side");
		//	return false;
		//}
		//System.out.println("Error checkBishop");
		return false;
	}
	
	//other
	public boolean isEatenPoint(Point point, Side side) {
		ArrayList<Entity> allEntity = getAllPieces(getAnotherSide(side));
		for (Entity e : allEntity) {
			ArrayList<Point> moveablePoint = e.moveList(this);
			for (Point p : moveablePoint) {
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
		return cellMap[p.x][p.y].setEntity(e);
	}

	public Entity getEntity(Point p) {
		if (!isInBoard(p) || cellMap[p.x][p.y].IsEmpty()) {
			return null;
		}
		return cellMap[p.x][p.y].getEntity();
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
	public Cell[][] getCellMap() {
		return cellMap;
	}
}
