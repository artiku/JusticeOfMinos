package com.game.justiceofminos;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static com.game.justiceofminos.JusticeofMinos.SCREEN_WIDTH;

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
     * Player health.
     */
    int playerHealth = 2 + 1;

    /**
     * Hero sprite animation.
     */
    HeroAnimation animation;

    /**
     * Heart group.
     */
    Group healthGroup = new Group();
//
//    /**
//     * Velocity.
//     */
//    Point2D playerVelocity = new Point2D(0, 0);

    /**
     * Constructor.
     */
    Character() {
        final int damageAnimCount = 5;
        heroView.setFitHeight(HERO_SIZE);
        heroView.setFitWidth(HERO_SIZE);
        heroView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new HeroAnimation();

        animation.damageAcquired.setCycleCount(damageAnimCount);
        animation.damageAcquired.setOnFinished(event -> {
            heroView.setVisible(true);
//            playerHealth--;
//            setHealthView();
        });
        getChildren().add(this.heroView);
    }

    /**
     * Applying damage.
     */
    void applyDamage() {
        playerHealth--;
        setHealthView();
    }

    /**
     * Display players health.
     */
    void setHealthView() {
        final int heartSize = 60;
        final double heartScale = 1.5;
        healthGroup.getChildren().removeAll();
        healthGroup.setTranslateX(SCREEN_WIDTH - playerHealth * heartSize);
        for (int i = 0; i < playerHealth; i++) {
            ImageView heart = new ImageView(Assets.HEART);
            heart.setX(i * heartSize);
            heart.setScaleX(heartScale);
            heart.setScaleY(heartScale);
            heart.setFitHeight(heartSize);
            heart.setPreserveRatio(true);
            healthGroup.getChildren().add(heart);
        }
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

        /**
         * Set player visible for blinking.
         */
        final Timeline t2 = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            heroView.setVisible(true);
        }));

        /**
         * Set player invisible for blinking.
         */
        final Timeline t1 = new Timeline(
                new KeyFrame(Duration.millis(400), e -> {
                    heroView.setVisible(false);
                }));

        /**
         * Played when player is damaged.
         */
        final SequentialTransition damageAcquired = new SequentialTransition(t1, t2);

    }

}
