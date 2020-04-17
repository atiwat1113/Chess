package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.King;
import entity.Rook;
import entity.base.Entity;
import entity.base.HaveCastling;
import logic.Side;

public class Chess960Board extends NormalBoard{
	public Chess960Board(String[][] map) {
		super(map);
	}
	//      01234567
	//black r---k--r
	//        kr-rk
	//white r---k--r
	public ArrayList<Point> castingPoint(Side side) {//finished
		ArrayList<Point> returnPoint = new ArrayList<Point>();
		Entity king = getKing(side);
		if(!((King) king).isNeverMove()) {
			return returnPoint;
		}
		if (isRightCastling(king, side)!=null) returnPoint.add(isRightCastling(king, side));
		if (isLeftCastling(king, side)!=null) returnPoint.add(isLeftCastling(king, side));
		return returnPoint;
	}
	public boolean isCastlingPoint(Side side, Point point) {//finished
		Entity entity = getEntity(point);
		return (entity instanceof Rook && entity.getSide()==side);
	}
	public boolean isFree(Point start, Point stop) {
		return false;
	}
	public boolean isNull(Point start, Point stop, Point king, Point rook) {
		return false;
	}
	public Point isRightCastling(Entity king, Side side) {
		int s = (side == Side.BLACK)? 0 : 7;
		Entity rightRook;
		
		return null;
	}
	public Point isLeftCastling(Entity king, Side side) {
		return null;
	}
	public void castling(Side side, Point oldPoint, Point newPoint) {
		Entity moveEntity = this.getEntity(oldPoint);
		boolean c = side == Side.WHITE;
		if (side == Side.BLACK) {
			if (c) {
				moveRook(new Point(0,0), new Point(0,3));
			}else {
				moveRook(new Point(0,7), new Point(0,5));
			}
		}else {
			if (c) {
				moveRook(new Point(7,0), new Point(7,3));
			}else {
				moveRook(new Point(7,7), new Point(7,5));
			}
		}
		remove(oldPoint);
		moveEntity.setPoint(newPoint);
		addEntity(moveEntity, newPoint);
	}
	
}
