package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import game.base.Board;
import game.base.CheckMateAble;
import logic.Side;

public class ThreeCheckBoard extends Board implements CheckMateAble{
	private int whiteCheck, blackCheck;

	public ThreeCheckBoard(String[][] map) {
		super(map);
		whiteCheck = blackCheck = 0;
	}

	@Override
	public boolean isWin(Side side) {
		return winByCheckmate(side) || winByThreeCheck(side);
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
	
	public boolean winByThreeCheck(Side side) {
		return (side == Side.BLACK) ? whiteCheck == 3 : blackCheck == 3;
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
	
	public void continueMove() {
		super.continueMove();
		Side side = getAnotherSide(movePiece.getSide());
		if (isCheck(side)) {
			if (side == Side.WHITE)
				blackCheck++;
			else
				whiteCheck++;
		}
	}

	public int[] getCheckNumber() {
		int[] re = { whiteCheck, blackCheck };
		return re;
	}
}
