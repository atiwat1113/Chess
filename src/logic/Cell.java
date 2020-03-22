package logic;

import entity.base.Entity;

public class Cell {
	private Entity myEntity;
	private boolean isEmpty;
	
	public Cell() {
		isEmpty = true;
	}
	
	public boolean IsEmpty() {
		return isEmpty;
	}
	
	public boolean setEntity(Entity e) {//---------------------------------------------
		return true;
	}
	
	public Entity getEntity() {
		return myEntity;
	}
	
	public void removeEntity() {
		myEntity = null;
		isEmpty = true;
	}
	
	public int getSymbol() {//------------------------------------------------------------------
		return 0;
	}
}
