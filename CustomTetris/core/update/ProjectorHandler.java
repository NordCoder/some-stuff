package core.update;

import core.entity.TetrisBlock;
import core.entity.TetrisShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static core.utils.Constants.GameConstants.BLOCK_SIZE;
import static core.utils.Constants.WindowConstants.GAME_ZONE_HEIGHT;
import static core.utils.Constants.WindowConstants.TOP_BOTTOM_PADDING;

public class ProjectorHandler {
    private static final int udPadding = TOP_BOTTOM_PADDING;
    private static final int gameSpaceBottom = udPadding + GAME_ZONE_HEIGHT;
    private static final int size = BLOCK_SIZE;
    public static List<Rectangle> calculateProjector(TetrisShape shape, List<TetrisBlock> grounded) {
        List<Rectangle> projector = new ArrayList<>();
        List<Integer> Ys = new ArrayList<>();
        List<Integer> Xs = new ArrayList<>();
        for (Rectangle rect : shape.getRects()) {
            Ys.add(rect.y);
            Xs.add(rect.x);
        }
        while (Ys.get(0) < gameSpaceBottom) {
            for (int i = 0; i < Ys.size(); i++) {
                if (Ys.get(i) + size == gameSpaceBottom) {
                    for (int j = 0; j < Ys.size(); j++) {
                        Rectangle rect = new Rectangle(Xs.get(j), Ys.get(j),
                                size, size);
                        projector.add(rect);
                    }
                    return projector;
                }
                for (TetrisBlock r : grounded) {
                    if ((Ys.get(i) + size == r.getPosY() && Xs.get(i) == r.getPosX())
                            || Ys.get(i) + size == gameSpaceBottom) {
                        for (int j = 0; j < Ys.size(); j++) {
                            Rectangle rect = new Rectangle(Xs.get(j), Ys.get(j),
                                    size, size);
                            projector.add(rect);
                        }
                        return projector;
                    }
                }
            }
            Ys.replaceAll(integer -> integer + size);
        }
        return projector;
    }
}
