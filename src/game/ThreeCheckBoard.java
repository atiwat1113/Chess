package game;

import java.awt.Point;
import application.AppManager;
import logic.Side;

public class ThreeCheckBoard extends NormalBoard {
	private int whiteCheck, blackCheck;

	public ThreeCheckBoard(String[][] map) {
		super(map);
		whiteCheck = blackCheck = 0;
	}

	@Override
	public boolean isWin(Side side) {
		return winByCheckmate(side) || winByThreeCheck(side);
	}

	public boolean winByThreeCheck(Side side) {
		return (side == Side.BLACK) ? whiteCheck == 3 : blackCheck == 3;
	}

	public void move(Point oldPoint, Point newPoint) {
		super.move(oldPoint, newPoint);
		Side side = getAnotherSide(getEntity(newPoint).getSide());
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
