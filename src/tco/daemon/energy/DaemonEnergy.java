package tco.daemon.energy;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.ItemShardGlass;
import tco.daemon.util.UtilItem;

public class DaemonEnergy {
	//misc. handling
	public static int getFirstStorage(IInventory inv) {
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof IDaemonEnergyStorage) {
				return i;
			}
		}
		return -1;
	}

	public static void absorbSoul(Entity victim, EntityPlayer player){
		InventoryPlayer inventory = player.inventory;
		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			Item item = stack.getItem();
			if(item instanceof ItemShardGlass){
				int damage = stack.getItemDamage();
				if(damage < ItemShardGlass.DAMAGE_CHARGED){
					stack.setItemDamage(damage + 1);
					return;
				}
			}
		}
		int orbSlot = getFirstStorage(inventory);
		if(orbSlot > -1) {
			ItemStack orb = inventory.getStackInSlot(orbSlot);
			DaemonEnergy energy = UtilItem.getDaemonEnergy(orb);
			energy.chargeEnergy(1, 1, 1); //TODO special cases
			UtilItem.setDaemonEnergy(orb, energy);
		}

	}

	public static boolean drainEnergy(EntityPlayer player, int death, int decay, int disease) {
		InventoryPlayer inv = player.inventory;
		int orbSlot = getFirstStorage(inv);
		if(orbSlot > -1) {
			ItemStack orb = inv.getStackInSlot(orbSlot);
			DaemonEnergy energy = UtilItem.getDaemonEnergy(orb);
			if(energy.drainEnergy(death, decay, disease)) {
				UtilItem.setDaemonEnergy(orb, energy);
				return true;
			}
		}
		return false;
	}

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
		if(death + deathEnergy + decay + decayEnergy + disease + diseaseEnergy > maxEnergy)
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
