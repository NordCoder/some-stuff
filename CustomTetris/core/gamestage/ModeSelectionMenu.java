package core.gamestage;

import core.ui.SelectionButton;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static core.utils.Constants.MenuConstants.MENUBAR_INIT_X;
import static core.utils.Constants.MenuConstants.MENUBAR_INIT_Y;

public class ModeSelectionMenu extends State {
    List<SelectionButton> buttons = new ArrayList<>();
    public ModeSelectionMenu(StateManager stateManager) {
        super(stateManager);
        initButtons();
    }
    public void initButtons() {
        buttons.add(new SelectionButton(240,MENUBAR_INIT_Y + 100,
                240, 90, Gamestate.PLAYING, Gamemode.FORTY));
        buttons.add(new SelectionButton(720, MENUBAR_INIT_Y + 100,
                240, 90, Gamestate.PLAYING, Gamemode.INFINITY));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (SelectionButton button : buttons) {
            if (isIn(e, button)) {
                button.setMousePressed(true);
                stateManager.getGame().getUpdater().getGameUpdater().getTimer().start();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (SelectionButton button : buttons) {
            if (isIn(e, button)) {
                if (button.isMousePressed()) {
                    button.applyGamestate();
                    button.applyMode();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (SelectionButton mb : buttons) {
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (SelectionButton mb : buttons) {
            mb.setMouseOver(false);
        }
        for (SelectionButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
            }
        }
    }
    public List<SelectionButton> getButtons() {
        return buttons;
    }
}
