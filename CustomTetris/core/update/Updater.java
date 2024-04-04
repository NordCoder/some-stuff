package core.update;

import core.Game;
import core.gamestage.Gamestate;
import core.gamestage.Pause;

public class Updater {
    private final Game game;
    private final GameUpdater gameUpdater;
    private final MenuUpdater menuUpdater;
    private final ModeSelectionUpdater modeSelectionUpdater;
    private final WinLoseUpdater winLoseUpdater;
    private final PauseUpdater pauseUpdater;

    public Updater(Game game) {
        this.game = game;
        this.gameUpdater = new GameUpdater(game);
        this.menuUpdater = new MenuUpdater(this);
        this.modeSelectionUpdater = new ModeSelectionUpdater(this);
        this.winLoseUpdater = new WinLoseUpdater(this);
        this.pauseUpdater = new PauseUpdater(this);
    }

    public void update() {
        if (Gamestate.state == Gamestate.PLAYING) {
            gameUpdater.update();
        } else if (Gamestate.state == Gamestate.START_MENU) {
            menuUpdater.update();
        } else if (Gamestate.state == Gamestate.MODE_SELECTION_MENU) {
            modeSelectionUpdater.update();
        } else if (Gamestate.state == Gamestate.WIN_LOSE) {
            winLoseUpdater.update();
        } else if (Gamestate.state == Gamestate.PAUSE) {
            pauseUpdater.update();
        }
    }
    public GameUpdater getGameUpdater() {
        return gameUpdater;
    }

    public Game getGame() {
        return game;
    }
}
