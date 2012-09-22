package tco.daemon;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemArrowUnstable extends ItemDaemon{

	protected ItemArrowUnstable(int id) {
		super(id);
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		list.add("Unstable I");
	}

}
