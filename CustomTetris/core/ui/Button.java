package core.ui;

import core.gamestage.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;

import static core.utils.Utils.loadImage;

public class Button {
    protected int xPos, yPos, index, xInitial, yInitial, width, height, initialHeight, initialWidth;
    protected Gamestate state;
    protected Rectangle bounds;
    protected boolean mouseOver, mousePressed;

    public Button(int xPos, int yPos, int width, int height, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.state = state;
        init();
        initBounds();
    }

    public void init() {
        index = 0;
        xInitial = xPos;
        yInitial = yPos;
        initialHeight = height;
        initialWidth = width;
    }

    private void initBounds() {
        this.bounds = new Rectangle(xPos, yPos, width, height);
    }

    public void render(Graphics g, BufferedImage[] imgs) {
        if (index == 0) {
            if (width > initialWidth) {
                width--;
            }
            if (height > initialHeight) {
                height--;
            }
            if (xPos < xInitial) {
                xPos++;
            }
            if (yPos < yInitial) {
                yPos++;
            }
            g.drawImage(imgs[index], xPos, yPos, width, height, null);
        } else if (index == 1) {
            if (width <= initialWidth + 10) {
                width++;
            }
            if (height <= initialHeight + 5) {
                height++;
            }
            if (xPos > xInitial - 10) {
                xPos--;
            }
            if (yPos > yInitial -  5) {
                yPos--;
            }
            g.drawImage(imgs[index], xPos, yPos, width, height, null);
        }
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
    }





    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}
