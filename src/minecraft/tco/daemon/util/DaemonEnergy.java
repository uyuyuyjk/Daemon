package tco.daemon.util;

import net.minecraft.src.NBTTagCompound;

public class DaemonEnergy {
	public int deathEnergy, decayEnergy, diseaseEnergy;
	public int maxEnergy;
	
	public DaemonEnergy(){
		this(0, 0, 0, 0);
	}
	
	public DaemonEnergy(int dea, int dec, int dis, int max){
		deathEnergy = dea;
		decayEnergy = dec;
		diseaseEnergy = dis;
		maxEnergy = max;
	}
		
	public int getTotal(){
		return deathEnergy + decayEnergy + diseaseEnergy;
	}
	
	public boolean drainEnergy(int death, int decay, int disease){
		if(death > deathEnergy || decay > decayEnergy || disease > diseaseEnergy)
			return false;
		deathEnergy -= death;
		decayEnergy -= decay;
		diseaseEnergy -= disease;
		return true;
	}
	
	public boolean chargeEnergy(int death, int decay, int disease){
		if(death + deathEnergy + decay + decayEnergy + disease + diseaseEnergy < maxEnergy)
			return false;
		deathEnergy += death;
		decayEnergy += decay;
		diseaseEnergy += disease;
		return true;
	}
	
	public void merge(DaemonEnergy de2) {
		deathEnergy += de2.deathEnergy;
		decayEnergy += de2.decayEnergy;
		diseaseEnergy += de2.diseaseEnergy;
		maxEnergy += de2.maxEnergy;
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
		energyList.setInteger("Death", de.deathEnergy);
		energyList.setInteger("Decay", de.decayEnergy);
		energyList.setInteger("Disease", de.diseaseEnergy);
		energyList.setInteger("Max", de.maxEnergy);
		tag.setTag("DaemonEnergy", energyList);
	}
}
