package jom.game.res.scripts;

/**
 * Created by Anna on 20.03.2017.
 */
public class Floor extends Sprite {


    /**
     * Path to the image of this specific sprite.
     */
    private String imagePath = "jom/game/jom.game.res/sprites/floorTest1.png";

    /**
     * Constructor.
     * @param x x World coordinate
     * @param y y World coordinate
     */
    public Floor( int x, int y) {
        /* Defining initial world coordinates for the Floor Sprite */
        super.updatePos(x, y);
        /* Setting image for the sprite */
        this.setImage(imagePath);
    }
}
