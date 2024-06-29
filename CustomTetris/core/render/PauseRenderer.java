package core.render;

import java.awt.*;

import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class PauseRenderer {
    private Renderer renderer;

    public PauseRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void render(Graphics g) {
        renderer.getBGRenderer().render(g);
        renderer.getFGRenderer().render(g);
        g.setColor(new Color(0,0,0, 120));
        g.fillRect(0, 0, INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT);
        g.drawImage(renderer.getTextureManager().getPauseImg()[0][0], 350, 300, 500, 125, null);
    }
}
