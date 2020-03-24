package entity.base;

import java.awt.*;

import logic.Side;


public abstract class Entity {
	protected Point p;
	protected boolean isMove;
	protected Side side;
	
	public Entity(Point p,Side side) {// Point (y,x)
		this.p = p;
		this.side = side;
		isMove = false;
	}
	
	public abstract int getSymbol();
	public abstract boolean move(Point p);
	public abstract Point[] canMove();
	public abstract Point[] canEat();
	
	public void remove() {//----------------------------------------
		
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

}
