package tco.daemon.machines;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import tco.daemon.util.ReferenceTiles;

public class ItemBlockDaemonMachine extends ItemBlock {

	public ItemBlockDaemonMachine(int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getItemNameIS(ItemStack stack){
		return ReferenceTiles.values()[stack.getItemDamage()].getName();
	}

}
