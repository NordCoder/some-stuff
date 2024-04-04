package core.update;

import core.ui.MenuButton;
import core.ui.SelectionButton;

import java.util.List;

public class ModeSelectionUpdater {
    Updater updater;
    private List<SelectionButton> buttons;
    public ModeSelectionUpdater(Updater updater) {
        this.updater = updater;
        init();
    }

    public void init() {
        this.buttons = updater.getGame().getStateManager().getModeSelectionMenu().getButtons();
    }

    public void update() {
        for (SelectionButton mb : buttons) {
            mb.update();
        }
    }
}
