package tco.daemon.util;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

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
}
