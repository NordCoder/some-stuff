package core.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TetrisBlock {
    private final int posX;
    private int posY;
    private final int size;
    private final int type;

    public TetrisBlock(int posX, int posY, int size, int type) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.type = type;
    }

    public void draw(Graphics g, BufferedImage img) {
        g.drawImage(img, posX, posY, size, size, null);

    }

    public void moveDown() {
        posY += size;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getType() {
        return type;
    }
}
