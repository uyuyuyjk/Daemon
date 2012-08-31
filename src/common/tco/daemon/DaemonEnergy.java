package tco.daemon;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class DaemonEnergy {
	public int death, decay, disease;
	public int maxEnergy;
	
	public DaemonEnergy(){
		this(0, 0, 0, 0);
	}
	
	public DaemonEnergy(int dea, int dec, int dis, int max){
		death = dea;
		decay = dec;
		disease = dis;
		maxEnergy = max;
	}
		
	public int getTotal(){
		return death + decay + disease;
	}
	
	//
	//NBT methods
	//
	
	public void writetoNBT(NBTTagCompound tag){
		writetoNBT(this, tag);
	}
	
	public static DaemonEnergy readFromNBT(NBTTagCompound tag){
		NBTTagCompound energyList = tag.getCompoundTag("DaemonEnergy");
		DaemonEnergy de = new DaemonEnergy(
				energyList.getInteger("Death"),
				energyList.getInteger("Decay"),
				energyList.getInteger("Disease"),
				energyList.getInteger("Max")
				);
		return de;
	}
	
	public static void writetoNBT(DaemonEnergy de, NBTTagCompound tag){
		NBTTagCompound energyList = new NBTTagCompound();
		energyList.setInteger("Death", de.death);
		energyList.setInteger("Decay", de.decay);
		energyList.setInteger("Disease", de.disease);
		energyList.setInteger("Max", de.maxEnergy);
		tag.setTag("DaemonEnergy", energyList);
	}
	
	//
	//ItemStack methods
	//
	
	public static void checkTagCompound(ItemStack itemStack){
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}
	
	public static DaemonEnergy getDaemonEnergy(ItemStack itemStack){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		return DaemonEnergy.readFromNBT(tagCompound);
	}
	
	public static void setDaemonEnergy(ItemStack itemStack, DaemonEnergy de){
		checkTagCompound(itemStack);
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		de.writetoNBT(tagCompound);
	}

}
