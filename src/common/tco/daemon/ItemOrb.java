package tco.daemon;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemOrb extends ItemDaemon {
	public static final int MAX_COND_FACTOR = 100;
	
	private int conductivity;
	
	protected ItemOrb(int id, int cond) {
		super(id);
		conductivity = cond;
		setMaxStackSize(1);
	}
	
	public void chargeOrb(ItemStack stack){
		DaemonEnergy de = DaemonEnergy.getDaemonEnergy(stack);
		de.maxEnergy += conductivity;
		if(de.maxEnergy > MAX_COND_FACTOR * conductivity){
			de.maxEnergy = MAX_COND_FACTOR * conductivity;
		}
		DaemonEnergy.setDaemonEnergy(stack, de);
	}
	
	public ItemStack mergeOrbs(ItemStack stack1, ItemStack stack2){
		DaemonEnergy de1 = DaemonEnergy.getDaemonEnergy(stack1);
		DaemonEnergy de2 = DaemonEnergy.getDaemonEnergy(stack2);
		ItemOrb orb2 = (ItemOrb)stack2.getItem();
		if(orb2.conductivity > conductivity){
			return mergeOrbs(stack2, stack1);
		}
		de1.maxEnergy += de2.maxEnergy;
		if(de1.maxEnergy > MAX_COND_FACTOR * conductivity){
			de1.maxEnergy = MAX_COND_FACTOR * conductivity;
		}
		DaemonEnergy.setDaemonEnergy(stack1, de1);
		return stack1;
	}
	
	@Override
	public void addInformation(ItemStack itemStack, List list) {
				
		DaemonEnergy de = DaemonEnergy.getDaemonEnergy(itemStack);

		if(de.maxEnergy == 0){
			list.add("Dormant");
			return;
		}
		
		list.add("Charge: " + de.getTotal() + "/" + de.maxEnergy);
		list.add("Death: " + de.death);
		list.add("Decay: " + de.decay);
		list.add("Disease: " + de.disease);
	}
	
	@Override
	public boolean getShareTag() {
		return true;
	}
	
}
