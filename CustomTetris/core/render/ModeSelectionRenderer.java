package core.render;

import core.ui.MenuButton;
import core.ui.SelectionButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class ModeSelectionRenderer {
    Renderer renderer;
    private BufferedImage backGround;
    private List<SelectionButton> buttons;
    private BufferedImage[] animationSprites;
    private BufferedImage title;
    private int animationFTick = 6;
    private int animationTick = 0;
    private int animationIndex = 0;
    public ModeSelectionRenderer(Renderer renderer) {
        this.renderer = renderer;
        this.buttons = renderer.getGame().getStateManager().getModeSelectionMenu().getButtons();
        init();
    }

    private void init() {
        this.backGround = renderer.getTextureManager().getSelectionMenuImages()[0][0];
        this.animationSprites = renderer.getTextureManager().getMenuAnimationFrames();
        this.title = renderer.getTextureManager().getSelectionMenuImages()[0][0];
    }

    public void render(Graphics g) {
        renderer.drawCurAnimation(g);
        buttons.get(0).render(g, renderer.getTextureManager().getSelectionMenuImages()[1]);
        buttons.get(1).render(g, renderer.getTextureManager().getSelectionMenuImages()[2]);
        g.drawImage(title, 150, 100, 900, 100, null);
    }

}
