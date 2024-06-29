package core.ui;

import core.gamestage.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;

import static core.utils.Utils.loadImage;


public class MenuButton extends Button {
    public MenuButton(int xPos, int yPos, int width, int height, Gamestate state) {
        super(xPos, yPos, width, height, state);
    }
}
