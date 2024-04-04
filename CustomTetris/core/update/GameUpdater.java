package core.update;

import core.Game;
import core.entity.TetrisBlock;
import core.entity.TetrisShape;
import core.gamestage.Gamestate;
import core.utils.CustomTimer;

import java.awt.*;
import java.util.*;
import java.util.List;

import static core.gamestage.Gamestate.WIN_LOSE;
import static core.update.CollisionHandler.canMoveDown;
import static core.update.CollisionHandler.isPositionValid;
import static core.update.ProjectorHandler.calculateProjector;
import static core.utils.Constants.GameConstants.BLOCK_SIZE;
import static java.util.Collections.shuffle;

public class GameUpdater {
    final private Game game;
    private TetrisShape curShape;
    private final List<TetrisBlock> blocksOnGround;
    private int tickToDown = 120;
    private int tick = 0;
    private int posInShapePack = 0;
    private List<Integer> shapePack = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
    private List<Rectangle> projector;
    private int score;
    private int lineCount;
    private boolean won;
    private CustomTimer timer;
    // TODO: 17.11.2023 let move after floor collision
    // TODO: 17.11.2023 proper permission to rotate check

    public GameUpdater(Game game) {
        this.game = game;
        shuffle(shapePack);
        blocksOnGround = game.getStateManager().getPlaying().getBlocksOnGround();
        generateNewShape();
        curShape = game.getStateManager().getPlaying().getCurShape();
        timer = new CustomTimer();
    }

    public void update() {

        checkWin();
        tick++;
        validatePosition();
        if (!canMoveDown(curShape, blocksOnGround, BLOCK_SIZE)) {
            resetCurShape();
        }
        if (tick >= tickToDown) {
            tick = 0;
            if (canMoveDown(curShape, blocksOnGround, BLOCK_SIZE)) {
                curShape.moveDown();
            }
        }
        curShape.update();
        checkAndDelete();
        game.getRenderer().getFGRenderer().setCurShape(curShape);
        game.getRenderer().getFGRenderer().setBlocksOnGround(blocksOnGround);
        checkLose();
    }

    private void checkAndDelete() {
        boolean flag = false;
        Map<Integer, Integer> placements = new HashMap<>();
        for (TetrisBlock rect : blocksOnGround) {
            if (!placements.containsKey(rect.getPosY())) {
                placements.put(rect.getPosY(), 1);
            } else {
                placements.put(rect.getPosY(), placements.get(rect.getPosY()) + 1);
            }
        }
        List<Integer> toDel = new ArrayList<>();
        placements.forEach((key, value) -> {
            if (value == 10) {
                toDel.add(key);
                score += 10;
                lineCount += 1;
            }
        });
        blocksOnGround.removeIf(r -> toDel.contains(r.getPosY()));
        List<TetrisBlock> toMoveDown = new ArrayList<>();
        for (TetrisBlock r : blocksOnGround) {
            for (Integer n : toDel) {
                if (r.getPosY() <= n) {
                    toMoveDown.add(r);
                }
            }
        }
        for (TetrisBlock r : toMoveDown) {
            r.moveDown();
            flag = true;
        }
        if (flag) {
            recalculateProjector();
        }
    }

    private void resetCurShape() {
        for (Rectangle r : curShape.getRects()) {
            TetrisBlock b = new TetrisBlock(r.x, r.y, r.height, curShape.getType());
            blocksOnGround.add(b);
        }
        generateNewShape();
    }

    private void generateNewShape() {
        if (posInShapePack == 7) {
            shuffle(shapePack);
            posInShapePack = 0;
        }
        game.getStateManager().getPlaying().setCurShape(new TetrisShape(shapePack.get(posInShapePack)));
        curShape = game.getStateManager().getPlaying().getCurShape();
        recalculateProjector();
        posInShapePack++;
    }

    private void validatePosition() {
        int command = isPositionValid(curShape, blocksOnGround);
        switch (command) {
            case 0:
                break;
            case 1:
                curShape.moveLeft();
                recalculateProjector();
                break;
            case 2:
                curShape.moveRight();
                recalculateProjector();
                break;
            case 3:
                curShape.moveUp();
                recalculateProjector();
                break;
        }
    }
    private void recalculateProjector() {
        projector = calculateProjector(curShape, blocksOnGround);
        curShape.setProjector(projector);
    }

    private void checkLose() {
        for (TetrisBlock b : blocksOnGround) {
            if (b.getPosY() < 90) {
                won = false;
                timer.stop();
                Gamestate.state = WIN_LOSE;
            }
        }
    }

    private void checkWin() {
        if (lineCount >= 40) {
            won = true;
            timer.stop();
            Gamestate.state = WIN_LOSE;
        }
    }

    public int getScore() {
        return score;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void restart() {
        score = 0;
        lineCount = 0;
        blocksOnGround.clear();
        generateNewShape();
        timer.start();
    }

    public boolean isWon() {
        return won;
    }

    public CustomTimer getTimer() {
        return timer;
    }
}
