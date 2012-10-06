package tco.daemon.item;

import java.util.List;

import net.minecraft.src.ItemStack;
import tco.daemon.util.UtilItem;
import tco.daemon.util.energy.DaemonEnergy;
import tco.daemon.util.energy.IDaemonEnergyStorage;
import tco.daemon.util.matrix.IMatrixAction;
import tco.daemon.util.matrix.IMatrixActivator;
import tco.daemon.util.matrix.MatrixActionCraft;

public class ItemOrb extends ItemDaemon implements IDaemonEnergyStorage, IMatrixActivator {
	public static final int MAX_COND_FACTOR = 100;

	private int conductivity;

	public ItemOrb(int id, int cond) {
		super(id);
		conductivity = cond;
		setMaxStackSize(1);
	}

	public void chargeOrb(ItemStack stack){
		DaemonEnergy de = UtilItem.getDaemonEnergy(stack);
		de.maxEnergy += conductivity;
		if(de.maxEnergy > MAX_COND_FACTOR * conductivity){
			de.maxEnergy = MAX_COND_FACTOR * conductivity;
		}
		UtilItem.setDaemonEnergy(stack, de);
	}

	public ItemStack mergeOrbs(ItemStack stack1, ItemStack stack2){
		DaemonEnergy de1 = UtilItem.getDaemonEnergy(stack1);
		DaemonEnergy de2 = UtilItem.getDaemonEnergy(stack2);
		ItemOrb orb2 = (ItemOrb)stack2.getItem();
		if(orb2.conductivity > conductivity){
			return mergeOrbs(stack2, stack1);
		}
		de1.maxEnergy += de2.maxEnergy;
		if(de1.maxEnergy > MAX_COND_FACTOR * conductivity){
			de1.maxEnergy = MAX_COND_FACTOR * conductivity;
		}
		UtilItem.setDaemonEnergy(stack1, de1);
		return stack1;
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {

		DaemonEnergy de = UtilItem.getDaemonEnergy(itemStack);

		if(de.maxEnergy == 0){
			list.add("Dormant");
			return;
		}

		list.add("Charge: " + de.getTotal() + "/" + de.maxEnergy);
		list.add("Death: " + de.deathEnergy);
		list.add("Decay: " + de.decayEnergy);
		list.add("Disease: " + de.diseaseEnergy);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public IMatrixAction getAction() {
		return new MatrixActionCraft();
	}

	@Override
	public int getActivatorId() {
		return shiftedIndex;
	}

}
