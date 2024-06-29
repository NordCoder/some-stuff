package core.input;

import core.Game;
import core.gamestage.Gamestate;
import core.windows.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {
    Game game;

    public MouseInput(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case START_MENU:
                game.getStateManager().getMenu().mousePressed(e);
                break;
            case PLAYING:
                game.getStateManager().getPlaying().mousePressed(e);
                break;
            case MODE_SELECTION_MENU:
                game.getStateManager().getModeSelectionMenu().mousePressed(e);
                break;
            case WIN_LOSE:
                game.getStateManager().getWinLose().mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case START_MENU:
                game.getStateManager().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                game.getStateManager().getPlaying().mouseReleased(e);
                break;
            case MODE_SELECTION_MENU:
                game.getStateManager().getModeSelectionMenu().mouseReleased(e);
                break;
            case WIN_LOSE:
                game.getStateManager().getWinLose().mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case START_MENU:
                game.getStateManager().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                game.getStateManager().getPlaying().mouseMoved(e);
                break;
            case MODE_SELECTION_MENU:
                game.getStateManager().getModeSelectionMenu().mouseMoved(e);
                break;
            case WIN_LOSE:
                game.getStateManager().getWinLose().mouseMoved(e);
                break;
        }
    }
}
