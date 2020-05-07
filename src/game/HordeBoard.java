package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class HordeBoard extends Board implements CheckMateAble{
	public HordeBoard(String[][] map) {
		super(map);
	}

	public boolean isWin(Side side) {
		return (side==Side.BLACK) ? winByEatenAll(side) : winByCheckmate(side);
	}

	public boolean winByEatenAll(Side side) {
		ArrayList<Entity> allPieces = getAllPieces(side);
		return allPieces.size() == 0;
	}
	
	public boolean winByCheckmate(Side side) {
		Entity king = getKing(side);
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
		if (side == Side.BLACK)
			return false;
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}

	protected ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint) {
		if (getEntity(oldPoint).getSide() == Side.BLACK) {
			return super.editMovePoint(oldPoint, movePoint);
		}
		return movePoint;
	}
}
