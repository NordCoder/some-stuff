package core.update;

import core.entity.TetrisBlock;
import core.entity.TetrisShape;

import java.awt.*;
import java.util.List;

import static core.utils.Constants.GameConstants.BLOCK_SIZE;
import static core.utils.Constants.WindowConstants.*;

public class CollisionHandler {
    private static int size = BLOCK_SIZE;
    private static int rlPadding = RIGHT_LEFT_PADDING;
    private static int udPadding = TOP_BOTTOM_PADDING;
    private static int gameSpaceWidth = GAME_ZONE_WIDTH;
    private static int gameSpaceHeight = GAME_ZONE_HEIGHT;

    public static boolean canMoveLR(TetrisShape shape, List<TetrisBlock> grounded, int dir) {
        for (Rectangle rect : shape.getRects()) {
            for (TetrisBlock b : grounded) {
                if (rect.x + size * dir == b.getPosX() && rect.y == b.getPosY()) {
                    return false;
                }
            }
            if (dir == 1) {
                if (rect.x + size == rlPadding + gameSpaceWidth) {
                    return false;
                }
            } else {
                if (rect.x + dir * size == rlPadding - size) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canMoveDown(TetrisShape shape, List<TetrisBlock> grounded, int size) {
        for (Rectangle rect : shape.getRects()) {
            for (TetrisBlock r : grounded) {
                if (rect.y + size == r.getPosY() && rect.x == r.getPosX()) {
                    return false;
                }
            }
            if (rect.y + size == udPadding + gameSpaceHeight) {
                return false;
            }
        }
        return true;
    }

    public static int isPositionValid(TetrisShape shape, List<TetrisBlock> grounded) {
        for (Rectangle rect : shape.getRects()) {
            if (rect.x >= rlPadding + gameSpaceWidth) {
                return 1;
            }
            if (rect.x < rlPadding) {
                return 2;
            }
            for (TetrisBlock b : grounded) {
                if (rect.x == b.getPosX() && rect.y == b.getPosY()) {
                    return 3;
                }
            }
        }
        return 0;
    }

}
