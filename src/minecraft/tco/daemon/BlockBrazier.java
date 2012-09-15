package tco.daemon;

import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceConfigs;

public class BlockBrazier extends Block {
	public BlockBrazier(int id) {
		super(id, Material.iron);
		setHardness(4.0F);
		setResistance(8.0F);
		setTextureFile(ReferenceConfigs.TEXTURE_BLOCKS);
		setTickRandomly(true);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return par1 == 1 ? 138 : (par1 == 0 ? 155 : 154);
	}

	@Override
	public void addCollidingBlockToList(World world, int x, int y, int z,
			AxisAlignedBB aabb, List list, Entity entity) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entity);
		float var8 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entity);
		setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entity);
		setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entity);
		setBlockBoundsForItemRender();
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return ModDaemon.proxy.renderBrazierId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
			return false;
	}

	@Override
    public int idDropped(int id, Random rand, int par3){
		return ModDaemon.instance.daemonBrazier.shiftedIndex;
	}
}
