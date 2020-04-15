package game.base;

import logic.*;
import entity.base.*;
import entity.*;
import java.awt.*;
import java.util.ArrayList;

//import java.lang.Math;
//remain: win, draw
public abstract class Board {
	private Point twoWalkPawn;
	private Cell[][] cellMap;
	private Entity whiteKing, blackKing;
	protected static final Point[] knightWalk = { new Point(1, 2), new Point(2, 1), new Point(2, -1), new Point(1, -2),
			new Point(-1, -2), new Point(-2, -1), new Point(-2, 1), new Point(-1, 2) };
	protected static final Point[] KingWalk = { new Point(1, 1), new Point(1, 0), new Point(1, -1), new Point(0, -1), new Point(-1, -1),
			new Point(-1, 0), new Point(-1, 1), new Point(0, 1) };
	private int width, height;

	// private Game game;//enum game
	public Board(String[][] map) {
		twoWalkPawn = null;
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
				case "WKing":
					whiteKing = new King(p, Side.WHITE);
					addEntity(whiteKing, p);
					break;
				case "WQueen":
					addEntity(new Queen(p, Side.WHITE), p);
					break;
				case "WRook":
					addEntity(new Rook(p, Side.WHITE), p);
					break;
				case "WBishop":
					addEntity(new Bishop(p, Side.WHITE), p);
					break;
				case "WKnight":
					addEntity(new Knight(p, Side.WHITE), p);
					break;
				case "WPawn":
					addEntity(new Pawn(p, Side.WHITE), p);
					break;
				case "BKing":
					blackKing = new King(p, Side.BLACK);
					addEntity(blackKing, p);
					break;
				case "BQueen":
					addEntity(new Queen(p, Side.BLACK), p);
					break;
				case "BRook":
					addEntity(new Rook(p, Side.BLACK), p);
					break;
				case "BBishop":
					addEntity(new Bishop(p, Side.BLACK), p);
					break;
				case "BKnight":
					addEntity(new Knight(p, Side.BLACK), p);
					break;
				case "BPawn":
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
	public void update(Side turn) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Entity e = getEntity(new Point(i, j));
				if (e instanceof Updatable) {
					((Updatable) e).update(turn);
				}
			}
		}
	}
	
	//iswin
	public abstract boolean isWin(Side side);
	public abstract boolean isDraw(Side side);
	//move
	public boolean move(Point oldPoint, Point newPoint, ArrayList<Point> moveList) {
		Entity moveEntity = this.getEntity(oldPoint);
		for (Point moveablePoint : moveList) {
			if (moveablePoint.equals(newPoint)) {
				if (moveEntity instanceof HaveCastling) ((HaveCastling) moveEntity).setNeverMove();
				if (moveEntity instanceof Pawn) {
					if (twoWalkPawn != null && twoWalkPawn.equals(new Point(oldPoint.x,newPoint.y))){
						remove(twoWalkPawn);
					} else if (Math.abs(oldPoint.x-newPoint.x)==2) {
						twoWalkPawn=newPoint;
					}
					else twoWalkPawn=null;
				}
				else twoWalkPawn=null;
				if (moveEntity instanceof King) {
					if (((King) moveEntity).isCastlingPoint(this, newPoint)) {
						((King) moveEntity).moveRook(this, newPoint);
					}
				}
				remove(oldPoint);
				moveEntity.setPoint(newPoint);
				addEntity(moveEntity, newPoint);
			}
		}
		int s = 0;
		if (moveEntity.getSide() == Side.BLACK) {
			s = 7;
		}
		if (moveEntity instanceof Pawn && newPoint.x == s) {
			havePromotion(newPoint, moveEntity.getSide());
		}
		return false;
	}
	//complete moveList to display
	public ArrayList<Point> moveList(Point point) {
		Entity moveEntity = getEntity(point);
		ArrayList<Point> movePoint = moveEntity.moveList(this);
		//GameController.printPointList(movePoint);
		return removeCannotMovePoint(point, movePoint);
	}
	//moveList
	protected ArrayList<Point> removeCannotMovePoint(Point oldPoint, ArrayList<Point> movePoint){
		Entity moveEntity = this.getEntity(oldPoint);
		Side side = moveEntity.getSide();
		ArrayList<Point> op = new ArrayList<Point>();
		op.add(oldPoint);
		if (moveEntity instanceof King) {
			for(int i = movePoint.size()-1; i>=0; i--) {
				if (checkCannotMovePoint(op,new Point(-1,-1),movePoint.get(i),side)) movePoint.remove(i);
			}
			for(Point p : ((King) moveEntity).castingPoint(this)) {//for castling
				movePoint.add(p);
			} 
		}else {
			Point kingPoint = getKing(side).getPoint();
			for(int i = movePoint.size()-1; i>=0; i--) {
				if (checkCannotMovePoint(op,movePoint.get(i),kingPoint,side))  {
					movePoint.remove(i);
				}
			}
			if (moveEntity instanceof Pawn) {
				if (twoWalkPawn!=null) {
					if (oldPoint.x==twoWalkPawn.x && Math.abs(oldPoint.y-twoWalkPawn.y)==1) {
						Point newPoint;
						op.add(twoWalkPawn);
						if (side == Side.BLACK) newPoint = new Point(twoWalkPawn.x+1,twoWalkPawn.y);
						else newPoint = new Point(twoWalkPawn.x-1,twoWalkPawn.y);
						if (!checkCannotMovePoint(op,newPoint,kingPoint,side)) {
							movePoint.add(newPoint);
						}
					}
				}
			}
		}
		return movePoint;
	}
	public boolean checkCannotMovePoint(ArrayList<Point> oldPoint, Point newPoint, Point kingPoint, Side side) {
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
	public boolean checkRook(Point point, Point vector, ArrayList<Point> oldPoint, Point newPoint, Side side) {
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
		if (oldPoint.contains(nextPoint) || getEntity(nextPoint) == null) {
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
	public boolean checkBishop(Point point, Point vector, ArrayList<Point> oldPoint, Point newPoint, Side side) {
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
		if (oldPoint.contains(nextPoint) || getEntity(nextPoint) == null) {
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
	public void havePromotion(Point point, Side side) {
		GameController.setPromotion(point, side);
	}
	public void promotion(Point point, Side side, String piece) {
		switch (piece) {
		case "Queen":
			addEntity(new Queen(point, side, true), point);
			return;
		case "Rook":
			addEntity(new Rook(point, side, true), point);
			return;
		case "Bishop":
			addEntity(new Bishop(point, side, true), point);
			return;
		case "Knight":
			addEntity(new Knight(point, side, true), point);
			return;
		default:
		}
	}
	
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
