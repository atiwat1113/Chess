package entity.base;

import java.awt.*;

import logic.Side;


public abstract class Entity {
	private Point p;
	private boolean isMove;
	private Side side;
	
	public Entity(Point p) {// Point (y,x)
		this.p = p;
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
