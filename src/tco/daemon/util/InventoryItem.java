package tco.daemon.util;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryItem extends InventoryBasic {

	private ItemStack item;

	public InventoryItem(String name, int slots, ItemStack itm) {
		super(name, slots);
		item = itm;
	}

	public InventoryItem(String name, IInventory inv, ItemStack itm){
		super(name, inv);
		item = itm;
	}

	@Override
	public void onInventoryChanged(){
		UtilItem.setInventory(item, this);
	}


}
