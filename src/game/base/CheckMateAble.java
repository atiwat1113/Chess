package game.base;

import logic.Side;

public interface CheckMateAble {
	public boolean winByCheckmate(Side side);
	public boolean drawCannotMove(Side side);
}
