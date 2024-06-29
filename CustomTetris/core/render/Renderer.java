package core.render;

import core.Game;
import core.gamestage.Gamestate;
import core.utils.TextureManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class Renderer {
    private Game game;
    private TextureManager textureManager;
    private ForeGroundRenderer FGRenderer;
    private BackGroundRenderer BGRenderer;
    private MenuRenderer menuRenderer;
    private ModeSelectionRenderer modeSelectionRenderer;
    private WinLoseRenderer winLoseRenderer;
    private PauseRenderer pauseRenderer;
    private BufferedImage[] animationFrames;
    private int animationFTick = 6;
    private int animationTick = 0;
    private int animationIndex = 0;


    public Renderer(Game game) {
        this.game = game;
        this.textureManager = new TextureManager();
        this.FGRenderer = new ForeGroundRenderer(this);
        this.BGRenderer = new BackGroundRenderer(this);
        this.menuRenderer = new MenuRenderer(this);
        this.modeSelectionRenderer = new ModeSelectionRenderer(this);
        this.winLoseRenderer = new WinLoseRenderer(this);
        this.pauseRenderer = new PauseRenderer(this);
        init();
    }

    private void init() {
        this.animationFrames = textureManager.getMenuAnimationFrames();
    }

    public Game getGame() {
        return this.game;
    }

    public void render(Graphics g) {
        if (Gamestate.state == Gamestate.PLAYING) {
            BGRenderer.render(g);
            FGRenderer.render(g);
        } else if (Gamestate.state == Gamestate.START_MENU) {
            menuRenderer.render(g);
        } else if (Gamestate.state == Gamestate.MODE_SELECTION_MENU) {
            modeSelectionRenderer.render(g);
        } else if (Gamestate.state == Gamestate.WIN_LOSE) {
            winLoseRenderer.render(g);
        } else if (Gamestate.state == Gamestate.PAUSE) {
            pauseRenderer.render(g);
        }
    }

    public ForeGroundRenderer getFGRenderer() {
        return FGRenderer;
    }

    public BackGroundRenderer getBGRenderer() {
        return BGRenderer;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public void drawCurAnimation(Graphics g) {
        animationTick++;
        if (animationTick == animationFTick) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex == 96) {
                animationIndex = 0;
            }
        }
        g.drawImage(animationFrames[animationIndex], 0,0, INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT, null);
    }
}
