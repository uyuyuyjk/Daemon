package tco.daemon;

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

}
