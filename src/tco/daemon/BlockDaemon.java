package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import tco.daemon.util.ReferenceConfigs;

public class BlockDaemon extends Block {
	public BlockDaemon(int id, Material material) {
		super(id, material);
		setTextureFile(ReferenceConfigs.TEXTURE_BLOCKS);
		setCreativeTab(CreativeTabs.tabMisc);
	}
}
