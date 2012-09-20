package tco.daemon.matrix;

import net.minecraft.src.IInventory;
import tco.daemon.util.DaemonEnergy;

public interface IMatrixAction {
	public abstract void doAction(IInventory matrix, DaemonEnergy energy);
}
