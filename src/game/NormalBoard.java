package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.*;
import entity.base.Entity;
import entity.base.HaveCastling;
import logic.Side;
import game.base.*;

public class NormalBoard extends Board implements CheckMateAble{
	
	public NormalBoard(String[][] map) {
		super(map);
	}
	//iswin
	public boolean isWin(Side side) {
		return winByCheckmate(side);
	}
	
	public boolean winByCheckmate(Side side) {
		Entity king = getKing(side);
		if (!isEatenPoint(king.getPoint(), side)) {
			return false;
		}
		return drawCannotMove(side);
	}
	public boolean isCheck(Side side) {
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}
	
	public boolean isDraw(Side side) {
		return drawCannotMove(side);
	}
	
	public boolean drawCannotMove(Side side) {
		ArrayList<Entity> allEntity = getAllPieces(side);
		for(Entity entity : allEntity) {
			if (entity == null) continue;
			ArrayList<Point> moveList = moveList(entity.getPoint());
			if (moveList.size() != 0) return false;
		}
		return true;
	}
	//move
	public void move(Point oldPoint, Point newPoint) {
		Entity moveEntity = this.getEntity(oldPoint);
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
		if (moveEntity instanceof King && isCastlingPoint(moveEntity.getSide(), newPoint)) {
			castling(moveEntity.getSide(), oldPoint, newPoint);
		}else {
			remove(oldPoint);
			moveEntity.setPoint(newPoint);
			addEntity(moveEntity, newPoint);
			int s = (moveEntity.getSide() == Side.BLACK)? 7 : 0;
			if (moveEntity instanceof Pawn && newPoint.x == s) {
				havePromotion(newPoint, moveEntity.getSide());
			}
		}
	}
	//moveList
	protected ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint){
		Entity moveEntity = this.getEntity(oldPoint);
		Side side = moveEntity.getSide();
		ArrayList<Point> op = new ArrayList<Point>();
		op.add(oldPoint);
		if (moveEntity instanceof King) {
			for(int i = movePoint.size()-1; i>=0; i--) {
				if (checkCannotMovePoint(op,new Point(-1,-1),movePoint.get(i),side)) movePoint.remove(i);
			}
			for(Point p : castingPoint(side)) {//for castling
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
	
}
