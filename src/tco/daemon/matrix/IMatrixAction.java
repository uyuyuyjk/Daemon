package tco.daemon.matrix;

import net.minecraft.src.IInventory;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.energy.DaemonEnergy;

public interface IMatrixAction {
	public abstract void doAction(IInventory matrix, DaemonEnergy energy);
	public abstract void writeToNBT(NBTTagCompound tagCompound);
	public abstract void readFromNBT(NBTTagCompound tagCompound);
}
