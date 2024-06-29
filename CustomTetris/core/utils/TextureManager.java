package core.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static core.utils.Utils.loadImage;

public class TextureManager {
    private BufferedImage blockAtlas;
    private BufferedImage[][] menuImages = new BufferedImage[2][2];
    private BufferedImage[] blockImgs = new BufferedImage[7];
    private BufferedImage[] gameBacks = new BufferedImage[1];
    private BufferedImage[][] selectionMenuImages = new BufferedImage[3][2];
    private BufferedImage[] menuAnimationFrames = new BufferedImage[96];
    private BufferedImage[][] winLoseImgs = new BufferedImage[2][2];
    private BufferedImage[][] pauseImg = new BufferedImage[1][1];
    private Font font;
    public TextureManager() {
        loadAndHandle();
    }

    private void loadAndHandle() {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/Summer-Blues.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.TRUETYPE_FONT, 32);
            is.close();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        blockAtlas = loadImage("resources/GLOWING_4_BLUE.png");
        for (int i = 0; i < 7; i++) {
            blockImgs[i] = blockAtlas.getSubimage(128 * i, 0, 128, 128);
        }
        menuImages[0][0] = loadImage("resources/TETRIS_TITLE_HR.png");
        menuImages[1][0] = loadImage("resources/PLAY_NN.png");
        menuImages[1][1] = loadImage("resources/PLAY_G.png");

        for (int i = 0; i < 96; i++) {
            String s = Integer.toString(i);
            while (s.length() < 5) {
                s = "0" + s;
            }
            menuAnimationFrames[i] = loadImage(String.format("resources/MainScene/Main Scene_%s.png", s));
        }

        gameBacks[0] = loadImage("resources/MainScene/Main Scene_00000.png");

        BufferedImage selectionMenuButtonAtlas = loadImage("resources/SELECTION_BUTTONS.png");
        selectionMenuImages[0][0] = loadImage("resources/SELECTION_TITLE.png");
        selectionMenuImages[1][0] = selectionMenuButtonAtlas.getSubimage(0,0,256,128);
        selectionMenuImages[1][1] = selectionMenuButtonAtlas.getSubimage(0,128,256,128);
        selectionMenuImages[2][0] = selectionMenuButtonAtlas.getSubimage(256, 0, 256, 128);
        selectionMenuImages[2][1] = selectionMenuButtonAtlas.getSubimage(256, 128, 256, 128);

        winLoseImgs[0][0] = loadImage("resources/WON.png");
        winLoseImgs[0][1] = loadImage("resources/LOSE.png");
        winLoseImgs[1][0] = loadImage("resources/BACK_TO_MENU_BUTTON.png");
        winLoseImgs[1][1] = loadImage("resources/BACK_TO_MENU_BUTTON_ACTIVE.png");

        pauseImg[0][0] = loadImage("resources/PAUSE.png");
    }


    public BufferedImage getBlockImg(int index) {
        return blockImgs[index];
    }

    public BufferedImage[][] getMenuImages() {
        return menuImages;
    }

    public BufferedImage[] getGameBacks() {
        return gameBacks;
    }

    public BufferedImage[][] getSelectionMenuImages() {
        return selectionMenuImages;
    }

    public BufferedImage[] getMenuAnimationFrames() {
        return menuAnimationFrames;
    }

    public Font getFont() {
        return font;
    }

    public BufferedImage[][] getWinLoseImgs() {
        return winLoseImgs;
    }

    public BufferedImage[][] getPauseImg() {
        return pauseImg;
    }
}
