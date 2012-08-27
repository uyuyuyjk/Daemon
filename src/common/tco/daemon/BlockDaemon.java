package tco.daemon;

import java.util.List;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockDaemon extends BlockContainer {

	protected BlockDaemon(int id) {
		super(id, Material.wood);
		setTextureFile("/tco/daemon/sprites/blocks.png");
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundWoodFootstep);
		setBlockName("blockDaemon");
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
				&& player.getCurrentEquippedItem().getItem() instanceof ItemDaemon) {
			ModDaemon.proxy.openGui(ReferenceGui.MATRIX, player, world, x, y, z);
		} else {
			ModDaemon.proxy.openGui(world.getBlockMetadata(x, y, z), player, world, x, y, z);
		}
		return true;
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	private void dropItems(World world, int x, int y, int z){
		Random rand = new Random();

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world,
						x + rx, y + ry, z + rz,
						new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.item.setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch (metadata) {
		case ReferenceGui.MATRIX:
			return new TileEntityDaemon();
		case ReferenceGui.FEEDER:
			return new TileEntityFeeder();
		case ReferenceGui.CHEST:
			return new TileEntityHungerChest();
		default:
			return null;
		}
	}
		
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		// crafting = 43
		switch (side) {
		case 0: // bottom
			return 2 * 16 + 5;
		case 1: // top
			switch (metadata) {
			case 1:
				return 16 + 9;
			case 2:
				return 16 + 8;
			default:
				return 3 * 16;
			}
		case 2: // f:2 -z north
			return 2 * 16 + 3;
		case 3: // f:0 +z south
			return 2 * 16 + 3;
		case 4: // f:1 -x west
			return 2 * 16 + 3;
		case 5: // f:3 +x east
			return 2 * 16 + 3;
		default:
			return 3 * 16;
		}
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		for (int i = 0; i < 3; i++) {
			double vx = 0, vy = 0, vz = 0;
			float fx = rand.nextFloat() - 0.5f;
			float fz = rand.nextFloat() - 0.5f;

			vx = rand.nextFloat() - 0.5;
			vy = rand.nextFloat() * 0.5;
			vz = rand.nextFloat() - 0.5;
			
			vx = rand.nextFloat() * fx;
			vz = rand.nextFloat() * fz;

			double dx = x + 0.5 + 2 * fx;
			double dy = y + 2 * rand.nextFloat();
			double dz = z + 0.5 + 2 * fz;

			world.spawnParticle("reddust", dx, dy, dz, vx, vy, vz);
		}
	}

	public void getSubBlocks(int id, CreativeTabs creativeTabs, List list) {
		for(int i = 0; createNewTileEntity(null, i) != null; i++){
			list.add(new ItemStack(id, 1, i));
		}
	}
}
