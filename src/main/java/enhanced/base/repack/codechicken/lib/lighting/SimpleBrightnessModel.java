package enhanced.base.repack.codechicken.lib.lighting;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import enhanced.base.repack.codechicken.lib.render.CCRenderState;
import enhanced.base.repack.codechicken.lib.vec.BlockCoord;

/**
 * Faster precomputed version of LightModel that only works for axis planar sides
 */
public class SimpleBrightnessModel implements CCRenderState.IVertexOperation
{
    public static final int operationIndex = CCRenderState.registerOperation();
    public static SimpleBrightnessModel instance = new SimpleBrightnessModel();

    public IBlockAccess access;
    public BlockCoord pos = new BlockCoord();

    private int sampled = 0;
    private int[] samples = new int[6];
    private BlockCoord c = new BlockCoord();

    public void locate(IBlockAccess a, int x, int y, int z) {
        access = a;
        pos.set(x, y, z);
        sampled = 0;
    }

    public int sample(int side) {
        if ((sampled & 1 << side) == 0) {
            c.set(pos).offset(side);
            Block block = access.getBlock(c.x, c.y, c.z);
            samples[side] = access.getLightBrightnessForSkyBlocks(c.x, c.y, c.z, block.getLightValue(access, c.x, c.y, c.z));
            sampled |= 1 << side;
        }
        return samples[side];
    }

    @Override
    public boolean load() {
        CCRenderState.pipeline.addDependency(CCRenderState.sideAttrib);
        return true;
    }

    @Override
    public void operate() {
        CCRenderState.setBrightness(sample(CCRenderState.side));
    }

    @Override
    public int operationID() {
        return operationIndex;
    }
}
