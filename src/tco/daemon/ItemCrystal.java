package tco.daemon;

import java.util.List;

import net.minecraft.src.ItemStack;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.IDaemonEnergyStorage;
import tco.daemon.util.UtilItem;

public class ItemCrystal extends ItemDaemon implements IDaemonEnergyStorage {

	public ItemCrystal(int id) {
		super(id);
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		DaemonEnergy de = UtilItem.getDaemonEnergy(itemStack);
		list.add("Death: " + de.deathEnergy);
		list.add("Decay: " + de.decayEnergy);
		list.add("Disease: " + de.diseaseEnergy);
	}

}
