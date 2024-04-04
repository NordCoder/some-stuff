package core.render;

import core.gamestage.Gamemode;
import core.ui.MenuButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class WinLoseRenderer {
    private Renderer renderer;
    private BufferedImage win;
    private BufferedImage lose;
    private List<MenuButton> buttons;

    public WinLoseRenderer(Renderer renderer) {
        this.renderer = renderer;
        init();
    }

    private void init() {
        this.win = renderer.getTextureManager().getWinLoseImgs()[0][0];
        this.lose = renderer.getTextureManager().getWinLoseImgs()[0][1];
        this.buttons = renderer.getGame().getStateManager().getWinLose().getButtons();
    }

    public void render(Graphics g) {
        renderer.drawCurAnimation(g);
        int s = renderer.getGame().getUpdater().getGameUpdater().getScore();
        g.setFont(renderer.getTextureManager().getFont().deriveFont(Font.TRUETYPE_FONT, 48));
        g.setColor(Color.WHITE);
        long[] times = renderer.getGame().getUpdater().getGameUpdater().getTimer().countTime();
        g.drawString("Time: " + times[0] + ":" + times[1] + ":" + times[2], 500, 350);
        g.drawString("SCORE: " + s, 500, 400);
        if (Gamemode.mode == Gamemode.INFINITY) {
            g.drawString("LINES: " + renderer.getGame().getUpdater().getGameUpdater().getLineCount(), 500, 450);
        }
        if (renderer.getGame().getUpdater().getGameUpdater().isWon()) {
            g.drawImage(win, 300, 150, 600, 100, null);
        } else {
            g.drawImage(lose, 250, 150, 700, 120, null);
        }
        buttons.get(0).render(g, renderer.getTextureManager().getWinLoseImgs()[1]);
    }
}
