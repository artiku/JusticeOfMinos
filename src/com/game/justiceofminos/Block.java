package com.game.justiceofminos;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Desc.
 */
public class Block extends Pane {

    /**
     * Blocks spritesheet.
     */
    private Image blocksImage = new Image(Assets.WORLD_SPRITES);

    /**
     * Block Image View.
     */
    ImageView block;

    /**
     * If chest is collected.
     */
    boolean collected;

    /**
     * Size in the file.
     */
    static final int FILE_SIZE = 32;

    /**
     * Block types.
     */
    public enum BlockType {
        /**
         * Type of blocks.
         */
        WALL, FLOOR, GATE, CHEST, T_WALL, CORRIDOR_WALL_HORIZONTAL, CORRIDOR_WALL_VERTICAL
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
                        FILE_SIZE, FILE_SIZE, FILE_SIZE - 1));
                JusticeofMinos.wallStreet.add(this);
                break;
            case FLOOR:
                block.setViewport(new Rectangle2D(FILE_SIZE * 2 + 1,
                        0, FILE_SIZE - 1, FILE_SIZE - 1));
                break;
            case GATE:
                block.setViewport(new Rectangle2D(FILE_SIZE * 2,
                        FILE_SIZE * 2, FILE_SIZE, FILE_SIZE));
                JusticeofMinos.wallStreet.add(this);
                break;
            case CHEST:
                block.setViewport(new Rectangle2D(0, 0,
                        FILE_SIZE, FILE_SIZE));
                JusticeofMinos.chestArray.add(this);
                collected = false;
                break;
            case T_WALL:
                block.setViewport(new Rectangle2D(0, FILE_SIZE * 2,
                        FILE_SIZE, FILE_SIZE - 1));
                JusticeofMinos.wallStreet.add(this);
                break;
            case CORRIDOR_WALL_HORIZONTAL:
                block.setViewport(new Rectangle2D(FILE_SIZE + 1, 0,
                        FILE_SIZE - 2, FILE_SIZE - 1));
                JusticeofMinos.wallStreet.add(this);
                break;
            case CORRIDOR_WALL_VERTICAL:
                block.setViewport(new Rectangle2D(FILE_SIZE, FILE_SIZE * 2 + 1,
                        FILE_SIZE, FILE_SIZE - 2));
                JusticeofMinos.wallStreet.add(this);
                break;
            default:
                break;
        }
        getChildren().add(block);
        JusticeofMinos.root.getChildren().add(this);
    }
}
