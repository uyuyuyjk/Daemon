package tco.daemon;

import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityPlayer;
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
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(ModDaemon.proxy.isSimulating() && te instanceof TileEntityDaemonAltar) {
			if(player.inventory.consumeInventoryItem(ModDaemon.instance.shardDark.shiftedIndex)){
				((TileEntityDaemonAltar) te).challenge();
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);
		if(ModDaemon.proxy.isSimulating()) {
			world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
		}
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
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
