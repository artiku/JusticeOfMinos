package com.game.justiceofminos;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Desc.
 */
public class Character extends Pane {

    /**
     * Image of the player.
     */
    Image heroImage = new Image(getClass().getClassLoader().getResource("Knight.png").toExternalForm());

    /**
     * Hero image view.
     */
    ImageView heroView = new ImageView(heroImage);

    /**
     * Count of sprites for one way of the walk animation.
     */
    private int count = 3;

    /**
     * Number of colums for the animation.
     */
    private int columns = 3;

    /**
     * Offset X for the primary viewport.
     */
    private int offsetX = 32;

    /**
     * Offset Y.
     */
    private int offsetY = 64;

    /**
     * Width.
     */
    private int width = 32;

    /**
     * Height.
     */
    private int height = 32;

    /**
     *  Walk Animation duration.
     */
    private Duration animDuration = Duration.millis(400);

    /**
     * Hero sprite animation.
     */
    HeroAnimation animation;

    /**
     * Velocity.
     */
    Point2D playerVelocity = new Point2D(0, 0);

    /**
     * Constructor.
     */
    Character() {
        heroView.setFitHeight(32);
        heroView.setFitWidth(32);
        heroView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        //TODO More animations. May be inner animation class, to call animation like: animation.right
//        animation = new SpriteAnimation(this.heroView, animDuration, count, columns, offsetX, offsetY, width, height);
        animation = new HeroAnimation();
        getChildren().add(this.heroView);
    }

    /**
     * Move player X.
     * @param value Value to move.
     */
    void moveX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node wall : JusticeofMinos.wallStreet) {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + JusticeofMinos.BLOCK_SIZE == wall.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            //TODO DEBUGGING
                            System.out.println("COLLIDES AT RIGHT");
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == JusticeofMinos.BLOCK_SIZE + wall.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            //TODO DEBUGGING
                            System.out.println("COLLIDES AT LEFT");
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    /**
     * Move player Y.
     * @param value Value to move.
     */
    void moveY(int value) {
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node wall : JusticeofMinos.wallStreet) {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getTranslateY() + JusticeofMinos.BLOCK_SIZE == wall.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            //TODO DEBUGGING
                            System.out.println("COLLIDES AT BOTTOM");
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == JusticeofMinos.BLOCK_SIZE + wall.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() + 1);
                            //TODO DEBUGGING
                            System.out.println("COLLIDES AT TOP");
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    /**
     * Inned class for animations.
     */
    class HeroAnimation {

        /**
         * Right movement.
         */
        final SpriteAnimation right
                = new SpriteAnimation(Character.this.heroView, Character.this.animDuration, count, columns,
                0, 0,
                Character.this.width, Character.this.height);

        /**
         * Left movement.
         */
        final SpriteAnimation left
                = new SpriteAnimation(Character.this.heroView, Character.this.animDuration, count, columns,
                0, 32,
                Character.this.width, Character.this.height);

        /**
         * Down.
         */
        final SpriteAnimation down
                = new SpriteAnimation(Character.this.heroView, Character.this.animDuration, count, columns,
                0, 64,
                Character.this.width, Character.this.height);

        /**
         * Up.
         */
        final SpriteAnimation up
                = new SpriteAnimation(Character.this.heroView, Character.this.animDuration, count, columns,
                0, 96,
                Character.this.width, Character.this.height);
    }

}