package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class NormalBoard extends Board implements CheckMateAble {

	public NormalBoard(String[][] map) {
		super(map);
	}

	// win
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
