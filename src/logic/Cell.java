package logic;

import entity.base.Entity;

public class Cell {
	private Entity myEntity;
	public Cell(){
		myEntity = null;
	}
	public void setEntity(Entity e) {
		myEntity = e;
	}

	public Entity getEntity() {
		return myEntity;
	}

	public void removeEntity() {
		myEntity = null;
	}

}
