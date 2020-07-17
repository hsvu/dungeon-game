package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * I modified the code like a shit ton lmao so this whole file doesnt work yeet 
 * fix this last
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author sophiavu
 *
 */



public class DungeonTests {
	/*
	 * Test to allow movement to a space
	 * where there are no entities
	 */
	@Test
	void moveToEmptySpace () {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 2, 2);
		dungeon.addEntity(player);
		// Create entities far away from the player's movement radius
		Wall wall1 = new Wall(5, 5);
		dungeon.addEntity(wall1);
		Wall wall2 = new Wall(4, 5);
		dungeon.addEntity(wall2);
		Wall wall3 = new Wall(4, 4);
		dungeon.addEntity(wall3);
		
		// Move player to the right
		player.moveRight();
		
		// Player should have moved to the right
		assertTrue(player.getX() == 3);
		assertTrue(player.getY() == 2);
	}
	
	/*
	 * Test to see if player is not allowed to move
	 * If there is a wall where they want to move
	 */
	@Test
	void moveOnToEntity() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 4, 5);
		dungeon.addEntity(player);
		// Create entities far away from the player's movement radius
		Wall wall1 = new Wall(5, 5);
		dungeon.addEntity(wall1);
		Wall wall2 = new Wall(4, 5);
		dungeon.addEntity(wall2);
		Wall wall3 = new Wall(4, 4);
		dungeon.addEntity(wall3);
		
		// Move player to the right
		player.moveRight();
		
		// Player should stay where they were
		assertTrue(player.getX() == 4);
		assertTrue(player.getY() == 5);
	}
	
	/*
	 * Check if treasure is collected once player
	 * moves onto a treasure spot
	 */
	@Test
	void collectTreasure() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 6, 3);
		dungeon.addEntity(player);
		// Create Treasure
		Treasure treasure = new Treasure(6, 2);
		dungeon.addEntity(treasure);
		// Create entities far away from the player's movement radius
		Wall wall1 = new Wall(5, 5);
		dungeon.addEntity(wall1);
		Wall wall2 = new Wall(4, 5);
		dungeon.addEntity(wall2);
		Wall wall3 = new Wall(4, 4);
		dungeon.addEntity(wall3);
		
		// Move player to the right
		player.moveUp();
		
		// Check treasure
		assertTrue(player.getTreasureWallet() == 1);
		assertFalse(dungeon.getEntities().contains(treasure));
	}
	
	/*
	 * Check that player doesn't collect treasure they are
	 * far not standing on
	 */
	@Test
	void dontCollectTreasure() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 6, 3);
		dungeon.addEntity(player);
		// Create Treasure
		Treasure treasure = new Treasure(7, 8);
		dungeon.addEntity(treasure);
		// Create entities far away from the player's movement radius
		Wall wall1 = new Wall(5, 5);
		dungeon.addEntity(wall1);
		Wall wall2 = new Wall(4, 5);
		dungeon.addEntity(wall2);
		Wall wall3 = new Wall(4, 4);
		dungeon.addEntity(wall3);
		
		// Move player to the right
		player.moveUp();
		
		// Check treasure
		assertTrue(player.getTreasureWallet() == 0);
		assertTrue(dungeon.getEntities().contains(treasure));
	}
	
	/*
	 * Check that boulder moves to the left when
	 * the player pushes it to the left
	 */
	@Test
	void moveBoulder() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 6, 3);
		dungeon.addEntity(player);
		// Create Boulder
		Boulder boulder = new Boulder(dungeon, 5, 3);
		dungeon.addEntity(boulder);
		
		// Move player to the left
		player.moveLeft();
		
		// Check player and boulder positions
		assertTrue(player.getX() == 5);
		assertTrue(player.getY() == 3);
		assertTrue(boulder.getX() == 4);
		assertTrue(boulder.getY() == 3);
		
	}
	
	/*
	 * Check that boulder doesn't move when boulder
	 * isn't in range of the player
	 */
	@Test
	void dontMoveBoulder() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(14, 14, "AND");
		// Create player at x = 1, y = 1
		Player player = new Player(dungeon, 6, 3);
		dungeon.addEntity(player);
		// Create Boulder
		Boulder boulder = new Boulder(dungeon, 5, 3);
		dungeon.addEntity(boulder);
		
		// Move player down
		player.moveDown();
		
		// Check player and boulder positions
		assertTrue(player.getX() == 6);
		assertTrue(player.getY() == 4);
		assertTrue(boulder.getX() == 5);
		assertTrue(boulder.getY() == 3);
	}
	
	/*
	 * Check if player picks up the key
	 */
	@Test
	void playerPicksUpKey() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Key
		Key key = new Key(8, 2, 1);
		dungeon.addEntity(key);
		
		// Pick up the key
		player.pickUpKey(key);;
		
		// Check
		assertTrue(player.getKeyInHand() == key);
		assertFalse(dungeon.getEntities().contains(key));
	}
	
	/*
	 * Test to see that player cannot pick up key
	 * if its not on the same square its standing on
	 */
	@Test
	void playerCantPickUpKey() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Key
		Key key = new Key(3, 4, 1);
		dungeon.addEntity(key);
		
		// Pick up the key
		player.pickUpKey(key);;
		
		// Check
		assertTrue(player.getKeyInHand() == null);
		assertTrue(dungeon.getEntities().contains(key));
	}
	
	/*
	 * Test to see if player can swap a key
	 */
	@Test
	void playerSwapsKey() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Key
		Key oldKey = new Key(3, 4, 1);
		player.setKeyInHand(oldKey);
		Key newKey = new Key(8, 2, 2);
		dungeon.addEntity(newKey);
		
		// Pick up the key
		player.pickUpKey(newKey);;
		
		// Check
		assertTrue(player.getKeyInHand() == newKey);
		assertTrue(dungeon.getEntities().contains(oldKey));
		assertTrue(oldKey.getX() == 8);
		assertTrue(oldKey.getY() == 2);
		assertFalse(dungeon.getEntities().contains(newKey));
	}
	
	/*
	 * Check if player picks up the bomb
	 */
	@Test
	void playerPicksUpBomb() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Bomb
		Bomb bomb = new Bomb(8, 2);
		dungeon.addEntity(bomb);
		
		// Pick up the bomb
		player.pickUpBomb(bomb);;
		
		// Check
		assertTrue(player.getBombInHand() == bomb);
		assertFalse(dungeon.getEntities().contains(bomb));
	}
	
	/*
	 * Test to see that player cannot pick up bomb
	 * if its not on the same square its standing on
	 */
	@Test
	void playerCantPickUpBomb() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Bomb
		Bomb bomb = new Bomb(3, 4);
		dungeon.addEntity(bomb);
		
		// Pick up the bomb
		player.pickUpBomb(bomb);;
		
		// Check
		assertTrue(player.getBombInHand() == null);
		assertTrue(dungeon.getEntities().contains(bomb));
	}
	
	/*
	 * Test to see if player can swap a bomb
	 */
	@Test
	void playerSwapsBomb() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Bomb
		Bomb oldBomb = new Bomb(3, 4);
		player.setBombInHand(oldBomb);
		Bomb newBomb = new Bomb(8, 2);
		dungeon.addEntity(newBomb);
		
		// Pick up the bomb
		player.pickUpBomb(newBomb);;
		
		// Check
		assertTrue(player.getBombInHand() == newBomb);
		assertTrue(dungeon.getEntities().contains(oldBomb));
		assertTrue(oldBomb.getX() == 8);
		assertTrue(oldBomb.getY() == 2);
		assertFalse(dungeon.getEntities().contains(newBomb));
	}
	
	/*
	 * Check if player picks up the sword
	 */
	@Test
	void playerPicksUpSword() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Sword
		Sword sword = new Sword(dungeon, 8, 2);
		dungeon.addEntity(sword);
		
		// Pick up the sword
		player.pickUpSword(sword);;
		
		// Check
		assertTrue(player.getSwordInHand() == sword);
		assertFalse(dungeon.getEntities().contains(sword));
	}
	
	/*
	 * Test to see that player cannot pick up sword
	 * if its not on the same square its standing on
	 */
	@Test
	void playerCantPickUpSword() {
		// Create dungeon of size 17 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Sword
		Sword sword = new Sword(dungeon, 3, 4);
		dungeon.addEntity(sword);
		
		// Pick up the sword
		player.pickUpSword(sword);;
		
		// Check
		assertTrue(player.getSwordInHand() == null);
		assertTrue(dungeon.getEntities().contains(sword));
	}
	
	/*
	 * Test to see if player can swap a sword
	 */
	@Test
	void playerSwapsSword() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create Sword
		Sword oldSword = new Sword(dungeon, 3, 4);
		player.setSwordInHand(oldSword);
		Sword newSword = new Sword(dungeon, 8, 2);
		dungeon.addEntity(newSword);
		
		// Pick up the sword
		player.pickUpSword(newSword);;
		
		// Check
		assertTrue(player.getSwordInHand() == newSword);
		assertTrue(dungeon.getEntities().contains(oldSword));
		assertTrue(oldSword.getX() == 8);
		assertTrue(oldSword.getY() == 2);
		assertFalse(dungeon.getEntities().contains(newSword));
	}
	
	/*
	 * Check if player picks up the invincibilityPotion
	 */
	//@Test
	void playerPicksUpInvincibilityPotion() {
		// Create dungeon of size 14 x 14 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create InvincibilityPotion
		Potion invincibilityPotion = new Potion(8, 2);
		dungeon.addEntity(invincibilityPotion);
		
		// Pick up the invincibilityPotion
		player.pickUpPotion(invincibilityPotion);;
		
		// Check
		assertFalse(dungeon.getEntities().contains(invincibilityPotion));
	}
	
	/*
	 * Test to see that player cannot pick up invincibilityPotion
	 * if its not on the same square its standing on
	 */
	@Test
	void playerCantPickUpInvincibilityPotion() {
		// Create dungeon of size 17 x 12 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		// Create InvincibilityPotion
		Potion invincibilityPotion = new Potion(3, 4);
		dungeon.addEntity(invincibilityPotion);
		
		// Pick up the invincibilityPotion
		player.pickUpPotion(invincibilityPotion);;
		
		// Check
		assertTrue(dungeon.getEntities().contains(invincibilityPotion));
	}
	
	/*
	 * Check if player can light a bomb and it destroys
	 * relevant entities
	 */
	@Test
	void playerLightsBomb() {
		// Create dungeon of size 17 x 12 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 2);
		dungeon.addEntity(player);
		
		
		Bomb bomb = new Bomb(8, 2);
		dungeon.addEntity(bomb);
		
		Boulder boulder = new Boulder(dungeon, 8, 3);
		dungeon.addEntity(boulder);
		
		player.pickUpBomb(bomb);
		assertTrue(player.getBombInHand() == bomb);
		
		player.lightBomb();
		
		assertFalse(dungeon.getEntities().contains(boulder));
		assertTrue(player.getHealth() == 0);
	}
	
	/*
	 * Test player can open the door
	 */
	@Test
	void playerCanOpenDoor() {
		// Create dungeon of size 17 x 12 with AND goal condition
		Dungeon dungeon = new Dungeon(17, 12, "AND");
		// Create player
		Player player = new Player(dungeon, 8, 8);
		dungeon.addEntity(player);
		
		Key key = new Key(9, 8, 1);
		dungeon.addEntity(key);
		
		Door door = new Door(10, 8, 1);
		dungeon.addEntity(door);
		
		player.moveRight();
		player.pickUpKey(key);
		
		assertTrue(player.getKeyInHand() == key);
		
		player.moveRight();
		
		assertTrue(door.isOpen() == false);
		player.openDoor(door);
		
		assertTrue(door.isOpen() == true);
		assertTrue(player.getKeyInHand() == null);
		
	}
	
	@Test
	public void TEST_boulder() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Boulder boulder = new Boulder(dungeon, 1, 0);
		
		dungeon.addEntity(player);
		dungeon.addEntity(boulder);
		
		// moving boulder
		player.moveRight();
		assert(boulder.getX() == 2);
		assert(boulder.getY() == 0);
		assert(player.getX() == 1);
		assert(player.getY() == 0);
		dungeon.removeEntity(boulder);
		
		// moving boulder with a wall in the way
		player.moveLeft();
		Boulder boulder2 = new Boulder(dungeon, 1, 0);
		dungeon.addEntity(boulder2);
		Wall wall = new Wall(2, 0);
		dungeon.addEntity(wall);
		player.moveRight();
		assert(boulder2.getX() == 1);
		assert(boulder2.getY() == 0);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		dungeon.removeEntity(wall);
		dungeon.removeEntity(boulder2);

		// moving boulder with an enemy in the way
		player.moveLeft();
		Boulder boulder3 = new Boulder(dungeon, 1, 0);
		dungeon.addEntity(boulder3);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		Enemy enemy = new Enemy(dungeon, 2, 0);
		dungeon.addEntity(enemy);
		player.moveRight();
		assert(boulder3.getX() == 1);
		assert(boulder3.getY() == 0);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		dungeon.removeEntity(enemy);
		dungeon.removeEntity(boulder3);

		// moving boulder with a boulder in the way
		player.moveLeft();
		Boulder boulder4 = new Boulder(dungeon, 1, 0);
		dungeon.addEntity(boulder4);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		Boulder boulder4_1 = new Boulder(dungeon, 2, 0);
		dungeon.addEntity(boulder4_1);
		player.moveRight();
		assert(boulder4.getX() == 1);
		assert(boulder4.getY() == 0);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		dungeon.removeEntity(boulder4_1);
		dungeon.removeEntity(boulder4);

//		// moving boulder with a bomb in the way
//		player.moveLeft();
//		Boulder boulder5 = new Boulder(dungeon, 1, 0);
//		dungeon.addEntity(boulder5);
//		assert(player.getX() == 0);
//		assert(player.getY() == 0);
//		Bomb bomb = new Bomb(2, 0);
//		dungeon.addEntity(bomb);
//		player.moveRight();
//		assert(boulder.getX() == 1);
//		assert(boulder.getY() == 0);
//		assert(player.getX() == 0);
//		assert(player.getY() == 0);
//		dungeon.removeEntity(bomb);
//		dungeon.removeEntity(boulder5);
		
		// moving boulder with a closed door in the way
//		player.moveLeft();
//		Boulder boulder6 = new Boulder(dungeon, 1, 0);
//		dungeon.addEntity(boulder6);
//		assert(player.getX() == 0);
//		assert(player.getY() == 0);
//		Key key = new Key(9, 9, 0);
//		Door door = new Door(2, 0, 0);
//		dungeon.addEntity(key);
//		dungeon.addEntity(door);
//		player.moveRight();
//		assert(boulder.getX() == 1);
//		assert(boulder.getY() == 0);
//		assert(player.getX() == 0);
//		assert(player.getY() == 0);
//		dungeon.removeEntity(key);
//		dungeon.removeEntity(door);
//		dungeon.removeEntity(boulder6);
		
		// moving boulder with an untriggered floor switch in the way
		player.moveLeft();
		Boulder boulder7 = new Boulder(dungeon, 1, 0);
		dungeon.addEntity(boulder7);
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		Switch fs2 = new Switch(2, 0);
		dungeon.addEntity(fs2);
		fs2.setTrigger(false);
		assertFalse(fs2.isTriggered());
		player.moveRight();
		assert(boulder7.getX() == 2);
		assert(boulder7.getY() == 0);
		assert(player.getX() == 1);
		assert(player.getY() == 0);
		dungeon.removeEntity(fs2);
		dungeon.removeEntity(boulder7);
	}

	@Test
	public void TEST_EnemyMove() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 0, 10);
		
		dungeon.addEntity(player);
		dungeon.addEntity(enemy);
		
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 10);
		
		// player moves down and enemy moves automatically
		player.moveDown();
		assert(player.getX() == 0);
		assert(player.getY() == 1);
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 9);
		
		// not a valid move hence no one moves
		player.moveLeft(); 
		assert(player.getX() == 0);
		assert(player.getY() == 1);
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 9);
		
		player.moveRight();
		assert(player.getX() == 1);
		assert(player.getY() == 1);
		assert(enemy.getX() == 1);
		assert(enemy.getY() == 9);
		
		player.moveUp();
		assert(player.getX() == 1);
		assert(player.getY() == 0);
		assert(enemy.getX() == 1);
		assert(enemy.getY() == 8);
		
		player.moveDown();
		assert(player.getX() == 1);
		assert(player.getY() == 1);
		assert(enemy.getX() == 1);
		assert(enemy.getY() == 7);
		
	}
	
	@Test
	public void TEST_EnemyKill() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 0, 2);
		
		dungeon.addEntity(player);
		dungeon.addEntity(enemy);
		
		// player and enemy on the same square
		player.moveDown();
		assert(player.getX() == 0);
		assert(player.getY() == 1);
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 1);
		
		// player killed
		assertTrue(player.killed());
	}
	
	@Test
	public void TEST_Exit() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Exit exit = new Exit(0, 1);
		
		dungeon.addEntity(player);
		dungeon.addEntity(exit);
		
		// exiting without interacting with exit entity
		player.exit(exit);
		assertTrue(exit.getMessage().equals("Stage not completed."));
		assertFalse(exit.isCompletedStage());
		// checking positions
		assert(player.getX() == 0);
		assert(player.getY() == 0);
		assert(exit.getX() == 0);
		assert(exit.getY() == 1);
		
		// exiting with exit entity and not completing goals
		player.moveDown();
		player.exit(exit);
		assertTrue(exit.getMessage().equals("Stage not completed."));
		assertFalse(exit.isCompletedStage());
		// checking positions
		assert(player.getX() == 0);
		assert(player.getY() == 1);
		assert(exit.getX() == 0);
		assert(exit.getY() == 1);
		
		// completing goals
		player.setObjectivesCompleted(true);
		player.exit(exit);
		assertTrue(exit.getMessage().equals("THE END!"));
		assertTrue(exit.isCompletedStage());
		
	}

	@Test
	public void TEST_FloorSwitch() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 3, 1);

		Switch fs = new Switch(3, 3);
		Boulder boulder = new Boulder(dungeon, 3, 2);
		dungeon.addEntity(boulder);
		dungeon.addEntity(fs);
		
		// moving boulder onto switch
		player.moveDown();
		assert(boulder.getX() == 3);
		assert(boulder.getY() == 3);
		assert(fs.getX() == 3);
		assert(fs.getY() == 3);
		assert(player.getX() == 3);
		assert(player.getY() == 2);
		assertTrue(fs.isTriggered());
		
		
		// moving boulder off switch
		player.moveDown();
		assert(boulder.getX() == 3);
		assert(boulder.getY() == 4);
		assert(fs.getX() == 3);
		assert(fs.getY() == 3);
		assert(player.getX() == 3);
		assert(player.getY() == 3);
		assertFalse(fs.isTriggered());
		
	}

	@Test
	public void TEST_Potion() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Potion potion = new Potion(0, 1);
		Enemy enemy = new Enemy(dungeon, 0, 5);
		
		dungeon.addEntity(player);
		dungeon.addEntity(potion);
		dungeon.addEntity(enemy);
		
		// checking if potion has not been picked up
		assert(player.getHealth() == 1);
		player.pickUpPotion(potion);
		assertFalse(player.getHasPotion());
		
		// moving to pick up potion
		player.moveDown();
		player.pickUpPotion(potion);
		assertTrue(player.getHasPotion());
		
		// enemy and player position
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 4);
		assert(player.getX() == 0);
		assert(player.getY() == 1);
		
		// enemy runs away with potion active
		player.moveDown();
		assert(enemy.getX() == 0);
		assert(enemy.getY() == 5);
		assert(player.getX() == 0);
		assert(player.getY() == 2);
		
	}

	@Test
	public void TEST_sword() {
		Dungeon dungeon = new Dungeon(10, 10, "OR");
		Player player = new Player(dungeon, 0, 0);
		Sword sword = new Sword(dungeon, 1, 0);
		
		dungeon.addEntity(player);
		assertTrue(dungeon.getEntities().contains(player));
		dungeon.addEntity(sword);
		assertTrue(dungeon.getEntities().contains(sword));
		
		// trying to pick up a sword at the wrong spot
		player.pickUpSword(sword);
		assert(player.getSwordInHand() == null);
		
		// checking if player has moved correctly
		player.moveRight();
		assert(player.getX() == 1);
		assert(player.getY() == 0);
		
		// trying to pick up sword at right sport
		player.pickUpSword(sword);
		assertTrue(player.getSwordInHand().equals(sword));
		
		// testing stats of sword
		assert(sword.getHealthSword() == 5);
		player.useSword(sword);
		assert(sword.getHealthSword() == 4);
		
		// using sword against an enemy
		Enemy enemy = new Enemy(dungeon, 2, 0);
		dungeon.addEntity(enemy);
		assertTrue(dungeon.getEntities().contains(enemy));
		player.useSword(sword);
		assert(sword.getHealthSword() == 3);
		assertFalse(dungeon.getEntities().contains(enemy));
		
		// testing when sword is used up
		player.useSword(sword);
		assert(sword.getHealthSword() == 2);
		player.useSword(sword);
		assert(sword.getHealthSword() == 1);
		player.useSword(sword);
		assert(sword.getHealthSword() == 0);
		assertFalse(dungeon.getEntities().contains(sword));
			
		
	}

	
}
