package core.update;

import core.ui.MenuButton;

import java.util.List;

public class WinLoseUpdater {
    Updater updater;
    private List<MenuButton> buttons;

    public WinLoseUpdater(Updater updater) {
        this.updater = updater;
        init();
    }

    public void init() {
        this.buttons = updater.getGame().getStateManager().getWinLose().getButtons();
    }

    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }
}
