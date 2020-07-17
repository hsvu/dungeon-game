package unsw.dungeon;

public class Enemy extends Entity {
	
	private Dungeon dungeon;
	
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}
	
	/**
	 * Move enemy closer to the player.
	 * If player has potion the move further away
	 * @param player
	 */
	public void moveTo(Player player) {
		
		boolean madeMove = false;
		int playerX = player.getX();
		int playerY = player.getY();
		int enemyX = this.getX();
		int enemyY = this.getY();
		
		// if player has potion, enemy runs away
		// swap variables for player and enemy
		if (player.getHasPotion().get()) {
			enemyX = playerX;
			enemyY = playerY;
			playerX = this.getX();
			playerY = this.getY();
		}
		
		// if enemy is to the right of player
		if (enemyX > playerX) {
			madeMove = moveLeft();
			if (madeMove) return;
		} else if (enemyX < playerX) {
			madeMove = moveRight();
			if (madeMove) return;
		}
		
		// if enemy is below player
		if (enemyY > playerY) {
			madeMove = moveUp();
			if (madeMove) return;
		} else if (enemyY < playerY) {
			madeMove = moveDown();
			if (madeMove) return;
		}
		
		return;
	}
	
	/**
     * Moves enemy up one position
     * @return boolean
     */
    private boolean moveUp() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY - 1;
    	
        if (currY > 0 &&
        	!isBlocked(currX, yPosToGo)) {
        	setY(yPosToGo);
        	checkPlayer();
        	return true;
        }
        return false;
    }
    
    /**
     * Moves enemy down one position
     * @return boolean
     */
    private boolean moveDown() {
    	int currX = getX();
    	int currY = getY();
    	int yPosToGo = currY + 1;
    	
        if (currY < (dungeon.getHeight() - 1) &&
        	!isBlocked(currX, yPosToGo)) {
        	setY(yPosToGo);
        	checkPlayer();
        	return true;
        }
        return false;
    }

    /**
     * Moves enemy to the left by one position
     * @return boolean
     */
    private boolean moveLeft() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX - 1;
    	
        if (currX > 0 &&
        	!isBlocked(xPosToGo, currY)) {
        	setX(xPosToGo);
        	checkPlayer();
        	return true;
        }
        return false;
    }

    /**
     * Moves enemy to the left by one position
     * @return boolean
     */
    private boolean moveRight() {
    	int currY = getY();
    	int currX = getX();
    	int xPosToGo = currX + 1;
    	
        if (currX < (dungeon.getWidth() - 1) &&
        	!isBlocked(xPosToGo, currY)) {
        	setX(xPosToGo);
        	checkPlayer();
        	return true;
        }
        return false;
    }
    
    /**
     * checking if path is blocked
     * @param x
     * @param y
     * @return
     */
    private boolean isBlocked(int x, int y) {
    	for (Entity entity : dungeon.getEntities()) {
    		if (entity instanceof Wall
    				|| entity instanceof Boulder
    				|| entity instanceof Bomb) {
    			
    				return true;
    		}
    		
    		if (entity instanceof Door) {
    			if (!((Door) entity).isOpen().get()) {
    				return true;
    			}
    		}
    		
    		if (entity instanceof Switch) {
    			if (((Switch) entity).isTriggered()) {
    				return true;
    			}
    		}
    		
    	}
    	return false;
    }
	
	
    /**
     * To kill enemy
     */
	public void killEnemy() {
		dungeon.removeEntity(this);
		this.setIsInDungeon(false);
	}
	
	/**
	 * To kill player
	 * @param player
	 */
	public void killPlayer(Player player) {
		if (player.getHasPotion().get()) return;
		player.killed();
	}
	
	/**
	 * checking if the given position has a player
	 */
	private void checkPlayer() {
		for (Entity entity : dungeon.getEntities()) {
			if (entity instanceof Player) {
				if (dungeon.isOnSameSquare(entity, this)) {
					killPlayer((Player) entity);
				}
			}
		}
	}
	
}
