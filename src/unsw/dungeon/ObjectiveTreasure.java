package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;

public class ObjectiveTreasure implements Objective {

	@Override
	public void isObjectiveCompleted(Dungeon dungeon) {
		List<Entity> entities = dungeon.getEntities();
		for (Entity entity: entities) {
			if (entity instanceof Treasure) {
				isComplete.set(false);
			}
		}
		
		isComplete.set(true);
	}
	
	@Override
	public BooleanProperty getIsComplete() {
		return isComplete;
	}

}
