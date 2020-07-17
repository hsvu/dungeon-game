package unsw.dungeon;

import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Potion extends Entity {
		
	private Dungeon dungeon;
	/**
	 * Create the invincibilityPotion object
	 * @param x
	 * @param y
	 */
	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}

	/**
	 * Make the potion active and start the counter
	 * @param player
	 */
	public void activatePotion(Player player) {
		usePotion(player);
		dungeon.removeEntity(this);
    	this.setIsInDungeon(false);
	}
	
	public void deactivatePotion(Player player) {
		player.setHasPotion(false);
	}
	
	public void usePotion(Player player) {
		player.setHasPotion(true);
		Timeline invincible = new Timeline(
			new KeyFrame(Duration.seconds(1), e -> {
				deactivatePotion(player);
		}));
		invincible.setDelay(Duration.seconds(9));
		invincible.play();
	}

}
