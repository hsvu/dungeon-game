package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public class ObjectiveExit implements Objective {

	@Override
	public void isObjectiveCompleted(Dungeon dungeon) {

		// get all exit coordinates
		for (Entity entity : dungeon.getEntities()) {
			if (entity instanceof Exit) {
				if (dungeon.isOnSameSquare(entity, dungeon.getPlayer())) {
					isComplete.set(true);
				}
			}
		}
		
		isComplete.set(false);
	}
	
	@Override
	public BooleanProperty getIsComplete() {
		return isComplete;
	}

}
