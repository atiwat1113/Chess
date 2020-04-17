package game.base;

import logic.*;
import entity.base.*;
import entity.*;
import java.awt.*;
import java.util.ArrayList;

//import java.lang.Math;
//remain: win, draw
public abstract class Board {
	protected Point twoWalkPawn;
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
	
	//iswin
	public abstract boolean isWin(Side side);//opposite side win
	public abstract boolean isDraw(Side side);
	public abstract boolean isCheck(Side side);
	//move
	public abstract void move(Point oldPoint, Point newPoint);
	//complete moveList to display
	public ArrayList<Point> moveList(Point point) {
		Entity moveEntity = getEntity(point);
		ArrayList<Point> movePoint = moveEntity.moveList(this);
		//GameController.printPointList(movePoint);
		return editMovePoint(point, movePoint);
	}
	//moveList
	protected abstract ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint);
	public boolean checkCannotMovePoint(ArrayList<Point> oldPoint, Point newPoint, Point kingPoint, Side side) {
		//System.out.println(print(oldPoint)+"->"+print(newPoint)+"K"+print(kingPoint));
		Point[] rookVector = { new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1) };
		Point[] bishopVector = { new Point(1, 1), new Point(-1, 1), new Point(1, -1), new Point(-1, -1) };
		if (checkOther(newPoint, kingPoint, side)) return true;
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
	public boolean checkOther(Point newPoint, Point kingPoint, Side side) {
		Point[] blackPawnWalk = { new Point(1, 1), new Point(1, -1)};
		Point[] whitePawnWalk = { new Point(-1, 1), new Point(-1, -1)};
		Point[] pawnWalk = whitePawnWalk;
		if (side == Side.BLACK) pawnWalk = blackPawnWalk;
		for(Point point : pawnWalk) {
			Point checkPoint = addPoint(kingPoint, point);
			if(!isInBoard(checkPoint)) continue;
			Entity interestingEntity = getEntity(checkPoint);
			if(interestingEntity==null) continue;
			if (checkPoint.equals(newPoint)) continue;
			if(interestingEntity.getSide()==getAnotherSide(side)&&interestingEntity instanceof Pawn)
				return true;
		}
		for(Point point : knightWalk) {
			Point checkPoint = addPoint(kingPoint, point);
			if(!isInBoard(checkPoint)) continue;
			Entity interestingEntity = getEntity(checkPoint);
			if(interestingEntity==null) continue;
			if (checkPoint.equals(newPoint)) continue;
			if(interestingEntity.getSide()==getAnotherSide(side)&&interestingEntity instanceof Knight)
				 return true;
		}
		for(Point point : KingWalk) {
			Point checkPoint = addPoint(kingPoint, point);
			if(!isInBoard(checkPoint)) continue;
			Entity interestingEntity = getEntity(checkPoint);
			if(interestingEntity==null) continue;
			if (checkPoint.equals(newPoint)) continue;
			if(interestingEntity.getSide()==getAnotherSide(side)&&interestingEntity instanceof King)
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
		switch (piece.charAt(0)) {
		case 'Q':
			addEntity(new Queen(point, side, true), point);
			return;
		case 'R':
			addEntity(new Rook(point, side, true), point);
			return;
		case 'B':
			addEntity(new Bishop(point, side, true), point);
			return;
		case 'K':
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
	
	
	
	//castling
	public ArrayList<Point> castingPoint(Side side) {//White 7,4 7,0 7,7 // Black 0,4 0,0 0,7
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Entity king = getKing(side);
		int s = (side == Side.BLACK)? 0 : 7;
		if(!((King) king).isNeverMove()) {
			return returnPoint;
		}
		if (isRightCastling(side)) returnPoint.add(new Point(s,6));
		if (isLeftCastling(side)) returnPoint.add(new Point(s,2));
		return returnPoint;
	}
	public boolean isCastlingPoint(Side side, Point point) {
		int s = (side == Side.BLACK)? 0 : 7;
		if (isRightCastling(side) && point.equals(new Point(s,6))) return true;
		if (isLeftCastling(side) && point.equals(new Point(s,2))) return true;
		return false;
	}
	public boolean isRightCastling(Side side) {
		int s = (side == Side.BLACK)? 0 : 7;
		int[] s1 = {4,5,6};
		int[] s2 = {5,6};
		for(int e : s1) {
			if(isEatenPoint(new Point(s,e), side)) {
				return false;
			}
		}
		for(int e : s2) {
			if(getEntity(new Point(s,e))!=null) {
				return false;
			}
		}
		if (getEntity(new Point(s,7)) != null && getEntity(new Point(s,7)) instanceof Rook) {
			if(((HaveCastling) getEntity(new Point(s,7))).isNeverMove()) return true;
		}
//		try {
//			if(((HaveCastling) board.getEntity(new Point(s,7))).isNeverMove()) {
//				return true;//new Point(s,6);
//			}
//		}finally {}
		return false;
	}
	public boolean isLeftCastling(Side side) {
		int s = (side == Side.BLACK)? 0 : 7;
		int[] s1 = {2,3,4};
		int[] s2 = {1,2,3};
		for(int e : s1) {
			if(isEatenPoint(new Point(s,e), side)) {
				return false;
			}
		}
		for(int e : s2) {
			if(getEntity(new Point(s,e))!=null) {
				return false;
			}
		}
		if (getEntity(new Point(s,0)) != null && getEntity(new Point(s,0)) instanceof Rook) {
			if(((HaveCastling) getEntity(new Point(s,0))).isNeverMove()) return true;
		}
//		try {
//			if(((HaveCastling) board.getEntity(new Point(s,0))).isNeverMove()) {
//				return true;//new Point(s,2);
//			}
//		}finally {}
		return false;
	}
	public void castling(Side side, Point oldPoint, Point newPoint) {
		Entity moveEntity = this.getEntity(oldPoint);
		remove(oldPoint);
		moveEntity.setPoint(newPoint);
		addEntity(moveEntity, newPoint);
		if (newPoint.equals(new Point(0,2))) {
			moveRook(new Point(0,0), new Point(0,3));
		}else if (newPoint.equals(new Point(0,6))) {
			moveRook(new Point(0,7), new Point(0,5));
		}else if (newPoint.equals(new Point(7,2))) {
			moveRook(new Point(7,0), new Point(7,3));
		}else if (newPoint.equals(new Point(7,6))) {
			moveRook(new Point(7,7), new Point(7,5));
		}
	}
	
	public void moveRook(Point oldPoint, Point newPoint) {//kingpoint
		Entity rook = getEntity(oldPoint);
		remove(oldPoint);
		rook.setPoint(newPoint);
		addEntity(rook, newPoint);
	}
}
