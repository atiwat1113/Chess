package game.base;

import java.awt.Point;
import java.util.ArrayList;

import entity.*;
import entity.base.Entity;
import logic.Side;

public class NormalBoard extends Board implements CheckMateAble{
	
	public NormalBoard(String[][] map) {
		super(map);
	}
	//iswin
	public boolean isWin(Side side) {
		return winByCheckmate(side);
	}
	
	public boolean winByCheckmate(Side side) {
		//--------------------------------------------------------------------------------------------------
		return false;
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
	public String print(Point p) {
		String pp=""+p;
		int s1,s2,s3,s4;
		s1=17;
		s2=pp.indexOf(",",s1);
		s3=s2+3;
		s4=pp.indexOf("]",s3);
		return "("+pp.substring(s1,s2)+","+pp.substring(s3,s4)+")";
	}
	public boolean checkCannotMovePoint(Point oldPoint, Point newPoint, Point kingPoint, Side side) {
		System.out.println(print(oldPoint)+"->"+print(newPoint)+"K"+print(kingPoint));
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
		Point nextPoint = addPoint(point, vector);
		if (vector.equals(new Point(0, 0)))
			return false;
		// System.out.println(" " + vector.toString());//---------------
		// System.out.println(vector.toString());
		if (!isInBoard(nextPoint))
			return false;
		if (nextPoint.equals(newPoint)) return false;
		if (nextPoint.equals(oldPoint) || getEntity(nextPoint) == null) {
			return checkRook(nextPoint, vector, oldPoint, newPoint, side);
			// }else if (isSameSide(getEntity(nextPoint).getSide(), side)) {
			// return false;
		} else if (getEntity(nextPoint).getSide() != side) {
			if (getEntity(nextPoint) instanceof Rook || getEntity(nextPoint) instanceof Queen) {
				return true;
			}
			return false;
		}
		// System.out.println("Error checkRook");
		return false;
	}

	public boolean checkBishop(Point point, Point vector, Point oldPoint, Point newPoint, Side side) {
		Point nextPoint = addPoint(point, vector);
		if (vector.equals(new Point(0, 0)))
			return false;
		// System.out.println(" " + vector.toString());//---------------
		// System.out.println(vector.toString());
		if (!isInBoard(nextPoint))
			return false;
		if (nextPoint.equals(newPoint)) return false;
		if (nextPoint.equals(oldPoint) || getEntity(nextPoint) == null) {
			return checkRook(nextPoint, vector, oldPoint, newPoint, side);
			// }else if (isSameSide(getEntity(nextPoint).getSide(), side)) {
			// return false;
		} else if (getEntity(nextPoint).getSide() != side) {
			if (getEntity(nextPoint) instanceof Bishop || getEntity(nextPoint) instanceof Queen) {
				return true;
			}
			return false;
		}
		// System.out.println("Error checkRook");
		return false;
	}
//	public boolean isCheckKing(Point movePoint, Point toPoint, Entity king) {
//		Side side = king.getSide();//
//		Point kingPoint = king.getP();
//		if (canBeEaten(side)) {
//			return true;
//		}
//		Point vector = new Point(movePoint.x - kingPoint.x, movePoint.y - kingPoint.y);
//		if (vector.x * vector.y == 0) {// check rook and queen
//			if (vector.x != 0)
//				vector.x /= Math.abs(vector.x);
//			if (vector.y != 0)
//				vector.y /= Math.abs(vector.y);
//			if (checkRook(kingPoint, vector, movePoint, side))
//				return true;
//		} else if (Math.abs(vector.x) == Math.abs(vector.y)) {// check bishop and queen
//			vector.x /= Math.abs(vector.x);
//			vector.y /= Math.abs(vector.y);
//			if (checkBishop(kingPoint, vector, movePoint, side))
//				return true;
//		}
//		return false;
//	}
}
