package tco.daemon;

import net.minecraft.src.EnumRarity;
import net.minecraft.src.EnumToolMaterial;
import tco.daemon.matrix.IMatrixAction;
import tco.daemon.matrix.IMatrixActivator;

public class ItemStaffUndeath extends ItemStaff implements IMatrixActivator {

	public ItemStaffUndeath(int id, EnumToolMaterial mat) {
		super(id, mat);
		setRarity(EnumRarity.uncommon);
	}

	@Override
	public IMatrixAction getAction() {
		return null; //TODO uhoh
	}

	@Override
	public int getActivatorId() {
		return shiftedIndex;
	}

}
