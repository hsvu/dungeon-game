package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;

public class ObjectiveBoulder implements Objective {

	@Override
	public void isObjectiveCompleted(Dungeon dungeon) {
		List<Entity> entities = dungeon.getEntities();
		
		// Check if there is any floor switches aren't
		// triggered
		for (Entity entity : entities) {
			if (entity instanceof Switch &&
				!((Switch) entity).isTriggered()) {
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
