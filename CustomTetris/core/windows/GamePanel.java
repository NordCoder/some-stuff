package core.windows;

import core.Game;
import core.input.KeyInput;
import core.input.MouseInput;

import javax.swing.*;
import java.awt.*;

import static core.utils.Constants.WindowConstants.INIT_WINDOW_HEIGHT;
import static core.utils.Constants.WindowConstants.INIT_WINDOW_WIDTH;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        MouseInput mouseInput = new MouseInput(game);
        KeyInput keyInput = new KeyInput(game);
        addKeyListener(keyInput);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        setPanelSize();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
