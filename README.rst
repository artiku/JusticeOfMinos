General description: 
====================

We introduce you to our game *“Labyrinth of Minos”*. The main goal of a player is to find all keys that will open the door (exit from the labyrinth). Labyrinth has one and only one exit and a lot of dangers, including one of the most famous creature of the ancient Greek mythology - the Minotaur.   

Description of the game mechanics
----------------------------------

**Map**

-	Top-down perspective
-	Pre-generated map (Will try to implement Random seed level generation)
-	Divided into cells

**NPC and Hero in general**

-	Sprites that change depending on a movement direction.
-	Can move Up, Down, Left, Right.
-	NPSs have limited line of sight: they can see only a restricted part of labyrinth. It depends on:
    -	if there is an obstacle, NPSs cannot see what is located behind it
    -	if there is an open space around, NPSs can see only part of it (limited by radius)
-	NPSs can move by their own way (randomly wandering). 
-	NPSs have defined movement speed.
-	NPS cannot fight with NPS. 
-	NPSs can attack player (Minotaur kills instantly).
-	NPSs have amount of health (except the Minotaur)
**Hero**

-	Sprites that change depending on a movement direction.
-	Can move Up, Down, Left, Right.
-	Same line of sight.
-	Health. Player has health points (HP) that can be reduced by enemies or increased by potion. 
-	Movement speed. Player has a certain movement speed, which could be changed with potion for a certain time.
-	Player can use equipment with his needs.
-	Player can interact through the interaction key with: treasure chests, levers, enemies, doors.

**Equipment**

-	Treasure chests 
    To open the chest, player have to come close to one cell and press the “interaction key”;
Every of four (or more) chests contain treasure(s): 

-	Shield – a guaranteed protection from the enemies (and may be the dragon). 
-	Health potion – regenerate all HP
-	Sword – gives an ability to fight with enemies 
-	Torch – increases player’s range of view by some radius, can be placed on the wall if there is a stand, in order to lighten area in the distance.

-	Keys
    To open the door (main goal), player have to find the keys that are found in the labyrinth. They can be found in the special chests. 

**Interaction with interior**

-	Levers
-	Traps 
-	Switches
-	Chests

**Additional features**

Score is the maze escape time.

**Advanced additional features**

-	Create various difficulty levels and include them into score calculations.
-	Create score Data Base synchronized with web page.
-	Create Level Random Generation with Seed.
-	Port game to Android.
-	Create Achievement system.

Screen views
------------

**Main menu**

Buttons: “New game”, “High scores” (Local; Global (on the webpage)), “Options”, “How to play”.

**New game**

Before actual game starts some introduction (prehistory and How to play) into gameplay appears. (Some basic character creation could be added.)

**High scores**

Local scores are scores saved on the current device. Global scores will lead to the webpage that synchronize scores from each player who wished to share his/her score.

**Options**

Would include some options to set:

-	Sound
-	Brightness
-	Key bindings

**Game View**

Camera is focused on player. Zoomed out during level investigation.
The whole map is unrevealed on the start. While player discovers every new cell, player camera view is re-zoomed. It could be zoomed out until some limit. As well it is specially zoomed for lightened areas in the distance from the player.
At the end of the game player’s escape time is shown up. (Player is asked to share his score on the web)

Schedule
--------

-	5. Week: UI mockup, sprites;
-	6. Week: Map creation, map movement;
-	7. Week: Interactive objects, inventory, Line of sight;
-	8. Week: Traps, enemies and their AI;
-	9. Week: Animations, better models(sprites);
-	10. Week: UI (trying any of advanced features);
-	11. Week: Score board, web implementation, options menu;
-	12. Week: bugfix, advanced features;

Points desired
--------------

.. Over9000

8 (to 10 if advanced are realized) points
