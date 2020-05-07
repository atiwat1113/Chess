package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class KingOfTheHillBoard extends Board implements CheckMateAble{
	private ArrayList<Point> centerPoint;

	public KingOfTheHillBoard(String[][] map) {
		super(map);
		centerPoint = new ArrayList<Point>();
		centerPoint.add(new Point(3, 3));
		centerPoint.add(new Point(3, 4));
		centerPoint.add(new Point(4, 3));
		centerPoint.add(new Point(4, 4));
	}

	@Override
	public boolean isWin(Side side) {
		return winByCheckmate(side) || winByCenterKing(side);
	}

	public boolean winByCenterKing(Side side) {
		Entity king = getKing(getAnotherSide(side));
		Point pointKing = king.getPoint();
		return centerPoint.contains(pointKing);
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
	
	// draw
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

	// check
	public boolean isCheck(Side side) {
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}
	
}
