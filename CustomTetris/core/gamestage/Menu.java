package core.gamestage;

import core.ui.MenuButton;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static core.utils.Constants.MenuConstants.MENUBAR_INIT_X;
import static core.utils.Constants.MenuConstants.MENUBAR_INIT_Y;

public class Menu extends State {
    List<MenuButton> buttons = new ArrayList<>();
    public Menu(StateManager stateManager) {
        super(stateManager);
        initButtons();
    }

    private void initButtons() {
        buttons.add(new MenuButton(MENUBAR_INIT_X + 230,MENUBAR_INIT_Y + 100,
                240, 80, Gamestate.MODE_SELECTION_MENU));
    }


    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isIn(e, button)) {
                button.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isIn(e, button)) {
                if (button.isMousePressed()) {
                    button.applyGamestate();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Gamestate.state = Gamestate.PLAYING;
        }
    }
    public List<MenuButton> getButtons() {
        return buttons;
    }
}
