/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private ArrayList<Objective> objectives;
    private Player player;
    private ObjectiveNode goalNode;
    private IntegerProperty triggeredSwitchCount;

    public Dungeon(int width, int height, ObjectiveNode goalNode) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.objectives = new ArrayList<>();
        this.player = null;
        this.goalNode = goalNode;
        this.triggeredSwitchCount = new SimpleIntegerProperty(0);
        
        // set objectives
        setObjectives();
    }
    
     /**
     * Add an objective to the objectives list
     */
    public void addObjective(Objective objective) {
    	this.objectives.add(objective);
    }
    
    /**
     * @return width of the dungeon
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @return height of the dungeon
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return get player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return player's X position
     */
    public int getPlayerXPos() {
    	return this.player.getX();
    }
    
    /**
     * @return player's Y position
     */
    public int getPlayerYPos() {
    	return this.player.getY();
    }
    
    /**
     * @param entity
     * @return get X position of a particular entity
     */
    public int getEntityXPos(Entity entity) {
    	return entity.getX();
    }
    
    /**
     * @param entity
     * @return get Y position of a particular entity
     */
    public int getEntityYPos(Entity entity) {
    	return entity.getY();
    }
    
    /**
     * Set a player entity
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add an entity to the entities list
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * Remove entity from entities list
     * @return void
     */
    public void removeEntity(Entity entity) {
    	this.entities.remove(entity);
    }
    
    public ObjectiveNode getGoalNode() {
    	return goalNode;
    }
    
    /**
     * Loops through all the entities and tries to find what
     * is at a particular location
     * @param x
     * @param y
     * @return Entity if entity exists at that location
     * @return if nothing is at that location return null
     */
    public Entity entityAtPos(int x, int y) {
    	for (Entity entity: this.entities) {
    		if (entity.getX() == x &&
    			entity.getY() == y) {
    			return entity;
    		}
    	}
    	
    	return null;
    }
    

	/**
	 * Find all the entities surrounding another entity
	 * including the square the entity is on
	 * @param entity
	 * @return List<Entity>
	 */
	public List<Entity> getSurroundingEntities(Entity entity) {
		
		List<Entity> withinRange = new ArrayList<>();
		
		int x = entity.getX();
		int y = entity.getY();
		
		// Top left corner
		if (x > 0 && y > 0) {
			Entity enitity = entityAtPos(x - 1, y - 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Top middle
		if (y > 0) {
			Entity enitity = entityAtPos(x, y - 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Top right hand corner
		if (x < (this.width - 1) && y > 0) {
			Entity enitity = entityAtPos(x + 1, y - 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Mid row to the left
		if (x > 0) {
			Entity enitity = entityAtPos(x - 1, y);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Position bomb is on
		Entity bombPos = entityAtPos(x, y);
		if (entity != null) withinRange.add(bombPos);
		
		// Mid row to the right
		if (x < (this.width - 1)) {
			Entity enitity = entityAtPos(x + 1, y);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Bottom left corner
		if (x > 0 && y < (this.height - 1)) {
			Entity enitity = entityAtPos(x - 1, y + 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Bottom middle
		if (y < (this.height - 1)) {
			Entity enitity = entityAtPos(x, y + 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		// Bottom right hand corner
		if (x < (this.width - 1) && y < (this.height - 1)) {
			Entity enitity = entityAtPos(x + 1, y + 1);
			if (entity != null) withinRange.add(enitity);
		}
		
		return withinRange;
	}
	
	/**
	 * Returns a list of entities in the dungeon
	 * @return entities
	 */
	public List<Entity> getEntities() {
		return this.entities;
	}

	/**
	 * Returns all objectives
	 * @return objectives
	 */
	public ArrayList<Objective> getObjectives() {
		return objectives;
	}
	
	/**
	 * Putting all objectives in list
	 */
	public void setObjectives() {
		if (goalNode.getGoal().equals("exit")) {
			objectives.add(new ObjectiveExit());
		} else if (goalNode.getGoal().equals("treasure")) {
			objectives.add(new ObjectiveTreasure());
		} else if (goalNode.getGoal().equals("boulder")) {
			objectives.add(new ObjectiveBoulder());
		} else if (goalNode.getGoal().equals("enemies")) {
			objectives.add(new ObjectiveEnemies());
		} else if (goalNode.getGoal().equals("OR")) {
			
		} else { // if (goalType.equals("AND"))
			
		} 
	}
	
	/**
	 * Checking if all the objectives are completed
	 * @return boolean
	 */
	public boolean isDungeonCompleted() {
		for (Objective objective : this.objectives) {
			objective.isObjectiveCompleted(this);
			if (objective.getIsComplete().get()) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	public boolean isOnSameSquare(Entity entity1, Entity entity2) {
		if (entity1.getX() == entity2.getX()
				&& entity1.getY() == entity2.getY()) {
			return true;
		}
		return false;
	}
	
	public IntegerProperty getTriggeredSwitchCount() {
		return triggeredSwitchCount;
	}
	
	public void setTriggeredSwitchCount(int value) {
		this.triggeredSwitchCount.set(value);;
	}
	
	
}

