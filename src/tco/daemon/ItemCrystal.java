package tco.daemon;

import java.util.List;

import net.minecraft.src.ItemStack;
import tco.daemon.energy.IDaemonEnergyStorage;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.UtilItem;

public class ItemCrystal extends ItemDaemon implements IDaemonEnergyStorage {

	public ItemCrystal(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public int getColorFromDamage(int damage, int renderPass) {
		return damage;
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		DaemonEnergy de = UtilItem.getDaemonEnergy(itemStack);
		int color = de.deathEnergy << 16 + de.decayEnergy << 8 + de.diseaseEnergy;
		itemStack.setItemDamage(color);
		if(color == 0){
			list.add("Depleted");
			return;
		}
		list.add("Death: " + de.deathEnergy);
		list.add("Decay: " + de.decayEnergy);
		list.add("Disease: " + de.diseaseEnergy);
	}

	@Override
	public boolean getShareTag(){
		return true;
	}

}
