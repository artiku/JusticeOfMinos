package com.game.justiceofminos;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * (Default) Created by Anna on 20.04.2017.
 */
public class GradientAlphaMask extends Pane {

    /**
     * Image.
     */
    private Image gradientImage = new Image(Assets.GRADIENT);

    /**
     * Constructor.
     */
    public GradientAlphaMask() {
        ImageView gradientView = new ImageView(gradientImage);
        getChildren().add(gradientView);
        gradientView.setFitWidth(JusticeofMinos.SCREEN_WIDTH);
        gradientView.setFitHeight(JusticeofMinos.SCREEN_HEIGHT);
        JusticeofMinos.root.getChildren().add(this);
    }
}
