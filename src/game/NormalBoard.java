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
	
	public String print(Point p) {//for debug
		String pp=""+p;
		int s1,s2,s3,s4;
		s1=17;
		s2=pp.indexOf(",",s1);
		s3=s2+3;
		s4=pp.indexOf("]",s3);
		return "("+pp.substring(s1,s2)+","+pp.substring(s3,s4)+")";
	}
}
