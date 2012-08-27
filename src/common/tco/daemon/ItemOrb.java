package tco.daemon;

import java.util.List;

import net.minecraft.src.EnumRarity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemOrb extends ItemDaemon {
	
	protected ItemOrb(int id) {
		super(id);
		setMaxStackSize(1);
	}
	
	public void checkTagCompound(ItemStack itemStack){
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}
	
	public void addInformation(ItemStack itemStack, List list) {
		checkTagCompound(itemStack);
		
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		
		DaemonEnergy de = DaemonEnergy.readFromNBT(tagCompound);

		if(de.maxEnergy == 0){
			list.add("Dormant");
			return;
		}
		
		list.add("Charge: " + de.getTotal() + "/" + de.maxEnergy);
		list.add("Death: " + de.death);
		list.add("Decay: " + de.decay);
		list.add("Disease: " + de.disease);
	}
	
	public DaemonEnergy getDaemonEnergy(ItemStack itemStack){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		return DaemonEnergy.readFromNBT(tagCompound);
	}
	
	public void setDaemonEnergy(ItemStack itemStack, DaemonEnergy de){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		de.writetoNBT(tagCompound);
	}
	
	public boolean getShareTag() {
		return true;
	}
	
}
