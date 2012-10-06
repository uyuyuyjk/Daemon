package tco.daemon.util.matrix;

import net.minecraft.src.IInventory;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.util.energy.DaemonEnergy;

public class MatrixActionSmelt implements IMatrixAction {

	private int boost;

	public int progress;

	public MatrixActionSmelt(int b) {
		boost = b;
	}

	public MatrixActionSmelt() {
		this(1);
	}

	@Override
	public void doAction(IInventory matrix, DaemonEnergy energy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub

	}
}
