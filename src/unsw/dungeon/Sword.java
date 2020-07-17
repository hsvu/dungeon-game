package unsw.dungeon;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Sword extends Entity {
	
	private IntegerProperty health;
	private Dungeon dungeon;
	
	/**
	 * Create sword object
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Sword(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.health = new SimpleIntegerProperty(5); 
		this.dungeon = dungeon;
	}
	
	/**
	 * Decrease the swords health
	 */
	public void decreaseSwordHealth() {
		int counter = this.health.get() - 1;
		this.health.set(counter);
		if (this.health.get() == 0) {
			SwordDisappear();
		}
	}
	
	/**
	 * Remove the sword from the dungeon
	 */
	public void SwordDisappear() {
		dungeon.removeEntity(this);
		this.setIsInDungeon(false);
		dungeon.getPlayer().getHasSword().set(false);
	}
	
	/**
	 * @return get the swor'ds health
	 */
	public IntegerProperty getHealthSword() {
		return health;
	}


}
	

