package com.game.justiceofminos;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Desc.
 */
public class Block extends Pane{

    /**
     * Blocks spritesheet.
     */
    Image blocksImage = new Image(getClass().getResource("WorldSprites.png").toExternalForm());

    /**
     * Block Image View.
     */
    ImageView block;

    public enum BlockType {
        WALL, FLOOR, GATE, CHEST
    }

    public Block(BlockType blockType, int x, int y) {
        block = new ImageView(blocksImage);
        block.setFitWidth(JusticeofMinos.BLOCK_SIZE);
        block.setFitHeight(JusticeofMinos.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);

        switch (blockType) {
            case WALL:
                block.setViewport(new Rectangle2D(64,64,32,32));
                JusticeofMinos.wallStreet.add(this);
                break;
            case FLOOR:
                block.setViewport(new Rectangle2D(64, 0, 32, 32));
                break;
            case GATE:
                block.setViewport(new Rectangle2D(64, 64, 32, 32));
                break;
            case CHEST:
                block.setViewport(new Rectangle2D(0, 0, 32, 32));
                break;
        }
        getChildren().add(block);
        JusticeofMinos.root.getChildren().add(this);
    }
}