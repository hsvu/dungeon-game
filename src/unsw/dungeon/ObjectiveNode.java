package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ObjectiveNode {

	private Objective goal;
	private String goalName;
	private BooleanProperty goalComplete;
	
	public ObjectiveNode(String goal) {
		
		this.goalComplete = new SimpleBooleanProperty();
		goalComplete.set(false);
		
		if (goal.equals("exit")) {
			this.goal = new ObjectiveExit();
			this.goalName = "exit";
		} else if (goal.equals("treasure")) {
			this.goal = new ObjectiveTreasure();
			this.goalName = "treasure";
		} else if (goal.equals("boulder")) {
			this.goal = new ObjectiveBoulder();
			this.goalName = "boulder";
		} else if (goal.equals("enemies")) {
			this.goal = new ObjectiveEnemies();
			this.goalName = "enemies";
		}
		goalComplete.bindBidirectional(this.goal.getIsComplete());
		
	}

	public Objective getGoal() {
		return goal;
	}
	
	public String getGoalName() {
		return goalName;
	}


	public BooleanProperty getGoalComplete() {
		return goalComplete;
	}

	public void setGoalComplete(boolean goalComplete) {
		this.goalComplete.set(goalComplete);
	}

}
