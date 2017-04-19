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
    private Image blocksImage = new Image(Assets.WORLD_SPRITES);

    /**
     * Block Image View.
     */
    private ImageView block;

    /**
     * Size in the file.
     */
    private static final int FILE_SIZE = 32;

    public enum BlockType {
        /**
         * Type of blocks.
         */
        WALL, FLOOR, GATE, CHEST
    }

    /**
     * Coonstructor.
     * @param blockType Type of the block from the enum.
     * @param x x coord
     * @param y y coord
     */
    public Block(BlockType blockType, int x, int y) {
        block = new ImageView(blocksImage);
        block.setFitWidth(JusticeofMinos.BLOCK_SIZE);
        block.setFitHeight(JusticeofMinos.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);

        switch (blockType) {
            case WALL:
                block.setViewport(new Rectangle2D(FILE_SIZE * 2,
                        FILE_SIZE, FILE_SIZE, FILE_SIZE));
                JusticeofMinos.wallStreet.add(this);
                break;
            case FLOOR:
                block.setViewport(new Rectangle2D(FILE_SIZE * 2,
                        0, FILE_SIZE, FILE_SIZE));
                break;
            case GATE:
                block.setViewport(new Rectangle2D(FILE_SIZE * 2,
                        FILE_SIZE * 2, FILE_SIZE, FILE_SIZE));
                break;
            case CHEST:
                block.setViewport(new Rectangle2D(0, 0,
                        FILE_SIZE, FILE_SIZE));
                break;
        }
        getChildren().add(block);
        JusticeofMinos.root.getChildren().add(this);
    }
}
