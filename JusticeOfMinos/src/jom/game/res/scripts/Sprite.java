package jom.game.res.scripts;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Created by Anna on 20.03.2017.
 */

public class Sprite {

    /**
     * Image of the sprite.
     */
    private ImageView image;

    /**
     * World Map Position of the sprite.
     */
    private double worldMapPositionX;

    /**
     * World Map Position of the sprite.
     */
    private double worldMapPositionY;

    /**
     * Standard value for the sprite size.
     */
    private double width = 64;

    /**
     * Standard value for the sprite size.
     */
    private double height = 64;

    /**
     * Sprite super class constructor.
     */
    public Sprite()
    {
        worldMapPositionX = 0;
        worldMapPositionY = 0;
    }

    /**
     * World X Position of the sprite getter.
     * @return X pos.
     */
    public double getWorldMapPositionX(){
        return worldMapPositionX;
    }

    /**
     * World Y Position of the sprite getter.
     * @return Y pos.
     */
    public double getWorldMapPositionY(){
        return worldMapPositionY;
    }

    /**
     * Set Standard image for the sprite after it's object initialization.
     * @param filename Path to the image as string.
     */
    public void setImage(String filename)
    {
        ImageView i = new ImageView(filename);
        setImage(i);
    }

    /**
     * (Overloaded)
     * Set ImageView private variable for the sprite object. (Assign the image)
     * @param i ImageView from the previous setImage.
     */
    public void setImage(ImageView i)
    {
        image = i;
        i.setFitHeight(height);
        i.setFitWidth(width);
        i.setVisible(false);
//        width = i.getFitWidth();
//        height = i.getFitHeight();
    }

    /**
     * TODO NOT USED?!?!?
     * @return ImageView Object.
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * Update position on the World map when moving. (not rendering)
     * Only for sprites, that can move through the World Map Coordinates.
     * @param x x coordinates.
     * @param y y coordinates.
     */
    public void updatePos(double x, double y)
    {
        worldMapPositionX = x;
        worldMapPositionY = y;
    }

    /**
     * Adding Sprite to the Game Group(Pane). In order to render on it.
     * @param group
     */
    public void addToGroup(Group group)
    {
        group.getChildren().add(image);
    }

    /**
     * Render on the Game Scene at the given coordinates.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void renderAt(int x, int y) {
        image.setX(x);
        image.setY(y);
        /* Made visible only after the first rendering */
        image.setVisible(true);
    }

    /**
     * @return Rectangle Object. In order to check for the collision(within intersects method).
     */
    public Rectangle getColBox()
    {
        return new Rectangle(worldMapPositionX, worldMapPositionY, width, height);
    }

    /**
     * TODO THIS METHOD WAS CREATED FOR Rectangle2D, THAT DOES NOT HAVE INTERSECTS METHOD. NOW WE ARE USING Rectangle.
     */
    public boolean intersects(Sprite s)
    {
        return s.getColBox().intersects( this.getColBox().getBoundsInLocal() );
    }

    /**
     * Overridden toString method.
     * @return Specifically formatted String.
     */
    public String toString() {
        return " Position: [" + worldMapPositionX + "," + worldMapPositionY + "]";
    }


//    /**
//    Used in deprecated render with nanotime. (not handled delay between frames)
//     */
//    public void update(double time)
//    {
//        worldMapPositionX += velocityX * time;
//        worldMapPositionY += velocityY * time;
//    }
//
//    /**
//    Used with deprecated movement methods. Where movement was calculated within time and sprite velocity.
//     */
//    public void setVelocity(double x, double y)
//    {
//        velocityX = x;
//        velocityY = y;
//    }
//
//    /**
//    Same as above
//     */
//    public void addVelocity(double x, double y)
//    {
//        velocityX += x;
//        velocityY += y;
//    }
//
//    /**
//     * (Deprecated) was used at render w/o Hero Camera Focus.
//     */
//    public void renderPos()
//    {
//        image.setX(this.getWorldMapPositionX());
//        image.setY(this.getWorldMapPositionY());
//    }
//
//    /**
//     * (Deprecated & Bad) was used at render with Grid Pane.
//     */
//    public void renderAtGrid(GridPane grid)
//    {
//        grid.add(image);
//        image.setX(this.getWorldMapPositionX());
//        image.setY(this.getWorldMapPositionY());
//    }
}