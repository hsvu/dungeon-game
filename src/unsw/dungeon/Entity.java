package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty isInDungeon;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);    
        this.isInDungeon = new SimpleBooleanProperty();
        isInDungeon.set(true);
    }
    
    /**
     * Get the X position of an entity
     * @param x
     */
    public void setX(int x) {
    	x().set(x);
	}
    
    /**
     * Set Y position of an entity
     * @param y
     */
	public void setY(int y) {
		y().set(y);
	}
	
	/**
	 * Get x position as IntegerProperty
	 * @return x as IntegerProperty
	 */
	public IntegerProperty x() {
        return x;
    }
	
	/**
	 * Get y position as IntegerProperty
	 * @return x as IntegerProperty
	 */
    public IntegerProperty y() {
        return y;
    }
    
	/**
	 * Get isInDungeon as BooleanProperty
	 * @return isInDungeon as BooleanProperty
	 */
    public BooleanProperty isInDungeon() {
        return isInDungeon;
    }
    
    /**
     * Set isInDungeon as BooleanProperty
     * @param bool to set
     */
    public void setIsInDungeon(boolean bool) {
    	isInDungeon().set(bool);
    }
    
    /**
     * Get Y position of entity
     * @return y position as int
     */
    public int getY() {
        return y().get();
    }
    
    /**
     * Get X position of entity
     * @return x position as int
     */
    public int getX() {
        return x().get();
    }


}

