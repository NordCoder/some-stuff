package core.gamestage;

import core.entity.TetrisBlock;
import core.entity.TetrisShape;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static core.update.CollisionHandler.canMoveDown;
import static core.update.CollisionHandler.canMoveLR;
import static core.update.ProjectorHandler.calculateProjector;
import static core.utils.Constants.GameConstants.BLOCK_SIZE;

public class Playing extends State {
    TetrisShape curShape;
    List<TetrisBlock> blocksOnGround = new ArrayList<>();
    public Playing(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                if (canMoveDown(curShape, blocksOnGround, BLOCK_SIZE)) {
                    curShape.moveDown();
                    curShape.setProjector(calculateProjector(curShape, blocksOnGround));
                }
                break;
            case KeyEvent.VK_LEFT:
                if (canMoveLR(curShape, blocksOnGround, -1)) {
                    curShape.moveLeft();
                    curShape.setProjector(calculateProjector(curShape, blocksOnGround));
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (canMoveLR(curShape, blocksOnGround, 1)) {
                    curShape.moveRight();
                    curShape.setProjector(calculateProjector(curShape, blocksOnGround));
                }
                break;
            case KeyEvent.VK_UP:
                curShape.rotate();
                curShape.setProjector(calculateProjector(curShape, blocksOnGround));
                break;
            case KeyEvent.VK_SPACE:
                curShape.bruteDown();
                break;
            case KeyEvent.VK_ESCAPE:
                Gamestate.state = Gamestate.PAUSE;
                stateManager.getGame().getUpdater().getGameUpdater().getTimer().stop();
                break;
            case KeyEvent.VK_R:
                super.stateManager.getGame().getUpdater().getGameUpdater().restart();
        }
    }
    public TetrisShape getCurShape() {
        return curShape;
    }

    public List<TetrisBlock> getBlocksOnGround() {
        return blocksOnGround;
    }

    public void setCurShape(TetrisShape curShape) {
        this.curShape = curShape;
    }
}
