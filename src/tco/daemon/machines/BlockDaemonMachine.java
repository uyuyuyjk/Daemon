package tco.daemon.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import tco.daemon.ModDaemon;
import tco.daemon.item.ItemStaff;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.ReferenceTiles;
import tco.daemon.util.UtilItem;

public class BlockDaemonMachine extends BlockContainer {

	public BlockDaemonMachine(int id) {
		super(id, Material.rock);
		setTextureFile(ReferenceConfigs.TEXTURE_BLOCKS);
		setHardness(2.0F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(CreativeTabs.tabMisc);
		setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		if (player.getCurrentEquippedItem() != null
				&& player.getCurrentEquippedItem().getItem() instanceof ItemStaff) {
			player.openGui(ModDaemon.instance, ReferenceTiles.MATRIX.ordinal(), world, x, y, z);
		} else {
			player.openGui(ModDaemon.instance, world.getBlockMetadata(x, y, z), world, x, y, z);
		}
		return true;
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof IInventory) {
			UtilItem.dropItems(world, (IInventory) te, x, y, z);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return ReferenceTiles.values()[metadata].getTileEntity();
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		// crafting = 43
		switch (side) {
		case 1: // top
			return 0;
		case 2: // f:2 -z north
		case 3: // f:0 +z south
		case 4: // f:1 -x west
		case 5: // f:3 +x east
			return 1;
		}

		return 0;  // 0 = bottom
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		for (int i = 0; i < 2; i++) {
			float fx = rand.nextFloat() - 0.5f;
			float fz = rand.nextFloat() - 0.5f;

			double dx = x + 0.5 + fx;
			double dy = y + 2 * rand.nextFloat();
			double dz = z + 0.5 + fz;

			world.spawnParticle("reddust", dx, dy, dz, 0, 0, 0);
		}
	}

	@Override
	public void getSubBlocks(int id, CreativeTabs creativeTabs, List list) {
		int values = ReferenceTiles.values().length;
		for(int i = 0; i < values; i++){
			list.add(new ItemStack(id, 1, i));
		}
	}
}
