package game.base;

import logic.*;
import entity.base.Entity;
import entity.*;
import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

public class Board {// abstract
	private Cell[][] cellmap;
	private Entity whiteKing, blackKing;
	private Point[] knightWalk = {new Point(1,2),new Point(2,1),new Point(2,-1),new Point(1,-2),
							   new Point(-1,-2),new Point(-2,-1),new Point(-2,1),new Point(-1,2)};
	private Point[] KingWalk = {new Point(1,1),new Point(1,0),new Point(1,-1),new Point(0,-1),
							   new Point(-1,-1),new Point(-1,0),new Point(-1,1),new Point(0,1)};
	private int width;
	private int height;
	//private Game game;//enum game
	public Board(String[][] map) { 
		int column = map[0].length;
		int row = map.length;
		whiteKing = null;
		blackKing = null;
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
	public boolean isWin(Side side) {
		return false;
	}
	public ArrayList<Point> moveList(Point p) {
		Entity moveEntity = getEntity(p);
		ArrayList<Point> movePoint = moveEntity.moveList(this);
		ArrayList<Integer> removeIndex = new ArrayList<Integer>();
		for(int i = 0; i<movePoint.size(); i++) {//----------------------------------------
			if (isCheck(p, movePoint.get(i), getKing(moveEntity.getSide()))) {//---------------------------
				removeIndex.add(i);
			}
		}
		for (int i=removeIndex.size()-1; i>=0; i--) {
			movePoint.remove(removeIndex.get(i));
		}
		return movePoint;
	}
	public boolean isCheck(Point movePoint, Point toPoint, Entity king) {//-----------------------
		Side side = king.getSide();//
		Point kingPoint = king.getP();
		if (canBeEaten(side)) {
			return true;
		}
		//Point vector = new Point(toPoint.x-kingPoint.x,toPoint.y-kingPoint.y);
		Point[] rookVector = {new Point(1,0),new Point(-1,0),new Point(0,1),new Point(0,-1)};
		Point[] bishopVector = {new Point(1,1),new Point(-1,1),new Point(1,-1),new Point(-1,-1)};
		for(Point vector : rookVector) {
			if (checkRook(kingPoint, vector, movePoint, side)) return true;
		}
		for(Point vector : bishopVector) {//check bishop and queen
			if (checkBishop(kingPoint, vector, movePoint, side)) return true;
		}
		return false;
	}
	public boolean isCheckKing(Point movePoint, Point toPoint, Entity king) {
		Side side = king.getSide();//
		Point kingPoint = king.getP();
		if (canBeEaten(side)) {
			return true;
		}
		Point vector = new Point(movePoint.x-kingPoint.x,movePoint.y-kingPoint.y);
		if (vector.x*vector.y==0) {//check rook and queen
			if (vector.x!=0)vector.x/=Math.abs(vector.x);
			if (vector.y!=0)vector.y/=Math.abs(vector.y);
			if (checkRook(kingPoint, vector, movePoint, side)) return true;
		}else if(Math.abs(vector.x)==Math.abs(vector.y)){//check bishop and queen
			vector.x/=Math.abs(vector.x);
			vector.y/=Math.abs(vector.y);
			if (checkBishop(kingPoint, vector, movePoint, side)) return true;
		}
		return false;
	}
	public boolean checkRook(Point point, Point vector, Point removePoint, Side side) {
		Point nextPoint = Entity.addVector(point, vector);
		if (vector.equals(new Point(0,0))) return false;
		//System.out.println("    " + vector.toString());//---------------
		//System.out.println(vector.toString());
		if (!isInBoard(nextPoint)) return false;
		if (nextPoint.equals(removePoint) || getEntity(nextPoint)==null) {
			return checkRook(nextPoint, vector, removePoint, side);
		//}else if (isSameSide(getEntity(nextPoint).getSide(), side)) {
		//	return false;
		}else if (getEntity(nextPoint).getSide()!=side) {
			if (getEntity(nextPoint) instanceof Rook || getEntity(nextPoint) instanceof Queen) {
				return true;
			}
			return false;
		}
		//System.out.println("Error checkRook");
		return false;
	}
	public boolean checkBishop(Point point, Point vector, Point removePoint, Side side) {
		//System.out.println(vector.toString());
		Point nextPoint = Entity.addVector(point, vector);
		if (!isInBoard(nextPoint)) return false;
		if (nextPoint.equals(removePoint) || getEntity(nextPoint)==null) {
			return checkRook(nextPoint, vector, removePoint, side);
		//}else if (getEntity(nextPoint).getSide()==side) {
		//	return false;
		}else if (getEntity(nextPoint).getSide()==side) {
			if (getEntity(nextPoint) instanceof Bishop || getEntity(nextPoint) instanceof Queen) {
				return true;
			}
			return false;
		}
		//System.out.println("Error checkBishop");
		return false;
	}
	public boolean move(Point oldPoint, Point newPoint) {
		if (newPoint == null || !isInBoard(newPoint)) return false;
		Entity moveEntity = this.getEntity(oldPoint);
		for (Point moveablePoint : moveEntity.moveList(this)) {
			if (moveablePoint.equals(newPoint)) {
				remove(oldPoint);
				moveEntity.setP(newPoint);
				addEntity(moveEntity, newPoint);
				return true;
			}
		}
		return false;
	}
	public boolean canBeEaten(Side side) {//nomove
		Entity king = getKing(side);
		Point kingPoint = king.getP();
		ArrayList<Entity> allEntity = getAllPieces(side);// Ex for black: white can eat this point?
		for (Entity e : allEntity) {
			System.out.println("Can "+e);
			ArrayList<Point> eatablePoint = e.eatList(this);
			for (Point p : eatablePoint) {
				System.out.println("point " + p.toString());//----------------------
				if(p.x==kingPoint.x && p.y==kingPoint.y) {
					return true;
				}
			}
		}
		return false;
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
		if (p.x<0 || p.x>=height) return false;
		if (p.y<0 || p.y>=width) return false;
		return true;
	}
	public ArrayList<Entity> getAllPieces(Side side){
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		for(int i = 0; i<8; i++) {
			for(int j = 0; j < 8; j++) {
				Entity e = getEntity(new Point(i,j));
				if(e==null)continue;
				//System.out.println(e);
				if (e.getSide()==side) {
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
		if (side == Side.WHITE) return whiteKing;
		return blackKing;
	}
	public Entity getAnotherKing(Side side) {
		if (side == Side.BLACK) return whiteKing;
		return blackKing;
	}
	public Point[] getKnightWalk() {
		return knightWalk;
	}
	public Point[] getKingWalk() {
		return KingWalk;
	}
	public boolean isSameSide(Side s1, Side s2) {
		if(s1==Side.BLACK && s2==Side.BLACK) return true;
		if(s1==Side.WHITE && s2==Side.WHITE) return true;
		return false;
	}
}
