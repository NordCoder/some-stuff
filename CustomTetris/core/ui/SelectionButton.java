package core.ui;

import core.gamestage.Gamemode;
import core.gamestage.Gamestate;

import java.awt.image.BufferedImage;

public class SelectionButton extends Button {
    Gamemode mode;
    public SelectionButton(int xPos, int yPos, int width, int height, Gamestate state, Gamemode mode) {
        super(xPos, yPos, width, height, state);
        this.mode = mode;
    }


    public void applyMode() {
        Gamemode.mode = mode;
    }
}
