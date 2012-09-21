package tco.daemon.machines;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.energy.DecomposerRecipes;
import tco.daemon.matrix.DaemonMatrix;
import tco.daemon.util.UtilItem;

public class TileEntityDecomposer extends TileEntityDaemon {

	public static final int FUEL_CAPACITY = 200;
	public static final int BASE_SPEED = 1;

	private DecomposerRecipes.DecomposerRecipe recipe;

	private int speed;
	private int fuelLeft;
	private int progress;

	public TileEntityDecomposer(){
		super();
		inv = new ItemStack[2];
		speed = BASE_SPEED;
		fuelLeft = 0;
		progress = 0;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote) return;

		ItemStack storage =  DaemonMatrix.getStorageItem(this);
		if(storage != null) {
			DaemonEnergy storageDE = UtilItem.getDaemonEnergy(storage);
			if(fuelLeft < FUEL_CAPACITY && storageDE.drainEnergy(0, speed, 0)){
				fuelLeft += speed;
			} else if(fuelLeft > FUEL_CAPACITY
					&& storageDE.chargeEnergy(0, fuelLeft - FUEL_CAPACITY, 0)) {
				fuelLeft -= fuelLeft - FUEL_CAPACITY;
			}
		}

		if(inv[0] != null && inv[1] == null && DecomposerRecipes.hasRecipe(inv[0].itemID)) {
			recipe = DecomposerRecipes.getRecipe(inv[0].itemID);
			if(recipe.cost > fuelLeft) {
				progress = 0;
			} else {
				progress += speed;
			}
			if(progress >= recipe.cost){
				progress = 0;
				fuelLeft -= recipe.cost;
				ItemStack result = DecomposerRecipes.useRecipe(inv[0].itemID);
				recipe = null;
			}
		} else {
			progress = 0;
		}
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		switch (side) {
		case DOWN:
			return super.getStartInventorySide(side);
		case UP:
			return matrix.length;
		default:
			return matrix.length + 1;
		}
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		switch (side) {
		case DOWN:
			return super.getSizeInventorySide(side);
		default:
			return 1;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		fuelLeft = tagCompound.getInteger("Fuel");
		progress = tagCompound.getInteger("Progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Fuel", fuelLeft);
		tagCompound.setInteger("Progress", progress);
	}

	@Override
	public String getInvName() {
		return "decomposer.name";
	}
}
