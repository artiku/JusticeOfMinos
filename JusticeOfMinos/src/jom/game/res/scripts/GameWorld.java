package jom.game.res.scripts;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Application.launch;

/**
 * Created by Anna on 20.03.2017.
 */
public class GameWorld extends Application {

    /**
     * Standard Frames Per Second value(render speed).
     */
    private final static int FPS = 60;

    /**
     * Standard screen width.
     */
    private final static int SCREENWIDTH = 800;

    /**
     * Standard screen height.
     */
    private final static int SCREENHEIGHT = 600;

    /**
     * Standard size of every sprite existing in the game.
     */
    private final static short SPRITESIZE = 64;

    /**
     * Spawn cell X coordinate for the Hero Character.
     */
    private final static int heroSpawnX = 11 * SPRITESIZE;

    /**
     * Spawn cell Y coordinate for the Hero Character.
     */
    private final static int heroSpawnY = 5 * SPRITESIZE;

    /**
     * Hero Speed, how many pixels could hero move Per Frame.
     */
    private final static int HEROSPEED = FPS / 24;

    /**
     * Center X coordinate for the Camera View.
     */
    private final static int cameraCenterX = SCREENWIDTH / 2;

    /**
     * Center Y coordinate for the Camera View.
     */
    private final static int cameraCenterY = SCREENHEIGHT / 2;

    /**
     * Keep values of every key pressed.
     */
    private ArrayList<String> input = new ArrayList<>();

    /**
     * Keep walls. In order to check collisions.
     */
    private ArrayList<Wall> wallList = new ArrayList<>();

    /**
     * Map as String. Would be converted into objects.
     * '#' - Wall
     * ' ' - Floor
     * '@' -
     */
    private String map = "#############\n"
            + "#           #\n"
            + "# ### #### ##\n"
            + "# #   ####  #\n"
            + "# # ### ### #\n"
            + "#   ###     #\n"
            + "###     ### #\n"
            + "#  ######   #\n"
            + "##      # ###\n"
            + "#### ## #  ##\n"
            + "# ## #  ## ##\n"
            + "#    # ##  ##\n"
            + "# ####    ###\n"
            + "#############\n";

    /**
     * Hash map to keep Coordinates as a Key & Object instance that owns these Coordinates.
     * In order to render Camera View.
     */
    private Map<String, Sprite> spriteWorldMapCoordsHashMap = new HashMap<>();

    /**
     * Instance of one and only Hero Character.
     */
    private Char hero;

    /**
     * Sets up input of every Key Pressed. And removes it, when Key Released.
     * @param primaryStage Stage where input occurs.
     */
    private void setupInput(Stage primaryStage) {
        Scene theScene = primaryStage.getScene();

        theScene.setOnKeyPressed(  // new EventHandler<KeyEvent>()
                e -> {
                    String code = e.getCode().toString();

                    // only add once... prevent duplicates
                    if ( !input.contains(code) )
                        input.add( code );
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove( code );
                });
    }

    /**
     * Render Camera View (Only the area, that is seen at the moment).
     * Camera is player focused. (Player is always in the center.)
     * @param boardGroup Group(Pane) where render to (Camera View)
     */
    public void renderCameraView(Group boardGroup) {
        /* Coordinates on the World Map where the left upper corner of the Camera View is at the moment. */
        int startDrawPosOnCameraViewX = (int) hero.getWorldMapPositionX() - cameraCenterX;
        int startDrawPosOnCameraViewY = (int) hero.getWorldMapPositionY() - cameraCenterY;

        /* Rendering Hero */
        hero.renderAt(cameraCenterX, cameraCenterY);
        //TODO Collision box. Rendering it for fun and collision check TEST.
        hero.renderBox(cameraCenterX, cameraCenterY);

        /* Getting closest exact coordinates of the sprite on the World Map from start drawing position
        (left upper corner of the Camera View) relatively to World Map. */
        int startSpriteToDrawX = startDrawPosOnCameraViewX / SPRITESIZE;
        int startSpriteToDrawY = startDrawPosOnCameraViewY / SPRITESIZE;
        startSpriteToDrawX *= SPRITESIZE;
        startSpriteToDrawY *= SPRITESIZE;

        /* Block to draw in line. */
        int x = 0;
        /* Block to draw in column. */
        int y = 0;

        // Map draw only (By integer coordinates)
        for (int spriteToDrawY = startSpriteToDrawY; spriteToDrawY <= SCREENHEIGHT + startDrawPosOnCameraViewY; spriteToDrawY += SPRITESIZE) {

            for (int spriteToDrawX = startSpriteToDrawX; spriteToDrawX <= SCREENWIDTH + startDrawPosOnCameraViewX; spriteToDrawX += SPRITESIZE) {

                // TODO Is there any other way to keep coordinates together? Anything faster than string? Like tuple at Python.
                Sprite sprite = spriteWorldMapCoordsHashMap.get( spriteToDrawX + ":" + spriteToDrawY);
                if (sprite != null) {
                    int cameraDrawX = SPRITESIZE * x - startDrawPosOnCameraViewX % SPRITESIZE;
                    int cameraDrawY = SPRITESIZE * y - startDrawPosOnCameraViewY % SPRITESIZE;
                    sprite.renderAt(cameraDrawX, cameraDrawY);
                }
                x++;
            }
            y++;
            x = 0;
        }

    }

    /**
     * Creates World Map objects from the Map String.
     * @param boardGroup Group (Game World Pane)
     */
    private void createGameWorldMap(Group boardGroup) {
        int x = 0;
        int y = 0;

        for (char c : map.toCharArray()) {
            if (c == '#') {
                Wall wall = new Wall(x * SPRITESIZE, y * SPRITESIZE);
                spriteWorldMapCoordsHashMap.put( (int) wall.getWorldMapPositionX() + ":" + y * SPRITESIZE, wall);
                wallList.add(wall);
                wall.addToGroup(boardGroup);
            } else if (c == ' ') {
                Sprite floor = new Floor(x * SPRITESIZE, y * SPRITESIZE);
                spriteWorldMapCoordsHashMap.put( (int) floor.getWorldMapPositionX() + ":" + (int) floor.getWorldMapPositionY(), floor);
                floor.addToGroup(boardGroup);
            } else if (c == '\n') {
                y++;
                x = -1;
            }
            x++;
        }
    }

    /**
     * Spawn Hero Character.
     * @param boardGroup Group of the Game World.
     */
    private void spawnHeroCharacter(Group boardGroup) {
        hero = new Char(heroSpawnX, heroSpawnY);
        hero.addToGroup(boardGroup);
    }

    /**
     * Check for the collision between Hero and World Map.
     * @param nextPosX Predictable X pos of the hero.
     * @param nextPosY Predictable Y pos of the hero.
     * @return false if collides and movement is not allowed
     */
    private boolean checkCollision(int nextPosX, int nextPosY) {
        for (Wall wall : wallList) {
            int xColBox = nextPosX + hero.getCollisionBoxX();
            int yColBox = nextPosY + hero.getCollisionBoxY();
            int widthColBox =  hero.getCollisionBoxWidth();
            int heightColBox =  hero.getCollisionBoxHeight();
                    /* We create predictable Collision Box and if it collide, next position wont be updated. */
            if (new Rectangle(xColBox, yColBox, widthColBox, heightColBox).intersects(wall.getColBox().getBoundsInLocal())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Input processing. Movement. Render.
     * @param boardGroup Group(Main Pane)
     */
    private void gameProcessesTimeline(Group boardGroup) {
        /* Initializing timeline for the game processes */
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        /* Rounding for the update delay value */
        BigDecimal updateDelay = new BigDecimal(1);
        updateDelay = updateDelay.divide(BigDecimal.valueOf(FPS), 4, BigDecimal.ROUND_HALF_UP);  // 0.0167

        /* KeyFrame creation */
        KeyFrame kf = new KeyFrame(
                Duration.seconds(updateDelay.doubleValue()),        // 0.017 - speed for 60 FPS
                event -> {                    // new EventHandler<ActionEvent>()

                    double nextPosX = hero.getWorldMapPositionX();
                    double nextPosY = hero.getWorldMapPositionY();

                    // TODO Wrap into dedicated method or even class(Controls / jom.Controller)
                    if (input.contains("LEFT"))
                        nextPosX -= HEROSPEED;
                    if (input.contains("RIGHT"))
                        nextPosX += HEROSPEED;
                    if (input.contains("UP"))
                        nextPosY -= HEROSPEED;
                    if (input.contains("DOWN"))
                        nextPosY += HEROSPEED;

                    if (input.contains("LEFT") && input.contains("SHIFT"))
                        nextPosX -= HEROSPEED * 1.15;
                    if (input.contains("RIGHT") && input.contains("SHIFT"))
                        nextPosX += HEROSPEED * 1.15;
                    if (input.contains("UP") && input.contains("SHIFT"))
                        nextPosY -= HEROSPEED * 1.15;
                    if (input.contains("DOWN") && input.contains("SHIFT"))
                        nextPosY += HEROSPEED * 1.15;

                    /* Rendering Camera View (All the details that player should see) */
                    renderCameraView(boardGroup);

                    if (checkCollision((int) nextPosX, (int) nextPosY)) {
                        hero.updatePos(nextPosX, nextPosY);
                    }

                }
        );

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * Start of the JavaFx Application.
     * @param primaryStage Primary Stage.
     * @throws Exception Exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        /* Setting Main Group(Pane) */
        Group boardGroup = new Group();

        /* Setting Scene */
        primaryStage.setScene(new Scene(boardGroup, SCREENWIDTH, SCREENHEIGHT));
        primaryStage.setTitle("Justice Of Minos");
        primaryStage.show();

        /* Setting up controls. Only after setting the Scene. */
        setupInput(primaryStage);

        /* Creating a map from private Map String */
        createGameWorldMap(boardGroup);

        /* Spawning hero character on the World Map */
        spawnHeroCharacter(boardGroup);

        // TODO TEST. AFTER REMOVE
        boardGroup.getChildren().add(hero.getColBox());

        gameProcessesTimeline(boardGroup);

//        I SAY IT HERE IN ORDER TO LEARN MORE ANIMATION METHODS
//        final double startNanoTime = System.nanoTime();
//        new AnimationTimer()
//        {
//            double lastNanoTime = startNanoTime;
//            public void handle(long currentNanoTime)
//            {
//                // calculate time since last update.
//                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
//                lastNanoTime = currentNanoTime;
//
//                // game logic
//
//                character.setVelocity(0,0);
//                if (input.contains("LEFT"))
//                    character.addVelocity(-150,0);
//                if (input.contains("RIGHT"))
//                    character.addVelocity(150,0);
//                if (input.contains("UP"))
//                    character.addVelocity(0,-150);
//                if (input.contains("DOWN"))
//                    character.addVelocity(0,150);
//
//                character.update(elapsedTime);
//
//                character.renderPos();
//            }
//        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
