package unsw.dungeon;

public class Key extends Entity {
	
	private int id;
	
	/**
	 * Create Key entity
	 * @param x
	 * @param y
	 * @param name = name of the key
	 */
	public Key(int x, int y, int id) {
		super(x, y);
		this.id = id;
	}
	
	/**
	 * Get the id of the key
	 * @return id of the key
	 */
	public int getId() {
		return this.id;
	}
	
	
}
