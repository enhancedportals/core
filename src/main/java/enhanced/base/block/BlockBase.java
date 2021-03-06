package enhanced.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block {
    public BlockBase(String mod, String name, Material material, CreativeTabs tab, float hard) {
        super(material);
        setBlockName(name);
        setBlockTextureName(mod + ":" + name);
        setStepSound(soundTypeStone);
        setHardness(hard);
        
        if (tab != null)
            setCreativeTab(tab);
    }
}
