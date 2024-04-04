package core.gamestage;

import core.Game;

public class StateManager {
    protected Game game;
    private Menu menu;
    private ModeSelectionMenu modeSelectionMenu;
    private Playing playing;
    private WinLose winLose;
    private Pause pause;
    public StateManager(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        this.menu = new Menu(this);
        this.playing = new Playing(this);
        this.modeSelectionMenu = new ModeSelectionMenu(this);
        this.winLose = new WinLose(this);
        this.pause = new Pause(this);
    }

    public Menu getMenu() {
        return menu;
    }

    public ModeSelectionMenu getModeSelectionMenu() {
        return modeSelectionMenu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Game getGame() {
        return game;
    }

    public WinLose getWinLose() {
        return winLose;
    }

    public Pause getPause() {
        return pause;
    }
}
