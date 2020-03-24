package entity.base;

import java.awt.*;
import java.util.ArrayList;

import logic.Board;
import logic.Side;

public abstract class Entity { // แก้ private --> protected
	protected Point p;
	protected boolean isMove;
	protected Side side;

	public Entity(Point p, Side side) {// Point (y,x)
		this.p = p;
		this.side = side; // น่าจะต้องบอกฝั่งไว้ตั้งแต่ตอนสร้างหมาก
		isMove = false;
	}

	public abstract int getSymbol();

	public abstract Point[] canMove();

	public boolean move(Point p) { // การ move ของทุกหมากมีรูปแบบเดียวกัน
		for (Point moveablePoint : this.canMove()) {
			if (moveablePoint.equals(p)) {
				this.p = p;
				return true;
			}
		}
		return false;
	}

	public Point[] canEat(Board board) { // การเช็คช่องที่กินได้ของทุกหมากมีวิธีการเช็คเหมือนกัน
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
