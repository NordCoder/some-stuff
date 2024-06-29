package core.gamestage;

import core.Game;
import core.ui.Button;
import core.ui.MenuButton;
import core.ui.SelectionButton;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class State implements StateMethods {
    StateManager stateManager;

    public State(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public boolean isIn(MouseEvent e, Button mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
