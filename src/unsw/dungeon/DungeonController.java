package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        
        // adding an area to put text
        Image white = new Image("/offwhite.png");
        for (int i = 0; i < 8; i++) {
        	for (int j = 0; j < dungeon.getHeight(); j++) {
        		squares.add(new ImageView(white), dungeon.getWidth() + i, j);
        	}
        }
        
        // adding a column of wall so player cant go outside
        Image wall = new Image("/brick_brown_0.png");
        for (int i = 0; i < dungeon.getHeight(); i++) {
        	squares.add(new ImageView(wall), dungeon.getWidth(), i);
        }
        
        // adding text fields
        // goal
        Button goalButton = new Button("GOAL");
        squares.add(goalButton, dungeon.getWidth() + 6, 1);
        goalButton.setOnAction(e -> {
        	Alert goalAlert = new Alert(AlertType.INFORMATION);
        	goalAlert.setHeaderText("The goals of the dungeon");
        	if (dungeon.getGoalNode().getGoalName().equals("exit")) {
        		goalAlert.setContentText("Goal of this dungeon is to reach the exit.");
        	} else if (dungeon.getGoalNode().getGoalName().equals("boulder")) {
        		goalAlert.setContentText("Goal of this dungeon is to trigger all floor switches."
        				+ "This can be done by pushing a boulder onto a floor switch.");
        	} else if (dungeon.getGoalNode().getGoalName().equals("treasure")) {
        		goalAlert.setContentText("Goal of this dungeon is to collect all treasure.");
        	} else if (dungeon.getGoalNode().getGoalName().equals("enemies")) {
        		goalAlert.setContentText("Goal of this dungeon is to kill all enemies.");
        	}
        	goalAlert.show();
        });
        
        //restart
        Button restartButton = new Button("RESTART");
        squares.add(restartButton, dungeon.getWidth() + 6, 2);
        restartButton.setOnAction(e -> {
        	
        	DungeonApplication application = new DungeonApplication();
        	try {
				application.start(new Stage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	
        });
        
        //instructions
        Button controlButton = new Button("CONTROLS");
        squares.add(controlButton, dungeon.getWidth() + 6, 3);
        controlButton.setOnAction(e -> {
        	Alert controlAlert = new Alert(AlertType.INFORMATION);
        	controlAlert.setHeaderText("Controls");
        	controlAlert.setContentText("Press SPACE to:\n"
        			+ "-> Light a bomb\n"
        			+ "-> Kill an enemy\n"
        			+ "-> Open a door (if you have key)\n"
        			+ "Use the Arrow keys or WASD to move.");
        	controlAlert.show();
        });
        
        // treasure
        Text treasureCountText = new Text("Treasure Count: ");
        Text treasureCount = new Text();
        squares.add(treasureCountText, dungeon.getWidth() + 2, 1);
        squares.add(treasureCount, dungeon.getWidth() + 3, 1);
        treasureCount.textProperty().bindBidirectional(player.getTreasureWallet(), new NumberStringConverter());

        //switch
        Text switchText = new Text("Switch Count: ");
        Text switchCounter = new Text();
        squares.add(switchText, dungeon.getWidth() + 2, 3);
        squares.add(switchCounter, dungeon.getWidth() + 3, 3);
        switchCounter.textProperty().bindBidirectional(dungeon.getTriggeredSwitchCount(), new NumberStringConverter());
        
        //sword
        Text hasSwordText = new Text("Has Sword: ");
        Text hasSword = new Text();
        squares.add(hasSwordText, dungeon.getWidth() + 2, 4);
        squares.add(hasSword, dungeon.getWidth() + 3, 4);
        hasSword.textProperty().bindBidirectional(player.getHasSword(), new BooleanStringConverter());

        
        //key
        Text hasKeyText = new Text("Has Key: ");
        Text hasKey = new Text();
        squares.add(hasKeyText, dungeon.getWidth() + 2, 5);
        squares.add(hasKey, dungeon.getWidth() + 3, 5);
        hasKey.textProperty().bindBidirectional(player.getHasKey(), new BooleanStringConverter());
        

        //potion
        Text hasPotionText = new Text("Has Potion: ");
        Text hasPotion = new Text();
        squares.add(hasPotionText, dungeon.getWidth() + 2, 2);
        squares.add(hasPotion, dungeon.getWidth() + 3, 2);
        hasPotion.textProperty().bindBidirectional(player.getHasPotion(), new BooleanStringConverter());
        
       
        //bomb
        Text hasBombText = new Text("Has Bomb: ");
        Text hasBomb = new Text();
        squares.add(hasBombText, dungeon.getWidth() + 2, 6);
        squares.add(hasBomb, dungeon.getWidth() + 3, 6);
        hasBomb.textProperty().bindBidirectional(player.getHasBomb(), new BooleanStringConverter());
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case W:
            player.moveUp();
            break;
        case S:
            player.moveDown();
            break;
        case A:
            player.moveLeft();
            break;
        case D:
            player.moveRight();
            break;
        case SPACE:
        	List<Entity> list = dungeon.getSurroundingEntities(player);
        	for (Entity entity : list) {
        		if (entity instanceof Enemy
        				&& player.getSwordInHand() != null) {
        			player.useSword(player.getSwordInHand());
        			
        		} else if (entity instanceof Door) {
        			player.openDoor((Door) entity);
        			if (((Door) entity).isOpen().get()) {        				
        				squares.add(new ImageView("/dirt_0_new.png"), entity.getX(), entity.getY());
        				squares.add(new ImageView("/open_door.png"), entity.getX(), entity.getY());
        			}
        			
        		} else if (player.getBombInHand() != null) {
        			player.lightBomb();
        			int playerX = player.getX();
        			int playerY = player.getY();
        			squares.add(new ImageView("/bomb_lit_1.png"), playerX, playerY);
        			Timeline explode2 = new Timeline(
        					new KeyFrame(Duration.seconds(1), e -> {
        				squares.add(new ImageView("/dirt_0_new.png"), playerX, playerY);
                    	squares.add(new ImageView("/bomb_lit_2.png"), playerX, playerY);
        			}));
        			Timeline explode3 = new Timeline(
        					new KeyFrame(Duration.seconds(1), e -> {
        				squares.add(new ImageView("/dirt_0_new.png"), playerX, playerY);
                    	squares.add(new ImageView("/bomb_lit_3.png"), playerX, playerY);
        			}));
        			Timeline explode4 = new Timeline(
        					new KeyFrame(Duration.seconds(1), e -> {
        				squares.add(new ImageView("/dirt_0_new.png"), playerX, playerY);
                    	squares.add(new ImageView("/bomb_lit_4.png"), playerX, playerY);
        			}));
        			Timeline reset = new Timeline(
        					new KeyFrame(Duration.seconds(1), e -> {
        				squares.add(new ImageView("/dirt_0_new.png"), playerX, playerY);
        				player.getBombInHand().bombKill();
        				dungeon.removeEntity(player.getBombInHand());
        				player.getBombInHand().isInDungeon().set(false);
        			}));
        			player.getBombInHand().explodeBomb(explode2, explode3, explode4, reset);
        		}
        	}
        	break;
        default:
            break;
        }
    }


}

