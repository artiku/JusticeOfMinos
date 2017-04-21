package com.game.justiceofminos;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * (Default) Created by Anna on 20.03.2017.
 */
public class JusticeofMinos extends Application {

    /**
     * Standard screen width.
     */
    static final int SCREEN_WIDTH = 1024;

    /**
     * Standard screen height.
     */
    static final int SCREEN_HEIGHT = 600;

    /**
     * Standard size of every block(sprite) exist in the game.
     */
    static final short BLOCK_SIZE = 90;

    /**
     * Initial player movement speed.
     */
    private static final int MOVEMENT_SPEED = 5;

    /**
     * Spawn cell X coordinate for the Hero Character.
     */
    private int heroSpawnX;

    /**
     * Spawn cell Y coordinate for the Hero Character.
     */
    private int heroSpawnY;

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
     * Chests.
     */
    static ArrayList<Block> chestArray = new ArrayList<>();

    /**
     * Gate block.
     */
    private Block gates;

    /**
     * Player movement speed.
     */
    private int playerMovementSpeed = MOVEMENT_SPEED;

    /**
     * Primary Application Pane.
     */
    static Pane appRoot = new Pane();

    /**
     * Primary scene.
     */
    private Scene scene;

    /**
     * Primary root Node.
     */
    static Pane root = new Pane();

    /**
     * Instance of one and only Hero Character.
     */
    private Character player;

    /**
     * Chest to be collected to get the key.
     */
    private int toBeCollected = 0;

    /**
     * Level width.
     */
    private int levelWidth;

    /**
     * Level height.
     */
    private int levelHeight;

    /**
     * If key was found by the player.
     */
    private boolean keyFound = false;



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
        scene.getStylesheets().add(getClass().getResource("fontstyle.css").toExternalForm());
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
                collectionCheck();
                exitCheck();
//                damageCheck();
            }
        }.start();
//        initSpears();
    }

    /**
     * Check collision with the Exit Gates and check if player has key.
     */
    private void exitCheck() {
        final int offsetToCenter = Character.HERO_SIZE / 4;

        Rectangle2D playerPos = new Rectangle2D(player.getTranslateX() + offsetToCenter,
                player.getTranslateY() + offsetToCenter * 2,
                offsetToCenter * 2 + 2, offsetToCenter * 2);
        if (playerPos.intersects(gates.getTranslateX(), gates.getTranslateY(),
                JusticeofMinos.BLOCK_SIZE, JusticeofMinos.BLOCK_SIZE * 2)) {
            if (keyFound) {
//                gameFinish();
             } else {
                LabelControl.exitLabel();
            }
        }


    }

    /**
     * Collects chests.
     */
    private void collectionCheck() {
        final int offsetToCenter = Character.HERO_SIZE / 4;

        Rectangle2D playerPos = new Rectangle2D(player.getTranslateX() + offsetToCenter,
                player.getTranslateY() + offsetToCenter * 2,
                offsetToCenter * 2 + 2, offsetToCenter * 2);
        for (Block chest : chestArray) {
            if (playerPos.intersects(chest.getTranslateX(), chest.getTranslateY(),
                    JusticeofMinos.BLOCK_SIZE, JusticeofMinos.BLOCK_SIZE)) {
                if (!chest.collected) {
                    chest.block.setViewport(new Rectangle2D(0, Block.FILE_SIZE * (2 + 1),
                            Block.FILE_SIZE - 1, Block.FILE_SIZE - 1));
                    chest.collected = true;
                    toBeCollected--;
                    if (toBeCollected == 0) {
                        showKeyLabel();
                    }
                }
            }
        }
    }

    /**
     * If every chest was opened give player the key and notify about that.
     */
    private void showKeyLabel() {
        final int labelOffset = 12;

        Label keyLabel = new Label("You've found the key!\nFind the exit now!");
        keyLabel.setWrapText(true);
        keyLabel.setTranslateX(SCREEN_WIDTH / labelOffset);
        keyLabel.setTranslateY(SCREEN_HEIGHT / (labelOffset / 2) * (labelOffset / 2 - 1));
        appRoot.getChildren().add(keyLabel);

        final int viewOffsetX = 50;
        final int viewOffsetY = 70;

        ImageView key = new ImageView(new Image(Assets.KEY));
        key.setScaleX(2);
        key.setScaleY(2);
        key.setX(SCREEN_WIDTH - viewOffsetX);
        key.setY(viewOffsetY);
        appRoot.getChildren().add(key);

        keyFound = true;
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
                        break;
                    case 'T':
                        Block tWallBlock = new Block(Block.BlockType.T_WALL, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case 'H':
                        Block horizontalWallBlock = new Block(Block.BlockType.CORRIDOR_WALL_HORIZONTAL,
                                x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case 'V':
                        Block verticcalWallBlock = new Block(Block.BlockType.CORRIDOR_WALL_VERTICAL,
                                x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case ' ':
                        Block floorBlock = new Block(Block.BlockType.FLOOR, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        break;
                    case 'C':
                        Block chestBlock = new Block(Block.BlockType.CHEST, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        toBeCollected++;
                        break;
                    case 'G':
                        Block gateBlock = new Block(Block.BlockType.GATE, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        gates = gateBlock;
                        break;
                    case '@':
                        Block floorSpawnBlock = new Block(Block.BlockType.FLOOR, x * BLOCK_SIZE, y * BLOCK_SIZE);
                        heroSpawnX = x * BLOCK_SIZE;
                        heroSpawnY = y * BLOCK_SIZE;
                        break;
                    default:
                        break;
                }

            }
        }


        spawnHero();

        root.getChildren().add(player);
        appRoot.getChildren().add(root);

        GradientAlphaMask gradient = new GradientAlphaMask();
        appRoot.getChildren().add(gradient);

    }

    /**
     * Spawn hero character.
     */
    private void spawnHero() {
        player = new Character();
        final int maxOffset = 200;

        player.translateXProperty().addListener((ons, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > maxOffset && offset < levelWidth - maxOffset) {
                root.setLayoutX(-(offset - SCREEN_WIDTH / 2) - Character.HERO_SIZE / 2);
            }
        });
        player.translateYProperty().addListener((ons, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > maxOffset && offset < levelHeight - maxOffset) {
                root.setLayoutY(-(offset - SCREEN_HEIGHT / 2) - Character.HERO_SIZE / 2);
            }
        });


        player.setTranslateX(heroSpawnX);
        player.setTranslateY(heroSpawnY);

//        double offsetPrimalX = player.getTranslateX();
        double offsetPrimalY = player.getTranslateY();

        System.out.println(player.getTranslateY());
        if (offsetPrimalY < maxOffset) {
            root.setLayoutY((SCREEN_HEIGHT / 2 - maxOffset) - Character.HERO_SIZE / 2);
        }
    }

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

