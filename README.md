# 2D DUNGEON GAME
This application is a dungeon-style puzzle game, written in Java 8.
The dungeon is created by reading in a JSON file which places
blocks in a grid, which is then rendered in the game itself.

A simple form of the application is a maze, where the player must find their
way from the starting point to the exit.

![Maze][maze]

Other goals could include pushing boulders onto floor switches

![Boulders][boulders]

or enemies that need to be fought with weapons, potions, or treasure.

![Advanced dungeon][advanced]


To create your own dungeon, place your JSON file in `dungeons` directory.
Example dungeons are included.

The dungeon files have the following format:

> { "width": *width in squares*, "height": *height in squares*, "entities": *list of entities*, "goal-condition": *goal condition* }

Each entity in the list of entities is structured as:

> { "type": *type*, "x": *x-position*, "y": *y-position* }

where *type* is one of

> ["player", "wall", "exit", "treasure", "door", "key", "boulder", "switch", "bomb", "enemy", "sword", "invincibility"]

The `wall` and `key` entities include an additional field `id` containing a number. Keys open the door with the same `id` (e.g. the key with `id` 0 opens the door with `id` 0).

The goal condition is a JSON object representing the logical statement that defines the goal. Basic goals are:

> { "goal": *goal* }

where *goal* is one of

> ["exit", "enemies", "boulders", "treasure"]

In the case of a more complex goal, *goal* is the logical operator and the additional *subgoals* field is a JSON array containing subgoals, which themselves are goal conditions. For example,

```JSON
{ "goal": "AND", "subgoals":
  [ { "goal": "exit" },
    { "goal": "OR", "subgoals":
      [ {"goal": "enemies" },
        {"goal": "treasure" }
      ]
    }
  ]
}
```

Note that the same basic goal *can* appear more than once in a statement.

You can extend this format to include additional information if you wish, but your application should still work with files in the original format.
