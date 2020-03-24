package entity.base;

import java.awt.*;
import java.util.ArrayList;

import logic.Board;
import logic.Side;

public abstract class Entity { // �� private --> protected
	protected Point p;
	protected boolean isMove;
	protected Side side;

	public Entity(Point p, Side side) {// Point (y,x)
		this.p = p;
		this.side = side; // ��Ҩе�ͧ�͡����������͹���ҧ��ҡ
		isMove = false;
	}

	public abstract int getSymbol();

	public abstract Point[] canMove();

	public boolean move(Point p) { // ��� move �ͧ�ء��ҡ���ٻẺ���ǡѹ
		for (Point moveablePoint : this.canMove()) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				return true;
			}
		}
		return false;
	}

	public Point[] canEat(Board board) { // ����礪�ͧ���Թ��ͧ�ء��ҡ���Ըա��������͹�ѹ
		ArrayList<Point> eatablePoint = new ArrayList<Point>();
		for (Point moveablePoint : this.canMove()) {
			if (board.getEntity(moveablePoint.x, moveablePoint.y) != null
					&& board.getEntity(moveablePoint.x, moveablePoint.y).getSide() != this.side) {
				eatablePoint.add(moveablePoint);
			}
		}
		return (Point[]) eatablePoint.toArray();
	}

	public void remove() {// ----------------------------------------

	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public Side getSide() {
		return side;
	}

}
