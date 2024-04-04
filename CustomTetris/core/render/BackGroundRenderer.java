package core.render;

import java.awt.*;
import java.awt.image.BufferedImage;

import static core.utils.Constants.WindowConstants.*;

public class BackGroundRenderer {
    Renderer renderer;
    private final int rlPadding = RIGHT_LEFT_PADDING;
    private final int udPadding = TOP_BOTTOM_PADDING;
    private final int gameSpaceWidth = GAME_ZONE_WIDTH;
    private final int gameSpaceHeight = GAME_ZONE_HEIGHT;
    private BufferedImage backGround;


    public BackGroundRenderer(Renderer rend) {
        renderer = rend;
        uploadBackground();
    }
    private void uploadBackground() {
        this.backGround = renderer.getTextureManager().getGameBacks()[0];
    }

    public void render(Graphics g) {
        //g.drawImage(backGround, 0,0, INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT, null);
        renderer.drawCurAnimation(g);
        g.setFont(renderer.getTextureManager().getFont());
        g.setColor(Color.WHITE);
        long[] times = renderer.getGame().getUpdater().getGameUpdater().getTimer().countTime();
        g.drawString("Time: " + times[0] + ":" + times[1] + ":" + times[2], 10, 100);
        g.drawString("Score: " + renderer.getGame().getUpdater()
                .getGameUpdater().getScore(), 10, 130);
        g.drawString("Lines: " + renderer.getGame().getUpdater().getGameUpdater().getLineCount(), 10, 160);
        g.setColor(Color.BLACK);
        g.fillRect(rlPadding, udPadding, gameSpaceWidth, gameSpaceHeight);
        g.setColor(Color.WHITE);
        g.drawLine(rlPadding, udPadding, rlPadding + gameSpaceWidth, udPadding);
        g.drawLine(rlPadding, udPadding, rlPadding, udPadding + gameSpaceHeight);
        g.drawLine(rlPadding, udPadding + gameSpaceHeight, rlPadding
                + gameSpaceWidth, udPadding + gameSpaceHeight);
        g.drawLine(rlPadding + gameSpaceWidth, udPadding, rlPadding
                + gameSpaceWidth, udPadding + gameSpaceHeight);
        g.drawLine(rlPadding, udPadding + 90, rlPadding
                + gameSpaceWidth, udPadding + 90);
    }
}
