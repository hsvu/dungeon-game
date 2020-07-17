package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;

public class ObjectiveEnemies implements Objective {

	@Override
	public void isObjectiveCompleted(Dungeon dungeon) {
		List<Entity> entities = dungeon.getEntities();
		for (Entity entity: entities) {
			if (entity instanceof Enemy) {
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
