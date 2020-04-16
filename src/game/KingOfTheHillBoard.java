package game;

import java.util.ArrayList;
import java.awt.Point;
import entity.base.Entity;
import logic.Side;

public class KingOfTheHillBoard extends NormalBoard{
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
}
