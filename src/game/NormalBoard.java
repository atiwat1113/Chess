package game;

import java.awt.Point;
import java.util.ArrayList;

import application.AppManager;
import entity.*;
import entity.base.Entity;
import entity.base.Castling;
import logic.Side;
import game.base.*;
import javafx.application.Platform;
import javafx.util.Duration;

public class NormalBoard extends Board implements CheckMateAble {

	public NormalBoard(String[][] map) {
		super(map);
	}

	// iswin
	public boolean isWin(Side side) {
		return winByCheckmate(side);
	}

	public boolean winByCheckmate(Side side) {
		Entity king = getKing(side);
		if (king == null)
			return false;
		if (!isEatenPoint(king.getPoint(), side)) {
			return false;
		}
		return drawCannotMove(side);
	}

	public boolean isDraw(Side side) {
		return drawCannotMove(side);
	}

	public boolean drawCannotMove(Side side) {
		ArrayList<Entity> allEntity = getAllPieces(side);
		for (Entity entity : allEntity) {
			if (entity == null)
				continue;
			ArrayList<Point> moveList = moveList(entity.getPoint());
			if (moveList.size() != 0)
				return false;
		}
		return true;
	}

	public boolean isCheck(Side side) {
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}

	// move
	public void move(Point oldPoint, Point newPoint) {
		Entity moveEntity = this.getEntity(oldPoint);
		if (moveEntity instanceof Castling)
			((Castling) moveEntity).setNeverMove();
		if (moveEntity instanceof Pawn) {
			if (twoWalkPawn != null && twoWalkPawn.equals(new Point(oldPoint.x, newPoint.y))) {
				//AppManager.setEnPassant(true,new Point(oldPoint.x, newPoint.y));// for moving animation ------------------------
				remove(twoWalkPawn);
			} else if (Math.abs(oldPoint.x - newPoint.x) == 2) {
				twoWalkPawn = newPoint;
			} else
				twoWalkPawn = null;
		} else
			twoWalkPawn = null;
		if (moveEntity instanceof King && isCastlingPoint(moveEntity.getSide(), newPoint)) {
			castling(moveEntity.getSide(), oldPoint, newPoint);
		} else {
			remove(oldPoint);
			moveEntity.setPoint(newPoint);
			addEntity(moveEntity, newPoint);
			int s = (moveEntity.getSide() == Side.BLACK) ? 7 : 0;
			if (moveEntity instanceof Pawn && newPoint.x == s) {
				havePromotion(newPoint, moveEntity.getSide());
			}
		}
	}
	public void startAnimation(Point oldPoint, Point newPoint) {
		removePoint = new ArrayList<Point>();
		Entity moveEntity = this.getEntity(oldPoint);
		if (moveEntity instanceof Castling)
			((Castling) moveEntity).setNeverMove();
		if (moveEntity instanceof Pawn) {
			if (twoWalkPawn != null && twoWalkPawn.equals(new Point(oldPoint.x, newPoint.y))) {
				//AppManager.setEnPassant(true,new Point(oldPoint.x, newPoint.y));// for moving animation ------------------------
				removePoint.add(twoWalkPawn);
			} else if (Math.abs(oldPoint.x - newPoint.x) == 2) {
				twoWalkPawn = newPoint;
			} else
				twoWalkPawn = null;
		} else {
			twoWalkPawn = null;
		}
		if (moveEntity instanceof King && isCastlingPoint(moveEntity.getSide(), newPoint)) {
			castling(moveEntity.getSide(), oldPoint, newPoint);
		} else {
			movePiece = moveEntity;
			movePoint = newPoint;
			remove(oldPoint);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AppManager.startAnimation(oldPoint, newPoint, moveEntity);
				}
			});
			
			//startAnimation(Point start, Point end, Entity entity)
			int s = (moveEntity.getSide() == Side.BLACK) ? 7 : 0;
			if (moveEntity instanceof Pawn && newPoint.x == s) {
				havePromotion(newPoint, moveEntity.getSide());
			}
		}
	}

	// moveList
	protected ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint) {
		Entity moveEntity = this.getEntity(oldPoint);
		Side side = moveEntity.getSide();
		ArrayList<Point> op = new ArrayList<Point>();
		op.add(oldPoint);
		if (moveEntity instanceof King) {
			for (int i = movePoint.size() - 1; i >= 0; i--) {
				if (checkCannotMovePoint(op, new Point(-1, -1), movePoint.get(i), side))
					movePoint.remove(i);
			}
			for (Point p : castingPoint(side)) {// for castling
				movePoint.add(p);
			}
		} else {
			Point kingPoint = getKing(side).getPoint();
			for (int i = movePoint.size() - 1; i >= 0; i--) {
				if (checkCannotMovePoint(op, movePoint.get(i), kingPoint, side)) {
					movePoint.remove(i);
				}
			}
			if (moveEntity instanceof Pawn) {
				if (twoWalkPawn != null) {
					if (oldPoint.x == twoWalkPawn.x && Math.abs(oldPoint.y - twoWalkPawn.y) == 1) {
						Point newPoint;
						op.add(twoWalkPawn);
						if (side == Side.BLACK)
							newPoint = new Point(twoWalkPawn.x + 1, twoWalkPawn.y);
						else
							newPoint = new Point(twoWalkPawn.x - 1, twoWalkPawn.y);
						if (!checkCannotMovePoint(op, newPoint, kingPoint, side)) {
							movePoint.add(newPoint);
						}
					}
				}
			}
		}
		return movePoint;
	}

}
