package com.game.justiceofminos;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * (Def) Created by Anna on 21.04.2017.
 */
public class LabelControl {

    /**
     * If label created.
     */
    private static boolean created = false;

    /**
     * Label Fade.
     */
    private static FadeTransition fadeIn = new FadeTransition();

    /**
     * Exit label.
     */
    static void exitLabel() {
        final int fadeDuration = 3000;
        if (!created) {
            Label exitLabel = new Label("You need the key!\nFind it in chests.");
            exitLabel.setTextFill(Color.WHITESMOKE);
            exitLabel.setWrapText(true);
            exitLabel.setTranslateX(JusticeofMinos.SCREEN_WIDTH / 2);
            exitLabel.setTranslateY(JusticeofMinos.SCREEN_HEIGHT / 2);
            JusticeofMinos.appRoot.getChildren().add(exitLabel);

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
