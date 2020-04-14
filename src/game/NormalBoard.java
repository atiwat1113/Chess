package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.*;
import entity.base.Entity;
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
	
	
}
