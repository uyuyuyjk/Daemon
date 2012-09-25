package tco.daemon.util;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import tco.daemon.ModDaemon;

public class InventoryItem extends InventoryBasic {

	private IInventory inventory;
	private ItemStack item;

	public InventoryItem(String name, int slots, IInventory inv, ItemStack itm) {
		super(name, slots);
		inventory = inv;
		item = itm;
	}

	public InventoryItem(String name, IInventory inv, ItemStack itm){
		super(name, inv);
		item = itm;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		if(!ModDaemon.proxy.isSimulating() || ModDaemon.proxy.isSimulating() && isValidInventory()) {
			super.setInventorySlotContents(i, stack);
		}
	}

	public boolean isValidInventory() {
		ItemStack container = UtilItem.getUniqueItem(item, inventory);
		return container != null;
	}

	@Override
	public void onInventoryChanged(){
		if(isValidInventory()) {
			UtilItem.setInventory(UtilItem.getUniqueItem(item, inventory), this);
		}
	}


}
