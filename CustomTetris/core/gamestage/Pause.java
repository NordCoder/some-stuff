package core.gamestage;

import java.awt.event.KeyEvent;

public class Pause extends State {
    public Pause(StateManager stateManager){
        super(stateManager);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                Gamestate.state = Gamestate.PLAYING;
                stateManager.getGame().getUpdater().getGameUpdater().getTimer().resume();
        }
    }

}
