package tco.daemon;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceConfigs;

public class BlockDaemonAltar extends BlockContainer {

	public BlockDaemonAltar(int id) {
		super(id, Material.rock);
		setTextureFile(ReferenceConfigs.TEXTURE_BLOCKS);
		setHardness(20.0F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityDaemonAltar();
	}

}
