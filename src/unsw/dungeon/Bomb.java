package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Bomb extends Entity {
	
	private boolean isLit;
	private Dungeon dungeon;
	
	/**
	 * Create Bomb entity
	 * @param x
	 * @param y
	 */
	public Bomb(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.isLit = false;
		this.dungeon = dungeon;
	}

	public boolean isLit() {
		return isLit;
	}
	
	/**
	 * Lit the bomb
	 */
	public void litBomb() {
		this.isLit = true;		
	}
	
	public void explodeBomb(Timeline explode2,
			Timeline explode3, Timeline explode4, Timeline reset) {
		explode3.setDelay(Duration.seconds(1));
		explode4.setDelay(Duration.seconds(2));
		reset.setDelay(Duration.seconds(3));
		explode2.play();
		explode3.play();
		explode4.play();
		reset.play();
	}
	
	public void bombKill() {
		Entity bomb = (Entity) this;
		List<Entity> entitiesToDel = dungeon.getSurroundingEntities(bomb);
			
		// Kill all enemies & boulders within range
		for (Entity entity: entitiesToDel) {
			if (entity instanceof Enemy ||
				entity instanceof Boulder) {
				dungeon.removeEntity(entity);
				entity.setIsInDungeon(false);
			} else if (entity instanceof Player) {
				if (!((Player) entity).getHasPotion().get()) {
					((Player) entity).killed();
				}
			}
		}
	}
	
}
