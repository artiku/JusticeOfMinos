package com.game.justiceofminos;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * (Default) Created by Anna on 20.03.2017.
 */
public class JusticeofMinos extends Application {

    /**
     * Standard screen width.
     */
    private static final int SCREEN_WIDTH = 1280;

    /**
     * Standard screen height.
     */
    private static final int SCREEN_HEIGHT = 960;

    /**
     * Standard size of every block(sprite) exist in the game.
     */
    static final short BLOCK_SIZE = 32;

    /**
     * Initial player movement speed.
     */
    private static final int MOVEMENT_SPEED = 5;

    /**
     * Spawn cell X coordinate for the Hero Character.
     */
    private static final int HERO_SPAWN_X = 11 * BLOCK_SIZE;

    /**
     * Spawn cell Y coordinate for the Hero Character.
     */
    private static final int HERO_SPAWN_Y = 5 * BLOCK_SIZE;

    /**
     * Keep values of every key pressed.
     */
    private ArrayList<KeyCode> keyInput = new ArrayList<>();

    /**
     * Current animation.
     */
    private Animation currentAnim = new Timeline();

    /**
     * Keep walls. In order to check collisions.
     */
    static ArrayList<Block> wallStreet = new ArrayList<>();

    /**
     * Player movement speed.
     */
    private int playerMovementSpeed = MOVEMENT_SPEED;

    /**
     * Primary Application Pane.
     */
    private static Pane appRoot = new Pane();

    /**
     * Primary scene.
     */
    private Scene scene;

    /**
     * Primary root Node.
     */
    static Pane root = new Pane();
//
//    /**
//     * Map array.
//     */
//    static ArrayList<ArrayList<Block>> mapArray = new ArrayList<>();

    /**
     * Instance of one and only Hero Character.
     */
    private Character player;

    /**
     * Level width.
     */
    private int levelWidth;

    /**
     * Level height.
     */
    private int levelHeight;




    /**
     * Start of the JavaFx Application.
     * @param primaryStage Primary Stage.
     * @throws Exception Exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        initGameWorld();
//
//        /* Setting Main Group(Pane) */
//        Group boardGroup = new Group();

        /* Setting Scene */
        scene = new Scene(appRoot, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Justice Of Minos");
        primaryStage.show();

        /* Setting up controls. Only after setting the Scene. */
        initializeKeyInput();

        /* Main thread */
//        AnimationTimer everyFrameUpdate =
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                update();
            }
        }.start();
    }

    /**
     * Init key controls.
     */
    private void initializeKeyInput() {
        scene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.RIGHT && !keyInput.contains(KeyCode.RIGHT)) {

                keyInput.add(event.getCode());
            } else if (event.getCode() == KeyCode.LEFT && !keyInput.contains(KeyCode.LEFT)) {

                keyInput.add(event.getCode());
            } else if (event.getCode() == KeyCode.UP && !keyInput.contains(KeyCode.UP)) {

                keyInput.add(event.getCode());
            } else if (event.getCode() == KeyCode.DOWN && !keyInput.contains(KeyCode.DOWN)) {

                keyInput.add(event.getCode());
            }
        });
        scene.setOnKeyReleased(event -> {
            currentAnim.pause();
            if (event.getCode() == KeyCode.RIGHT) {
                keyInput.remove(event.getCode());
            }
            if (event.getCode() == KeyCode.LEFT) {

                keyInput.remove(event.getCode());
            }
            if (event.getCode() == KeyCode.UP) {

                keyInput.remove(event.getCode());
            }
            if (event.getCode() == KeyCode.DOWN) {

                keyInput.remove(event.getCode());
            }
        });
    }

    /**
     * Update method.
     */
    private void update() {

//        currentAnim.pause();
        if (keyInput.contains(KeyCode.RIGHT)) {
            player.moveX(playerMovementSpeed);
            //TODO or do this:
            player.animation.right.play();

//            currentAnim = moveRight;
            currentAnim.play();
        } else if (keyInput.contains(KeyCode.LEFT)) {
            player.moveX(-playerMovementSpeed);
            player.animation.left.play();

//            currentAnim = moveLeft;
            currentAnim.play();
        } else if (keyInput.contains(KeyCode.UP)) {
            player.moveY(-playerMovementSpeed);
            player.animation.up.play();

//            currentAnim = moveUp;
            currentAnim.play();
        } else if (keyInput.contains(KeyCode.DOWN)) {
            player.moveY(playerMovementSpeed);
            player.animation.down.play();

//            currentAnim = moveDown;
//            currentAnim.play();
        }
    }

    /**
     * Initialize game world content.
     */
    private void initGameWorld() {
        /* WILL IT WORK??? */
        levelWidth = Level.REGULAR_LEVEL_DATA[0].length() * BLOCK_SIZE;
        levelHeight = Level.REGULAR_LEVEL_DATA.length * BLOCK_SIZE;
        for (int y = 0; y < Level.REGULAR_LEVEL_DATA.length; y++) {
            String line = Level.REGULAR_LEVEL_DATA[y];
            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case '#':
                        Block wallBlock = new Block(Block.BlockType.WALL, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        // TODO Could be done in class(better) or here
//                        wallStreet.add(wallBlock);
                        break;
                    case ' ':
                        Block floorBlock = new Block(Block.BlockType.FLOOR, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case 'C':
                        Block chestBlock = new Block(Block.BlockType.CHEST, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case 'E':
                        Block gateBlock = new Block(Block.BlockType.GATE, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                }

            }
        }

        player = new Character();
        player.setTranslateX(SCREEN_WIDTH / 2);
        player.setTranslateY(SCREEN_HEIGHT / 2);
        player.translateXProperty().addListener((ons, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 200 && offset < levelWidth - 200) {
                root.setLayoutX(-(offset - SCREEN_WIDTH / 2));
                // Here we can move background as well: background.<<same>>
            }
        });
        player.translateYProperty().addListener((ons, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 200 && offset < levelHeight - 200) {
                root.setLayoutY(-(offset - SCREEN_HEIGHT / 2));
                // Here we can move background as well: background.<<same>>
            }
        });
        root.getChildren().add(player);
        appRoot.getChildren().add(root);

        // TODO UnderStaNd!
        /* ?????????????????????? */
//        for (int y = 0; y <= map2.length; y++) {
//            ArrayList<Block> xWorldStrip = new ArrayList<>();
//            for (int x = 0; x <= map2[y].length(); x++) {
//                if (map2[y].charAt(x) == '#') {
//                    xWorldStrip.add(Block.WALL);
//                }
//            }
//            mapArray.add(xWorldStrip);
//        }

//        int x = 0;
//        int y = 0;
//
//        for (char c : map.toCharArray()) {
//            if (c == '#') {
//                Wall wall = new Wall(x * SPRITESIZE, y * SPRITESIZE);
//                spriteWorldMapCoordsHashMap.put( (int) wall.getWorldMapPositionX() + ":" + y * SPRITESIZE, wall);
//                wallList.add(wall);
//                wall.addToGroup(boardGroup);
//            } else if (c == ' ') {
//                Sprite floor = new Floor(x * SPRITESIZE, y * SPRITESIZE);
//                spriteWorldMapCoordsHashMap.put( (int) floor.getWorldMapPositionX() + ":" +
// (int) floor.getWorldMapPositionY(), floor);
//                floor.addToGroup(boardGroup);
//            } else if (c == '\n') {
//                y++;
//                x = -1;
//            }
//            x++;
//        }
    }

//    /**
//     * Render Camera View (Only the area, that is seen at the moment).
//     * Camera is player focused. (Player is always in the center.)
//     * @param boardGroup Group(Pane) where render to (Camera View)
//     */
//    public void renderCameraView(Group boardGroup) {
//        /* Coordinates on the World Map where the left upper corner of the Camera View is at the moment. */
//        int startDrawPosOnCameraViewX = (int) hero.getWorldMapPositionX() - cameraCenterX;
//        int startDrawPosOnCameraViewY = (int) hero.getWorldMapPositionY() - cameraCenterY;
//
//        /* Rendering Hero */
//        hero.renderAt(cameraCenterX, cameraCenterY);
//        //TODO Collision box. Rendering it for fun and collision check TEST.
//        hero.renderBox(cameraCenterX, cameraCenterY);
//
//        /* Getting closest exact coordinates of the sprite on the World Map from start drawing position
//        (left upper corner of the Camera View) relatively to World Map. */
//        int startSpriteToDrawX = startDrawPosOnCameraViewX / SPRITESIZE;
//        int startSpriteToDrawY = startDrawPosOnCameraViewY / SPRITESIZE;
//        startSpriteToDrawX *= SPRITESIZE;
//        startSpriteToDrawY *= SPRITESIZE;
//
//        /* Block to draw in line. */
//        int x = 0;
//        /* Block to draw in column. */
//        int y = 0;
//
//        // Map draw only (By integer coordinates)
//        for (int spriteToDrawY = startSpriteToDrawY; spriteToDrawY <= SCREEN_HEIGHT +
// startDrawPosOnCameraViewY; spriteToDrawY += SPRITESIZE) {
//
//            for (int spriteToDrawX = startSpriteToDrawX; spriteToDrawX <= SCREEN_WIDTH +
// startDrawPosOnCameraViewX; spriteToDrawX += SPRITESIZE) {
//
//                // TODO Is there any other way to keep coordinates together? Anything
// faster than string? Like tuple at Python.
//                Sprite sprite = spriteWorldMapCoordsHashMap.get( spriteToDrawX + ":" + spriteToDrawY);
//                if (sprite != null) {
//                    int cameraDrawX = SPRITESIZE * x - startDrawPosOnCameraViewX % SPRITESIZE;
//                    int cameraDrawY = SPRITESIZE * y - startDrawPosOnCameraViewY % SPRITESIZE;
//                    sprite.renderAt(cameraDrawX, cameraDrawY);
//                }
//                x++;
//            }
//            y++;
//            x = 0;
//        }
//
//    }



//    /**
//     * Spawn Hero Character.
//     * @param boardGroup Group of the Game World.
//     */
//    private void spawnHeroCharacter(Group boardGroup) {
//        hero = new Char(heroSpawnX, heroSpawnY);
//        hero.addToGroup(boardGroup);
//    }
//
//    /**
//     * Check for the collision between Hero and World Map.
//     * @param nextPosX Predictable X pos of the hero.
//     * @param nextPosY Predictable Y pos of the hero.
//     * @return false if collides and movement is not allowed
//     */
//    private boolean checkCollision(int nextPosX, int nextPosY) {
//        for (Wall wall : wallList) {
//            int xColBox = nextPosX + hero.getCollisionBoxX();
//            int yColBox = nextPosY + hero.getCollisionBoxY();
//            int widthColBox =  hero.getCollisionBoxWidth();
//            int heightColBox =  hero.getCollisionBoxHeight();
//                    /* We create predictable Collision Box and if it collide, next position wont be updated. */
//            if (new Rectangle(xColBox, yColBox, widthColBox, heightColBox)
// .intersects(wall.getColBox().getBoundsInLocal())) {
//                return false;
//            }
//        }
//        return true;
//    }

//    /**
//     * Input processing. Movement. Render.
//     * @param boardGroup Group(Main Pane)
//     */
//    private void gameProcessesTimeline(Group boardGroup) {
//        /* Initializing timeline for the game processes */
//        final Timeline timeline = new Timeline();
//        timeline.setCycleCount(Timeline.INDEFINITE);
//
//        /* Rounding for the update delay value */
//        BigDecimal updateDelay = new BigDecimal(1);
//        updateDelay = updateDelay.divide(BigDecimal.valueOf(FPS), 4, BigDecimal.ROUND_HALF_UP);  // 0.0167
//
//        /* KeyFrame creation */
//        KeyFrame kf = new KeyFrame(
//                Duration.seconds(updateDelay.doubleValue()),        // 0.017 - speed for 60 FPS
//                event -> {                    // new EventHandler<ActionEvent>()
//
//                    double nextPosX = hero.getWorldMapPositionX();
//                    double nextPosY = hero.getWorldMapPositionY();
//
//                    // TODO Wrap into dedicated method or even class(Controls / jom.game.Controller)
//                    if (input.contains("LEFT"))
//                        nextPosX -= HEROSPEED;
//                    if (input.contains("RIGHT"))
//                        nextPosX += HEROSPEED;
//                    if (input.contains("UP"))
//                        nextPosY -= HEROSPEED;
//                    if (input.contains("DOWN"))
//                        nextPosY += HEROSPEED;
//
//                    if (input.contains("LEFT") && input.contains("SHIFT"))
//                        nextPosX -= HEROSPEED * 1.15;
//                    if (input.contains("RIGHT") && input.contains("SHIFT"))
//                        nextPosX += HEROSPEED * 1.15;
//                    if (input.contains("UP") && input.contains("SHIFT"))
//                        nextPosY -= HEROSPEED * 1.15;
//                    if (input.contains("DOWN") && input.contains("SHIFT"))
//                        nextPosY += HEROSPEED * 1.15;
//
//                    /* Rendering Camera View (All the details that player should see) */
//                    renderCameraView(boardGroup);
//
//                    if (checkCollision((int) nextPosX, (int) nextPosY)) {
//                        hero.updatePos(nextPosX, nextPosY);
//                    }
//
//                }
//        );
//
//        timeline.getKeyFrames().add(kf);
//        timeline.play();
//    }

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

