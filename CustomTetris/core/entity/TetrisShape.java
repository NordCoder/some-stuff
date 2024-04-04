package core.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static core.utils.Constants.GameConstants.*;
import static core.utils.Constants.WindowConstants.GEN_BLOCK_POS_X;
import static core.utils.Constants.WindowConstants.GEN_BLOCK_POS_Y;

public class TetrisShape {
    private final int type;
    private int posX, posY, curRotation;
    private final int blockSize = BLOCK_SIZE;
    private final List<Rectangle> rects = new ArrayList<>();
    private List<Rectangle> projector;
    private int[][] size;
    private boolean changed = true;

    public TetrisShape(int type) {
        this.type = type;
        this.size = ROTATIONS[type][0];
        this.posX = GEN_BLOCK_POS_X;
        this.posY = GEN_BLOCK_POS_Y;
        updateRectArr(posX, posY);
    }

    public void update() {
        if (changed) {
            updateRectArr(posX, posY);
        }
    }

    public void draw(Graphics g, BufferedImage img) {
        for (Rectangle r : rects) {
            g.drawImage(img, r.x, r.y, blockSize, blockSize, null);
        }
        g.setColor(Color.GRAY);
        for (Rectangle r : projector) {
            g.fillRect(r.x, r.y, BLOCK_SIZE, BLOCK_SIZE);
        }

    }

    private void updateRectArr(int sx, int sy) {
        rects.clear();
        for (int i = 0; i < size.length; i++) {
            for (int j = 0; j < size[i].length; j++) {
                if (size[i][j] == 1) {
                    Rectangle rect = new Rectangle(sx + j * blockSize, sy + i * blockSize, blockSize, blockSize);
                    rects.add(rect);
                }
            }
        }
        changed = false;
    }


    public void rotate() {
        curRotation += 1;
        curRotation %= 4;
        this.size = ROTATIONS[type][curRotation];
        changed = true;
        updateRectArr(posX, posY);
    }

    public void moveDown() {
        posY += blockSize;
        for (Rectangle rect : rects) {
            rect.setLocation(rect.x, rect.y + blockSize);
        }
        changed = true;
    }

    public void moveRight() {
        posX += blockSize;
        for (Rectangle rect : rects) {
            rect.setLocation(rect.x + blockSize, rect.y);
        }
        changed = true;
    }

    public void moveLeft() {
        posX -= blockSize;
        for (Rectangle rect : rects) {
            rect.setLocation(rect.x - blockSize, rect.y);
        }
        changed = true;
    }

    public void moveUp() {
        posY -= blockSize;
        for (Rectangle rect : rects) {
            rect.setLocation(rect.x, rect.y + blockSize);
        }
        changed = true;
    }

    public void bruteDown() {
        for (int i = 0; i < projector.size(); i++) {
            rects.set(i, projector.get(i)) ;
        }
    }

    public List<Rectangle> getRects() {
        return rects;
    }

    public int getType() {
        return type;
    }

    public void setProjector(List<Rectangle> projector) {
        this.projector = projector;
    }
}
