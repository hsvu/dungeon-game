package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public interface Objective {
	
	public BooleanProperty isComplete = new SimpleBooleanProperty();
	isComplete.set(false);
	
	/*
	 * Checks if a particular object has been completed
	 * @param dungeon
	 * @return boolean = true if completed, false if not
	 */
	public void isObjectiveCompleted(Dungeon dungeon);
	public BooleanProperty getIsComplete();
	
}
