package com.game.justiceofminos;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static com.game.justiceofminos.JusticeofMinos.SCREEN_HEIGHT;
import static com.game.justiceofminos.JusticeofMinos.SCREEN_WIDTH;
import static com.game.justiceofminos.JusticeofMinos.appRoot;

/**
 * (Def) Created by Anna on 21.04.2017.
 */
class LabelControl {

    /**
     * If label created.
     */
    private static boolean created = false;

    /**
     * Label Fade.
     */
    private static FadeTransition fadeIn = new FadeTransition();


    /**
     * Last nano time counted.
     */
    private double startTime = System.currentTimeMillis();

    /**
     * Timer label.
     */
    private Label timerLabel = new Label();

    /**
     * Constructor.
     */
    LabelControl() {
        final int timerOffset = 10;
        timerLabel.setTranslateX(timerOffset);
        timerLabel.setTranslateY(timerOffset);
        timerLabel.setTextFill(Color.SNOW);
        appRoot.getChildren().add(timerLabel);
    }

    /**
     *
     * @return End Scene.
     */
    Scene gameEnd() {
        final int fontSize1 = 50;
        final int fontSize2 = 160;

        BorderPane endPane = new BorderPane();
        endPane.setStyle("-fx-background-color: black;");
        Label endLabel = new Label("You've finished the level for");
        Label endLabel2 = new Label(timerLabel.getText());
        endPane.setAlignment(endLabel, Pos.CENTER);
        endPane.setTop(endLabel);
        endPane.setCenter(endLabel2);
        endPane.setCenter(endLabel2);
        endLabel.setTextFill(Color.GHOSTWHITE);
        endLabel2.setTextFill(Color.GHOSTWHITE);
        endLabel.setFont(new Font("Monospace", fontSize1));
        endLabel2.setFont(new Font("Monospace", fontSize2));
        endLabel.setTranslateY(SCREEN_HEIGHT / (2 + 1));
        //TODO Restart Button.
        return new Scene(endPane, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
    }

    /**
     * Update time method.
     */
    void updateTime() {
        final int secondsCount = 60;
        final int millis = 1000;

        String format = String.format("%%0%dd", 2);
        int elapsedTime = (int) (System.currentTimeMillis() - startTime) / millis;
        String seconds = String.format(format, elapsedTime % secondsCount);
        String minutes = String.format(format, (elapsedTime % (secondsCount * secondsCount)) / secondsCount);
        String time =  minutes + ":" + seconds;
        timerLabel.setText(time);
    }

    /**
     * Exit label.
     */
    static void exitLabel() {
        final int fadeDuration = 3000;
        if (!created) {
            final int offsetX = 150;

            Label exitLabel = new Label("You need the key!\nFind it in chests.");
            exitLabel.setTextFill(Color.WHITESMOKE);
            exitLabel.setWrapText(true);
            exitLabel.setTranslateX((SCREEN_WIDTH / 2) - offsetX);
            exitLabel.setTranslateY(SCREEN_HEIGHT / 2);
            appRoot.getChildren().add(exitLabel);

            fadeIn = new FadeTransition(
                    Duration.millis(fadeDuration)
            );
            fadeIn.setNode(exitLabel);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            created = true;
        }
        fadeIn.playFromStart();
    }
}
