package tco.daemon.matrix;

import net.minecraft.src.IInventory;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.energy.DaemonEnergy;

public class MatrixActionSmelt implements IMatrixAction {

	private int boost;
	
	public int progress;

	public MatrixActionSmelt(int b) {
		boost = b;
		progress = 0;
	}

	public MatrixActionSmelt() {
		this(1);
	}

	@Override
	public void doAction(IInventory matrix, DaemonEnergy energy) {
		progress += boost;
		//TODO finish implementation
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("Progress", progress);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		progress = tagCompound.getInteger("Progress");
	}
}
