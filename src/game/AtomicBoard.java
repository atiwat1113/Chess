package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.King;
import entity.Knight;
import entity.Pawn;
import entity.base.Entity;
import entity.base.HaveCastling;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class AtomicBoard extends Board implements CheckMateAble{
	public AtomicBoard(String[][] map) {
		super(map);
	}
	//iswin
	public boolean isWin(Side side) {
		return winByCheckmate(side) || winByLosingKing(side);
	}
	
	public boolean winByCheckmate(Side side) {
		Entity king = getKing(side);
		if (!isEatenPoint(king.getPoint(), side)) {
			return false;
		}
		return drawCannotMove(side) || drawByLosing2King();
	}
	
	public boolean winByLosingKing(Side side) {
		Entity king = getKing(side);
		for (Entity entity : getAllPieces(side)) {
			if (entity instanceof King) return false;
		}
		return true;
	}
	
	public boolean isCheck(Side side) {
		return false;
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
	
	public boolean drawByLosing2King() {
		return winByLosingKing(Side.WHITE) && winByLosingKing(Side.BLACK);
	}
	
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
				if (getEntity(newPoint)==null) {
					moveEntity.setPoint(newPoint);
					addEntity(moveEntity, newPoint);
				}else {
					remove(newPoint);
					for(Point vector: KingWalk) {
						explosion(addPoint(newPoint, vector));
					}
				}
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
	
	protected ArrayList<Point> removeCannotMovePoint(Point oldPoint, ArrayList<Point> movePoint){
		Entity moveEntity = this.getEntity(oldPoint);
		Side side = moveEntity.getSide();
		ArrayList<Point> op = new ArrayList<Point>();
		op.add(oldPoint);
		ArrayList<Point> oppositeKingPoints = new ArrayList<Point>();
		ArrayList<Point> kingPoints = new ArrayList<Point>();
		Point opKingPoint = getKing(getAnotherSide(side)).getPoint();
		Point kingPoint = getKing(side).getPoint();
		for (Point vector : KingWalk) {
			kingPoints.add(addPoint(kingPoint, vector));
			oppositeKingPoints.add(addPoint(opKingPoint, vector));
		}
		if (moveEntity instanceof King) {
			for(int i = movePoint.size()-1; i>=0; i--) {
				if (getEntity(movePoint.get(i))!=null) {
					movePoint.remove(i);
					continue;
				}
				if (oppositeKingPoints.contains(movePoint.get(i))) continue;
				if (checkCannotMovePoint(op,new Point(-1,-1),movePoint.get(i),side)) movePoint.remove(i);
			}
			for(Point p : ((King) moveEntity).castingPoint(this)) {//for castling
				movePoint.add(p);
			} 
		}else {
			for(int i = movePoint.size()-1; i>=0; i--) {
				if (kingPoints.contains(movePoint.get(i))) {
					if (getEntity(movePoint.get(i))!=null) {
						movePoint.remove(i);
						continue;
					}
				}
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
	//other
	private void explosion(Point point) {
		if (!isInBoard(point)) return;
		if (!(getEntity(point) instanceof Pawn)) remove(point);
	}
}
