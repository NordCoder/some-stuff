package core.update;

import core.ui.MenuButton;

import java.util.List;

public class MenuUpdater {
    Updater updater;
    private List<MenuButton> buttons;
    public MenuUpdater(Updater updater) {
        this.updater = updater;
        init();
    }

    public void init() {
        this.buttons = updater.getGame().getStateManager().getMenu().getButtons();
    }

    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }
}
