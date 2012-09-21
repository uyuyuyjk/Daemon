package tco.daemon.util;

import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.matrix.DaemonMatrix;

public class UtilItem {

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

}
