package tco.daemon.event;

import net.minecraft.src.Enchantment;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import tco.daemon.ItemStaff;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		if(item.getItem() instanceof ItemStaff){
			item.addEnchantment(Enchantment.knockback, 1);
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {

	}

}
