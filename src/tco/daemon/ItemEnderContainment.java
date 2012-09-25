package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryEnderChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemEnderContainment extends ItemDaemon {

	protected ItemEnderContainment(int id) {
		super(id);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		InventoryEnderChest inventory = player.getInventoryEnderChest();
		if(inventory != null && ModDaemon.proxy.isSimulating())
		{
			inventory.setAssociatedChest(new TileDaemonEnderChest());
			player.displayGUIChest(inventory);
		}
		return itemStack;
	}

}
