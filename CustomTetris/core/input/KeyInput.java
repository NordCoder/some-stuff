package core.input;

import core.Game;
import core.gamestage.Gamestate;
import core.windows.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    Game game;
    public KeyInput(Game game) {
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case START_MENU:
                game.getStateManager().getMenu().keyPressed(e);
                break;
            case PLAYING:
                game.getStateManager().getPlaying().keyPressed(e);
                break;
            case PAUSE:
                game.getStateManager().getPause().keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
