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
		//--------------------------------------------------------------------------------------------------
		return false;
	}
	
	
}
