package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");
        JSONObject jsonGoal = json.getJSONObject("goal-condition");
        String goal = jsonGoal.getString("goal"); // "AND" or "OR" or a goal (if there is only one)
        ObjectiveNode objective;
        
//        if (goal.equals("AND")
//        		|| goal.equals("OR")) {
//        	
//        } else {
        	objective = new ObjectiveNode(goal);
//        }
        

        Dungeon dungeon = new Dungeon(width, height, objective);        

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
     
        return dungeon;
    }

    
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
        	Exit exit = new Exit(x, y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "key":
        	int id_key = json.getInt("id");
        	Key key = new Key(x, y, id_key);
        	onLoad(key);
        	entity = key;
        	break;
        case "door":
        	int id_door = json.getInt("id");
        	Door door = new Door( x, y, id_door);
        	onLoad(door);
        	entity = door;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(dungeon, x, y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	Switch floorSwitch = new Switch(dungeon, x, y);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	break;
        case "bomb":
        	Bomb bomb = new Bomb(dungeon, x, y);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon, x, y);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "sword":
        	Sword sword = new Sword(dungeon, x, y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "potion":
        	Potion potion = new Potion(dungeon, x, y);
        	onLoad(potion);
        	entity = potion;
        	break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Switch floorSwitch);
    
    public abstract void onLoad(Bomb bomb);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Sword Sword);
    
    public abstract void onLoad(Potion potion);


}
