package com.game.justiceofminos;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Desc.
 */
class Block extends Pane {

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
     * If trap deal damage.
     */
    boolean active;

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
        WALL, FLOOR, GATE, CHEST, T_WALL, CORRIDOR_WALL_HORIZONTAL, CORRIDOR_WALL_VERTICAL, TRAP_SYNC
    }

    /**
     * Coonstructor.
     * @param blockType Type of the block from the enum.
     * @param x x coord
     * @param y y coord
     */
    Block(BlockType blockType, int x, int y) {

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
            case TRAP_SYNC:
                block.setViewport(new Rectangle2D(0, FILE_SIZE + 1,
                        FILE_SIZE, FILE_SIZE - 2));
                JusticeofMinos.itIsATrvp.add(this);
                assignTrapAnimation();
            default:
                break;
        }
        getChildren().add(block);
        JusticeofMinos.root.getChildren().add(this);
    }

    /**
     * Animation for the trap.
     */
    private void assignTrapAnimation() {
        final SpriteAnimation activeAnim
                = new SpriteAnimation(block, Duration.millis(750), 2, 2,
                0, FILE_SIZE,
                FILE_SIZE, FILE_SIZE - 1);

        final Timeline trapAnim = new Timeline(new KeyFrame(Duration.millis(1500), event -> {
            activeAnim.play();
            active = true;
        }));
        activeAnim.setOnFinished(event -> {
            final Timeline pauseTimeline = new Timeline(new KeyFrame(Duration.ZERO, event1 -> {
                active = false;
                block.setViewport(new Rectangle2D(FILE_SIZE * 2,
                        0, FILE_SIZE, FILE_SIZE - 1));
            }),
            new KeyFrame(Duration.millis(1500)));
            pauseTimeline.play();
        });
        trapAnim.setCycleCount(Animation.INDEFINITE);
        trapAnim.play();

    }
}
