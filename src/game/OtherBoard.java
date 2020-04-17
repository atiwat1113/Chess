package game;

import java.awt.Point;
import java.util.ArrayList;

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
	public boolean isCheck(Side side) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean move(Point oldPoint, Point newPoint, ArrayList<Point> moveList) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected ArrayList<Point> editMovePoint(Point oldPoint, ArrayList<Point> movePoint) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean checkCannotMovePoint(ArrayList<Point> oldPoint, Point newPoint, Point kingPoint, Side side) {
		// TODO Auto-generated method stub
		return false;
	}
}
