package game;

import logic.*;
import game.base.GameController;
import java.awt.*;

public class NormalController extends GameController{
	
	public static boolean isGameWin(Side side) {
		if (!isCheck(side)) return false;
		Point[] moveableKingPoint = board.getKing(getAnotherSide(side)).moveList();
		for (Point p : moveableKingPoint) {
			if(!canEat(side,p))return false;
		}
		return true;
	}
}
