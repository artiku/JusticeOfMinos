package jom.game.res.scripts;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Anna on 20.03.2017.
 */
public class Char extends Sprite {


    /**
     * Path to the image of this specific Hero sprite.
     */
    private String imagePath = "jom/game/jom.game.res/sprites/charTest.png";

    /**
     * Collision rectangle of the hero.
     */
    private Rectangle r;

    /**
     * Collision box X coordinate, relatively to Hero Sprite
     */
    private int collisionBoxX = 22;


    /**
     * Collision box Y coordinate, relatively to Hero Sprite
     */
    private int collisionBoxY = 46;

    /**
     * Hero collision box Width.
     */
    private int collisionBoxWidth = 22;

    /**
     * Hero collision box Height.
     */
    private int collisionBoxHeight = 10;

    /**
     * Constructor.
     * @param x x World coordinate
     * @param y y World coordinate
     */
    public Char( int x, int y) {
        /* Defining initial world coordinates for the Hero */
        super.updatePos(x, y);
        /* Setting image for the sprite */
        this.setImage(imagePath);
        /* Initiating collision rectangle for the Hero Sprite and managing it's initial coordinates */
        r = new Rectangle( x + collisionBoxX, y + collisionBoxY,
                collisionBoxWidth, collisionBoxHeight);
        r.setStroke(Color.YELLOW);
    }

    /**
     * @return Collision box X coordinate.
     */
    public int getCollisionBoxX() {
        return collisionBoxX;
    }

    /**
     * @return Collision box Y coordinate.
     */
    public int getCollisionBoxY() {
        return collisionBoxY;
    }

    /**
     * @return Collision box Width
     */
    public int getCollisionBoxWidth() {
        return collisionBoxWidth;
    }

    /**
     * @return Collision box Height
     */
    public int getCollisionBoxHeight() {
        return collisionBoxHeight;
    }

    /**
     * @return Rectangle in order to check collision Or add to the group.
     */
    @Override
    public Rectangle getColBox() {
        return r;
    }

    /**
     * Render collision box to make it visible.
     * FOR THE TEST PURPOSES.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void renderBox(int x, int y) {
        r.setX(x + collisionBoxX);
        r.setY(y  + collisionBoxY);
    }
}
