package tco.daemon.handlers;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import tco.daemon.ModDaemon;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.util.UtilItem;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {

	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		Random rand = new Random();
		if(item.getItem() == ModDaemon.instance.crystal) {
			DaemonEnergy de = UtilItem.getDaemonEnergy(item);
			de.deathEnergy = rand.nextInt(0x4F) - 8;
			de.decayEnergy = rand.nextInt(0x4F) - 8;
			de.diseaseEnergy = rand.nextInt(0x4F) - 8;
			de.maxEnergy = de.deathEnergy + de.decayEnergy + de.diseaseEnergy;
			UtilItem.setDaemonEnergy(item, de);
		}
	}

}
