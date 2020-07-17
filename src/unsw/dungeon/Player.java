package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {
		
    private Dungeon dungeon;
    private IntegerProperty treasureWallet;
    private Key keyInHand;
    private Bomb bombInHand;
    private Sword swordInHand;
    private boolean canMove;
    
    private BooleanProperty hasPotion;
    private BooleanProperty hasBomb;
    private BooleanProperty hasSword;
    private BooleanProperty hasKey;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {   	
        super(x, y);
        this.dungeon = dungeon;
        this.treasureWallet = new SimpleIntegerProperty(0);
        this.keyInHand = null;
        this.bombInHand = null;
        this.swordInHand = null;
        this.canMove = true;
        this.hasPotion = new SimpleBooleanProperty();
        hasPotion.set(false);
        this.hasBomb = new SimpleBooleanProperty();
        hasBomb.set(false);
        this.hasKey = new SimpleBooleanProperty();
        hasKey.set(false);
        this.hasSword = new SimpleBooleanProperty();
        hasSword.set(false);
    }
    
    /**
     * Checks the square the player is about to move to
     * and executes any necessary functions.
     * If the position contains a key allow player to walk ontop.
     * If the position contains a door allow player to walk ontop.
     * If the position contains a exit allow player to walk ontop.
     * If the position contains a floor switch allow player to walk ontop.
     * If the position contains a bomb allow player to walk ontop.
     * If the position contains a invincible potion allow plater to walk ontop.
     * @return true = is allowed to move to that square
     * @return false = not allowed to move to that square
     */
    private boolean movement(int x, int y, String direction) {
    	Entity entity = dungeon.entityAtPos(x, y);
    	
    	if (!canMove) return false;
    	
    	// If there is nothing there, allow player to move
    	if (entity == null) {
    		return true;
    		
    	// if there is a wall at that position return false
    	} else if (entity instanceof Wall) {
    		return false;
    	
    	// Add 1 if entity is Treasure
    	} else if (entity instanceof Treasure) {
    		int treasureCounter = treasureWallet.get() + 1;
    		this.treasureWallet.set(treasureCounter);;
    		this.dungeon.removeEntity(entity);
    		entity.setIsInDungeon(false);
    	
    	// If its a boulder move the boulder
    	} else if (entity instanceof Boulder) {
    		return ((Boulder) entity).allowMovement(this, direction);
 
    	// If its am enemy, player is dead
    	} else if (entity instanceof Enemy) {
    		((Enemy) entity).killPlayer(this);
    		
    	} else if (entity instanceof Exit) {
    		this.exit((Exit) entity);
    		
    	} else if (entity instanceof Switch) {
    		if(((Switch) entity).isTriggered()) {
    			for (Entity entity2 : this.dungeon.getEntities()) {
    				if (entity2 instanceof Boulder &&
    					dungeon.isOnSameSquare(this, entity2)) {
    					boolean moved = ((Boulder) entity2).allowMovement(this, direction);
    					if (moved) {
    						((Switch) entity).setTrigger(false);
    					}
    				}
    			}
    		}
    	} else if (entity instanceof Door) {
    		if (!((Door) entity).isOpen().get()) return false;
    	} else if (entity instanceof Bomb) {
    		if (((Bomb) entity).isLit()) return false;
    	}
    	
    	return true;
    }
    
	/**
     * Moves player up one position
     * @return void
     */
    public void moveUp() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY - 1;
    	
        if (currY > 0 &&
        	movement(currX, yPosToGo, "UP")) {
        	setY(yPosToGo);
        	goThroughChecks();
        }
    }
    
    /**
     * Moves player down one position
     * @return void
     */
    public void moveDown() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY + 1;
        if (currY < (dungeon.getHeight() - 1) &&
        	movement(currX, yPosToGo, "DOWN")) {
        	setY(yPosToGo);
        	goThroughChecks();
        }
    }
    
	/**
     * Moves player to the left by one position
     * @return void
     */
    public void moveLeft() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX - 1;
    	
        if (currX > 0 &&
        	movement(xPosToGo, currY, "LEFT")) {
        	setX(xPosToGo);
        	goThroughChecks();
        }
    }

    /**
     * Moves player to the left by one position
     * @return void
     */
    public void moveRight() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX + 1;
    	
        if (currX < (dungeon.getWidth() - 1) &&
        	movement(xPosToGo, currY, "RIGHT")) {
        	setX(xPosToGo);
        	goThroughChecks();
        }
    }

    
    
    /**
	 * @return the keyInHand
	 */
	public Key getKeyInHand() {
		return keyInHand;
	}

	/**
	 * @return the bombInHand
	 */
	public Bomb getBombInHand() {
		return bombInHand;
	}

	/**
	 * @return the swordInHand
	 */
	public Sword getSwordInHand() {
		return swordInHand;
	}

	/**
	 * @return the HasPotion
	 */
    public BooleanProperty getHasPotion() {
    	return hasPotion;
    }
    
    public void setHasPotion(boolean bool) {
    	this.hasPotion.set(bool);
    }
	
    /**
	 * @return the treasureWallet
	 */
	public IntegerProperty getTreasureWallet() {
		return treasureWallet;
	}
	
	public BooleanProperty getHasBomb() {
		return hasBomb;
	}
	
	public BooleanProperty getHasKey() {
		return hasKey;
	}
	
	public BooleanProperty getHasSword() {
		return hasSword;
	}
	
    /**
     * @return Get if the player has completed objectives
     */
    public boolean getObjectivesCompleted() {
    	return dungeon.isDungeonCompleted();
    }

    
	
    /**
     * Pick up new key and drop any old key
     * @param key
     */
    public void pickUpKey(Key key) {
    	
    	// Drop the old key
    	if (this.keyInHand != null) {
    		this.keyInHand.setX(getX());
    		this.keyInHand.setY(getY());
    		this.dungeon.addEntity(this.keyInHand);
    	}
    	
    	// Pick up the new key
    	this.keyInHand = key;
    	hasKey.set(true);
    	
    	// Remove the new key from the map
    	this.dungeon.removeEntity(key);
    	key.setIsInDungeon(false);
    }
    
    /**
     * Pick up a bomb
     * @param bomb
     */
    public void pickUpBomb(Bomb bomb) {
    	
    	// Drop the old bomb
    	if (this.bombInHand != null) {
    		this.bombInHand.setX(getX());
    		this.bombInHand.setY(getY());
    		this.dungeon.addEntity(this.bombInHand);
    	}
    	
    	// Pick up the new bomb
    	this.bombInHand = bomb;
    	hasBomb.set(true);
    	
    	// Remove the new bomb from the map
    	this.dungeon.removeEntity(bomb);
    	bomb.setIsInDungeon(false);
    }

    /**
     * Pick up Invincibility Potion
     * @param potion
     */
    public void pickUpPotion(Potion potion) {
    	potion.activatePotion(this);
    }
    
	
    /**
     * Pick up a sword
     * @param sword
     */
    public void pickUpSword(Sword sword) {

    	// Drop the old sword
    	if (this.swordInHand != null) {
    		this.swordInHand.setX(getX());
    		this.swordInHand.setY(getY());
    		this.dungeon.addEntity(this.swordInHand);
    	}
    	
    	// Pick up the new sword
    	this.swordInHand = sword;
    	hasSword.set(true);
    	
    	// Remove the new sword from the map
    	this.dungeon.removeEntity(sword);
    	sword.setIsInDungeon(false);
    }
    
    
    
    
    /**
     * Lit the bomb
     * @return true = if bomb was successfully lit
     * @return false = player doesn't have a bomb
     */
    public boolean lightBomb() {
    	if (this.bombInHand == null) {
    		return false;
    	}
    	
    	// Lit the bomb
    	this.bombInHand.litBomb();
    	
    	// Drop the bomb onto the map
    	this.bombInHand.setX(getX());
		this.bombInHand.setY(getY());
		this.dungeon.addEntity(this.bombInHand);
		hasBomb.set(false);
    	
    	return true;
    }
    
    /**
     * Open the door. If the door is successfully
     * opened, remove the key
     * @return the status of the door
     */
    public int openDoor(Door door) {
    	int doorStatus = door.openDoor(this.keyInHand);
    	if (doorStatus == door.CAN_OPEN) {
    		this.keyInHand = null;
    		hasKey.set(false);
    	}
    	return doorStatus;
    }
   
    /**
     * Use the sword to attack an enemy if they are within
     * range
     * @param sword
     */
	public void useSword(Sword sword) {
		
		List<Entity> withinRange = dungeon.getSurroundingEntities(this);
		
		for (Entity entity : withinRange) {
			if (entity instanceof Enemy) {
				((Enemy) entity).killEnemy();
			}
		}
			
		sword.decreaseSwordHealth();
		checkObjectives();
	}
	
	
	
    
    /**
     * Kill the player
     * @return boolean to tell us player is dead
     */
    public boolean killed() {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setContentText("You have been killed.");
    	alert.show();
    	disableMovement();
    	return true;
    	
    }

    /**
     * Exit the game if objectives are completed
     * @param exit
     */
    public void exit(Exit exit) {
 
		if (this.getObjectivesCompleted()) {
    		exit.setCompletedStage(true); 
    		exit.setMessage("Congratulations!"); 
    		exit.getAlert().setAlertType(AlertType.INFORMATION);
    		exit.getAlert().setContentText(exit.getMessage());
    		exit.getAlert().show();
    		
    	} else {
    		exit.setMessage("Goal not completed.");
    		exit.getAlert().setAlertType(AlertType.WARNING);
    		exit.getAlert().setContentText(exit.getMessage());
    		exit.getAlert().show();
    	}
	}
    
    /**
     * To check if objectives have been completed
     * If so, end game
     */
    private void checkObjectives() {
    	if (!dungeon.isDungeonCompleted()) return;
    	this.exit(new Exit(this.getX(), this.getY()));
		disableMovement();
    }
    
    /**
     * Used after game has ended. Doesn't allow user to continue playing.
     */
    private void disableMovement() {
    	this.canMove = false;
    }

    
    /**
     * Going through all necessary checks
     */
    private void goThroughChecks() {
    	checkEnemy();
    	checkObjectives();
    	checkBomb();
//    	checkPotion();  
    	dungeon.getTriggeredSwitchCount();
    	
    	// checking if player needs to pick up/drop off anything
    	for (Entity entity : dungeon.getEntities()) {
    		if (dungeon.isOnSameSquare(entity, this)
    				&& !entity.equals(this)) {
    			
    			if (entity instanceof Sword) {
    	    		this.pickUpSword((Sword) entity); break;
    	    		
    	    	} else if (entity instanceof Potion) {
    	    		this.pickUpPotion((Potion) entity); break; 
    	    		
    	    	} else if (entity instanceof Bomb) {
    	    		this.pickUpBomb((Bomb) entity); break;
    	    		
    	    	} else if (entity instanceof Key) {
    	    		this.pickUpKey((Key) entity); break;
    	    		
    	    	}
    			
    		}
    	}    	
    }
    
	/**
	 * Make enemy move closer to the player
	 */
	private void checkEnemy() {
		for (Entity entity: dungeon.getEntities()) {
			if (entity instanceof Enemy) {
				((Enemy) entity).moveTo(this);
			}
		}
	}
	
	/**
	 * Function is called every time player moves to 
	 * check if bomb is lit and change its state
	 */
	private void checkBomb() {
		for (Entity entity: dungeon.getEntities()) {
			if (entity instanceof Bomb) {
				Bomb bomb = (Bomb) entity;
				if (bomb.isLit()) {
					bomb.litBomb();
				}
			}
		}
	}
    
//	/**
//	 * Checks if player is invincible
//	 */
//	private void checkPotion() {
//		if (this.hasPotion.get()) {
//			this.potion.activatePotion(this);
//			if (this.potion.getTimer() == 0) {
//				this.potion = null;
//			}
//		}
//	}

}
