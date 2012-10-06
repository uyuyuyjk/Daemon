package tco.daemon.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryEnderChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.ModDaemon;
import tco.daemon.TileDaemonEnderChest;
import tco.daemon.util.InventoryMerged;
import tco.daemon.util.UtilItem;

public class ItemEnderContainment extends ItemDaemon {

	public ItemEnderContainment(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		InventoryEnderChest enderChest = player.getInventoryEnderChest();
		IInventory inventory = enderChest;

		ItemStack extension = inventory.getStackInSlot(0);
		if(extension != null && extension.itemID == ModDaemon.instance.enderExtension.shiftedIndex) {
			UtilItem.setUniqueItem(extension, player);
			IInventory containedInventory =
					UtilItem.getInventory(player, extension, enderChest, enderChest.getSizeInventory());
			inventory = new InventoryMerged(inventory, containedInventory);
		}

		if(inventory != null && ModDaemon.proxy.isSimulating())
		{
			enderChest.setAssociatedChest(new TileDaemonEnderChest());
			player.displayGUIChest(inventory);
		}
		return itemStack;
	}

}
