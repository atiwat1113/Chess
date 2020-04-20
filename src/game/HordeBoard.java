package game;

import java.awt.Point;
import java.util.ArrayList;

import entity.base.Entity;
import logic.GameController;
import logic.Side;

public class HordeBoard extends NormalBoard{
	public HordeBoard(String[][] map) {
		super(map);
	}
	public boolean isWin(Side side) {
		return winByCheckmate(side) || winByEatenAll(side);
	}
	public boolean winByEatenAll(Side side) {
		ArrayList<Entity> allPieces=getAllPieces(side);
		System.out.println(allPieces.size());
		return allPieces.size()==0;
	}
	public boolean isCheck(Side side) {
		if (side == Side.BLACK) return false;
		Point kingPoint = getKing(side).getPoint();
		return isEatenPoint(kingPoint, side);
	}
	protected ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint){
		if (getEntity(oldPoint).getSide() == Side.BLACK) {
			return super.editMovePoint(oldPoint, movePoint);
		}
		return movePoint;
	}
}
