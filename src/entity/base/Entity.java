package entity.base;

import java.awt.*;

import logic.Board;
import logic.Side;


public abstract class Entity { // แก้ private --> protected
	protected Point p;
	protected boolean isMove;
	protected Side side;
	
	public Entity(Point p,Side side) {// Point (y,x)
		this.p = p;
		this.side = side; // น่าจะต้องบอกฝั่งไว้ตั้งแต่ตอนสร้างหมาก
		isMove = false;
	}
	
	public abstract int getSymbol();
	public abstract boolean move(Point p);
	public abstract Point[] canMove();
	public abstract Point[] canEat(Board board);
	
	public void remove() {//----------------------------------------
		
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
