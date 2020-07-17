package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;

public class Door extends Entity {
	
	public static final int ALREADY_OPENED = -1;
	public static final int INCORRECT_KEY = 0;
	public static final int CAN_OPEN = 1;
	public static final int NO_KEY = 2;
	
	private int id;
	private BooleanProperty isOpen;
	
	/**
	 * Create Key entity
	 * @param x
	 * @param y
	 * @param key = Key object that can open this door
	 */
	public Door(int x, int y, int id) {
		super(x, y);
		this.id = id;
		this.isOpen = new SimpleBooleanProperty();
		isOpen.set(false);
	}
	
	
	
	/**
	 * @return the isOpen
	 */
	public BooleanProperty isOpen() {
		return isOpen;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return id of the door
	 */
	public int getId() {
		return this.id;
	}


	/**
	 * Checks if the door can be opened with the key
	 * and opens the door if everything is key matches.
	 * @return ALREADY_OPENED = If door is already opened
	 * @return INCORRECT_KEY = If key doesn't match the door
	 * @return CAN_OPEN = Door was successfully opened
	 * @return NO_KEY = Player has no key
	 */
	public int openDoor(Key key) {
		// Door is already open
		if (isOpen.get()) {
			return ALREADY_OPENED;
		
		// No key was given
		} else if (key == null) {
			return NO_KEY;
		
		// Correct key
		} else if (key.getId() == this.id) {
			isOpen.set(true);			
			return CAN_OPEN;
		
		// Incorrect key
		} else {
			return INCORRECT_KEY;
		}
		
	}
	
	
}
