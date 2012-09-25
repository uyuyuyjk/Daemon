package tco.daemon.util;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryItem extends InventoryBasic {

	private EntityPlayer owner;
	private ItemStack item;

	public InventoryItem(String name, int slots, EntityPlayer player, ItemStack itm) {
		super(name, slots);
		owner = player;
		item = itm;
	}

	public InventoryItem(String name, IInventory inv, ItemStack itm){
		super(name, inv);
		item = itm;
	}

	@Override
	public void onInventoryChanged(){
		UtilItem.setInventory(UtilItem.getUniqueItem(item, owner.inventory), this);
	}


}
