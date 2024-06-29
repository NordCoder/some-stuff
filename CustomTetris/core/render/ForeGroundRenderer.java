package core.render;

import core.entity.TetrisBlock;
import core.entity.TetrisShape;

import java.awt.*;
import java.util.List;

import static core.utils.Constants.GameConstants.colorScheme;

public class ForeGroundRenderer {
    Renderer renderer;
    TetrisShape curShape;
    List<TetrisBlock> blocksOnGround;
    public ForeGroundRenderer(Renderer rend) {
        this.renderer = rend;
    }

    public void render(Graphics g) {
        curShape.draw(g, renderer.getTextureManager().
                getBlockImg(colorScheme.get(curShape.getType())));
        for (TetrisBlock b : blocksOnGround) {
            b.draw(g, renderer.getTextureManager().
                    getBlockImg(colorScheme.get(b.getType())));
        }
    }

    public void setCurShape(TetrisShape curShape) {
        this.curShape = curShape;
    }

    public void setBlocksOnGround(List<TetrisBlock> blocksOnGround) {
        this.blocksOnGround = blocksOnGround;
    }
}
