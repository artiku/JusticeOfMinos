package com.game.justiceofminos;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Sprite animation inner class.
 */
public class SpriteAnimation extends Transition {

    /**
     * Image view where animation would be shown.
     */
    private final ImageView imageView;

    /**
     * Count of the frames.
     */
    private final int count;

    /**
     * Columns for the frames in one row in the image file.
     */
    private final int columns;

    /**
     * X offset where image left upper corner starts.
     */
    private final int offsetX;

    /**
     * Y offset where image left upper corner starts.
     */
    private final int offsetY;

//    /**
//     * Square size of one frame.
//     */
//    private final int size;

    /**
     * Width.
     */
    private final int width;

    /**
     * Height.
     */
    private final int height;

    /**
     * Last index of the image.
     */
    private int lastIndex;

    /**
     * Sprite animation constructor.
     * @param imageView Image view where animation would be shown.
     * @param duration Duration of the whole animation.
     * @param count Count of the frames.
     * @param columns Columns for the frames in one row in the image file.
     * @param offsetX X offset where image left upper corner starts.
     * @param offsetY Y offset
     * @param height Height.
     * @param width Width.
     */
    SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count,   int columns,
            int offsetX, int offsetY,
            int width, int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
//        this.size     = size;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    /**
     * Overriden interpolate method.
     * @param k 0 to 1 value
     */
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
