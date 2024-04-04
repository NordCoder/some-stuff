package core.render;

import core.ui.MenuButton;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static core.utils.Constants.MenuConstants.*;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class MenuRenderer {
    private Renderer renderer;
    private BufferedImage titleImg;
    private BufferedImage menuBackGroundImage;
    private List<MenuButton> buttons;
    private BufferedImage[] animationSprites;
    private int animationFTick = 6;
    private int animationTick = 0;
    private int animationIndex = 0;

    public MenuRenderer(Renderer renderer) {
        this.renderer = renderer;
        init();
    }

    private void init() {
        this.titleImg = renderer.getTextureManager().getMenuImages()[0][0];
        this.menuBackGroundImage = renderer.getTextureManager().getMenuImages()[0][1];
        this.buttons = renderer.getGame().getStateManager().getMenu().getButtons();
        this.animationSprites = renderer.getTextureManager().getMenuAnimationFrames();
    }

    public void render(Graphics g) {
        renderer.drawCurAnimation(g);
        g.drawImage(titleImg, MENUBAR_INIT_X, 80, TITLE_WIDTH, 150, null);
        for (MenuButton mb : buttons) {
            mb.render(g, renderer.getTextureManager().getMenuImages()[1]);
        }
    }
}
