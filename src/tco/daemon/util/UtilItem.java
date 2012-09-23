package tco.daemon.util;

import java.util.Random;

import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.matrix.DaemonMatrix;

public class UtilItem {

	private static final Random rand = new Random();

	public static boolean checkTagCompound(ItemStack itemStack){
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
			return true;
		}
		return false;
	}

	//DaemonEnergy
	public static DaemonEnergy getDaemonEnergy(ItemStack itemStack){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		return DaemonEnergy.readFromNBT(tagCompound);
	}
	public static void setDaemonEnergy(ItemStack itemStack, DaemonEnergy de){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		de.writetoNBT(tagCompound);
	}

	//InventoryItem
	public static IInventory getInventory(ItemStack stack){
		checkTagCompound(stack);
		InventoryItem inv = new InventoryItem("inventory", DaemonMatrix.MATRIX_SIZE, stack);
		inv.getFromNBT(stack.getTagCompound());
		return inv;
	}

	public static void setInventory(ItemStack stack, InventoryBasic inventory){
		checkTagCompound(stack);
		inventory.saveToNBT(stack.getTagCompound());
	}

	//assumes items exist
	//attempts to leave stacks with at least 1 item
	/**
	 * 
	 * @param side
	 * @param item
	 * @param amount amount of the item to remove from the inventory
	 */
	public static void removeItems(IInventory inv, Item item, int start, int size, int amount){
		for(int i = 0; i < size && amount > 0; i++){
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null && stack.getItem() == item){
				int amt = stack.stackSize - 1; //maximum amt it can take from the stack
				if(amount < amt){
					amt = amount;
				}
				if(amt > 0){
					amount -= inv.decrStackSize(i, amt).stackSize;
				}
			}
		}
		for (int i = 0; i < size && amount > 0; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() == item) {
				amount -= inv.decrStackSize(i, amount).stackSize;
			}
		}
	}

	public static void removeItems(IInventory inv, Item item, int amount){
		removeItems(inv, item, 0, inv.getSizeInventory(), amount);
	}

	public static void removeItems(ISidedInventory inv, Item item, int amount){
		removeItems(inv, item,
				inv.getStartInventorySide(ForgeDirection.DOWN),
				inv.getSizeInventorySide(ForgeDirection.DOWN),
				amount);
	}

	public static void dropItems(World world, IInventory inventory, int start, int end, int x, int y, int z) {
		for (int i = start; i < end; i++) {
			ItemStack item = inventory.getStackInSlot(i);
			dropItem(world, item, x, y, z);
		}
	}

	public static void dropItems(World world, IInventory inventory, int x, int y, int z) {
		dropItems(world, inventory, 0, inventory.getSizeInventory(), x, y, z);
	}

	public static void dropItem(World world, ItemStack item, int x, int y, int z){
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
