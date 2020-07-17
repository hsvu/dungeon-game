package unsw.dungeon;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Exit extends Entity {
	
	private boolean completedStage;
	private String message;
	private Alert alert;
	
	/**
	 * Create Exit entity
	 * @param int x = x position in dungeon
	 * @param int y = x position in dungeon
	 */
	public Exit(int x, int y) {
		super(x, y);
		this.completedStage = false;
		this.message = "";
		this.alert = new Alert(AlertType.NONE);
	}
	
	/**
	 * get constructor completedstage
	 * @return
	 */
	public boolean isCompletedStage() {
		return completedStage;
	}

	/**
	 * get constructor message
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * set constructor completed stage
	 * @param bool
	 */
	public void setCompletedStage(boolean bool) {
		this.completedStage = bool;
	}
	
	/**
	 * set constructor message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Alert getAlert() {
		return alert;
	}
	
	
}
