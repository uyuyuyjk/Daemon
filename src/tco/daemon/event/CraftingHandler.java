package tco.daemon.event;

import java.util.Random;

import net.minecraft.src.Enchantment;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import tco.daemon.ItemStaff;
import tco.daemon.ModDaemon;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.UtilItem;
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
		if(item.getItem() == ModDaemon.instance.crystal) {
			Random rand = new Random();
			DaemonEnergy de = UtilItem.getDaemonEnergy(item);
			de.deathEnergy = rand.nextInt(50);
			de.decayEnergy = rand.nextInt(50);
			de.diseaseEnergy = rand.nextInt(50);
			de.maxEnergy = de.deathEnergy + de.decayEnergy + de.diseaseEnergy;
			UtilItem.setDaemonEnergy(item, de);
		}
	}

}
