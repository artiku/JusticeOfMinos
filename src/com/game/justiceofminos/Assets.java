package com.game.justiceofminos;

import javafx.geometry.Rectangle2D;

/**
 * Created by Anna on 19.04.2017.
 */
public class Assets {

    /**
     * Blocks sprite sheet.
     */
    static final String WORLD_SPRITES = "file:assets/WorldSprites.png";

    /**
     * Hero(player) sprites.
     */
    static final String HERO_SPRITE = "file:assets/Knight.png";

//    /**
//     * Wall image path.
//     */
//    static final Rectangle2D WALL_VIEWPORT = new Rectangle2D(JusticeofMinos.BLOCK_SIZE * 2,
//            0, JusticeofMinos.BLOCK_SIZE, JusticeofMinos.BLOCK_SIZE);

    /**
     * Floor path.
     */
    static final String FLOOR = Assets.class.getResource("/iti0011_gui/assets/floorTest.png").toExternalForm();
}
