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
class Character extends Pane {

    /**
     * Hero size.
     */
    static final int HERO_SIZE = JusticeofMinos.BLOCK_SIZE;

    /**
     * Image of the player.
     */
    Image heroImage = new Image(Assets.HERO_SPRITE);

    /**
     * Hero image view.
     */
    ImageView heroView = new ImageView(heroImage);

    /**
     * Count of sprites for one way of the walk animation.
     */
    private final int count = 3;

    /**
     * Number of colums for the animation.
     */
    private final int columns = 3;

    /**
     * Offset X for the primary viewport.
     */
    private final int offsetX = 32;

    /**
     * Offset Y.
     */
    private final int offsetY = 64;

    /**
     * Width.
     */
    private final int width = 32;

    /**
     * Height.
     */
    private final int height = 32;

    /**
     *  Walk Animation duration.
     */
    private final Duration animDuration = Duration.millis(400);

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
//        setWidth(20);
//        setHeight(20);
        heroView.setFitHeight(HERO_SIZE);
        heroView.setFitWidth(HERO_SIZE);
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
        final int offsetToCenter = HERO_SIZE / 4;

        Rectangle2D futurePosRect = new Rectangle2D(this.getTranslateX() + value + offsetToCenter,
                this.getTranslateY() + offsetToCenter * 2,
                offsetToCenter * 2 + 2, offsetToCenter * 2);
        for (Node wall : JusticeofMinos.wallStreet) {
            if (futurePosRect.intersects(wall.getTranslateX(), wall.getTranslateY(),
                    JusticeofMinos.BLOCK_SIZE, JusticeofMinos.BLOCK_SIZE)) {
                return;
            }
        }

        this.setTranslateX(this.getTranslateX() + value);
    }

    /**
     * Move player Y.
     * @param value Value to move.
     */
    void moveY(int value) {
        final int offsetToCenter = HERO_SIZE / 4;

        Rectangle2D futurePosRect = new Rectangle2D(this.getTranslateX() + offsetToCenter,
                this.getTranslateY() + value + offsetToCenter * 2,
                offsetToCenter * 2 + 2, offsetToCenter * 2);
        for (Node wall : JusticeofMinos.wallStreet) {
            if (futurePosRect.intersects(wall.getTranslateX(), wall.getTranslateY(),
                    JusticeofMinos.BLOCK_SIZE, JusticeofMinos.BLOCK_SIZE)) {
                return;
            }
        }

        this.setTranslateY(this.getTranslateY() + value);
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
