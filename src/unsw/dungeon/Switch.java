package unsw.dungeon;

public class Switch extends Entity {

	private static final boolean NOT_TRIGGERED = false;
	private static final boolean TRIGGERED = true;
	
	private boolean triggered; 
	private int triggerCount = 0;
	private Dungeon dungeon;
	
	/**
	 * Create floorSwitch object
	 * @param x
	 * @param y
	 */
	public Switch(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		this.triggered = NOT_TRIGGERED;
	}
	
	/**
	 * Check if floor switch is triggered
	 * @return boolean (true if triggered, false if not)
	 */
	public boolean isTriggered() {
		return triggered;
	}

	/**
	 * Increase the trigger count by one
	 */
	public void increaseTriggerCount() {
		int count = dungeon.getTriggeredSwitchCount().get() + 1;
		dungeon.setTriggeredSwitchCount(count);
	}
	
	/**
	 * Decrease the trigger count by one
	 */
	public void decreaseTriggerCount() {
		int count = dungeon.getTriggeredSwitchCount().get() - 1;
		dungeon.setTriggeredSwitchCount(count);
	}
	
	/**
	 * Set the trigger to either true or false
	 * @param bool
	 */
	public void setTrigger(boolean bool) {
		this.triggered = bool;
	}
}
