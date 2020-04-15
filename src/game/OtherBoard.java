package game;

import game.base.Board;
import logic.Side;

public class OtherBoard extends Board{
	public OtherBoard(String[][] map) {
		super(map);
	}
	public boolean isWin(Side side) {
		return false;
	}
	public boolean isDraw(Side side) {
		return false;
	}
}
