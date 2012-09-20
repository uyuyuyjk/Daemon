package tco.daemon.util;

import net.minecraftforge.common.ISidedInventory;

public interface IMatrixAction {
	public abstract void doAction(ISidedInventory matrix, DaemonEnergy energy);
}
