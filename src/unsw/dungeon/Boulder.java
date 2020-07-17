package unsw.dungeon;

import java.util.List;

import javafx.beans.property.IntegerProperty;

public class Boulder extends Entity {

	private Dungeon dungeon;
	
	public Boulder(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}	

	/**
	 * Check which way the boulder needs to move
	 * @param player
	 * @param direction
	 * @return
	 */
	public boolean allowMovement(Player player, String direction) {
		
		if (direction.equals("UP")) {
			return moveUp();
		} else if (direction.equals("DOWN")) {
			return moveDown();
		} else if (direction.equals("LEFT")) {
			return moveLeft();
		} else { // direction.equals("RIGHT")
			return moveRight();
		}
		
	}

	
	/**
     * Moves boulder up one position
     * @return boolean
     */
    private boolean moveUp() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY - 1;
    	
        if (currY > 0 &&
        	!isBlocked(currX, yPosToGo)) {
        	checkforFloorSwitch(currX, yPosToGo);
        	setY(yPosToGo);
        	return true;
        }
        return false;
    }
    
    /**
     * Moves boulder down one position
     * @return boolean
     */
    private boolean moveDown() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY + 1;
    	
        if (currY < (dungeon.getHeight() - 1) &&
        	!isBlocked(currX, yPosToGo)) {
        	checkforFloorSwitch(currX, yPosToGo);
        	setY(yPosToGo);
        	return true;
        }
        return false;
    }

    /**
     * Moves boulder to the left by one position
     * @return boolean
     */
    private boolean moveLeft() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX - 1;
    	
        if (currX > 0 &&
        	!isBlocked(xPosToGo, currY)) {
        	checkforFloorSwitch(xPosToGo, currY);
        	setX(xPosToGo);
        	return true;
        }
        return false;
    }

    /**
     * Moves boulder to the left by one position
     * @return boolean
     */
    private boolean moveRight() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX + 1;
    	
        if (currX < (dungeon.getWidth() - 1) &&
        	!isBlocked(xPosToGo, currY)) {
        	checkforFloorSwitch(xPosToGo, currY);
        	setX(xPosToGo);
        	return true;
        }
        return false;
    }
    
    /**
     * Check if the boulder can move to a
     * specific position or not
     * @param x
     * @param y
     * @return
     */
    private boolean isBlocked(int x, int y) {
    	for (Entity entity : dungeon.getEntities()) {
    		
    		if (entity.getX() == x && entity.getY() == y) {
    			// If wall then blocked
    			if (entity instanceof Wall) {
    				return true;
    			}
    			
    			// If bomb then blocked
    			else if (entity instanceof Bomb) {
    				return true;
    			}
    			
    			// If enemy then block
    			else if (entity instanceof Enemy) {
    				return true;
    			}
    			
    			// If bloulder and isnt self, then block
    			else if (entity instanceof Boulder &&
    	    			 !entity.equals(this)) {
    				return true;
    			}
    			
    			// If door and its open
    			else if (entity instanceof Door &&
    					 !((Door) entity).isOpen().get()) {
    				return true;
    			}
    			
    			// If its a switch then trigger it
    			else if (entity instanceof Switch) {
    				if (((Switch) entity).isTriggered()) {
    					return true;
    				} else {
    					((Switch) entity).setTrigger(true);
    				}
    			}
    			
			}
    		

    	}
    	
    	return false;
    }
	
	
	/**
	 * Checking if there is a lit bomb in the surrounding area
	 * @return boolean
	 */
	public boolean isDestroyed() {
		
		List<Entity> withinRange = dungeon.getSurroundingEntities(this);
		
		for (Entity entity : withinRange) {
			if (entity instanceof Bomb) {
				if (!((Bomb) entity).isInDungeon().get()) {
					destroyBoulder();
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Destroy the boulder
	 */
	private void destroyBoulder() {
		dungeon.removeEntity(this);
		this.setIsInDungeon(false);
	}
	
	/**
	 * If a boulder has been moved on a floorswitch,
	 * trigger it
	 * @param fs
	 */
	private void triggerFloorSwitch(Switch fs) {
		fs.setTrigger(true);
		fs.increaseTriggerCount();
	}
	
	/**
	 * if a boulder has been moved off a floorswitch,
	 * untrigger it
	 * @param fs
	 */
	private void untriggerFloorSwitch(Switch fs) {
		fs.setTrigger(false);
		fs.decreaseTriggerCount();
	}
	
	/**
	 * checking if there is a floor switch at this
	 * particular position
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkforFloorSwitch(int x, int y) {
		Entity currEntity = dungeon.entityAtPos(x, y);
		if (currEntity instanceof Switch) {
			triggerFloorSwitch((Switch) currEntity);
		}
		return false;
	}
	
}
