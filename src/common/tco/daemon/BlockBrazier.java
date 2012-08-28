package tco.daemon;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.List;
import java.util.Random;

import net.minecraft.src.*;

public class BlockBrazier extends Block {
	public BlockBrazier(int id) {
		super(id, Material.iron);
		setHardness(4.0F);
		setResistance(8.0F);
		setTextureFile(ReferenceConfigs.TEXTURE_BLOCKS);
		setTickRandomly(true);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return par1 == 1 ? 138 : (par1 == 0 ? 155 : 154);
	}

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
	
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public int getRenderType() {
		return ModDaemon.proxy.renderBrazierId;
	}

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
