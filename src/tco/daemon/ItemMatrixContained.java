package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceTiles;
import tco.daemon.util.UtilItem;

public class ItemMatrixContained extends ItemDaemon {

	protected ItemMatrixContained(int id) {
		super(id);
		setMaxStackSize(1);
		setRarity(EnumRarity.rare);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		UtilItem.setUniqueItem(itemStack, player);
		player.openGui(ModDaemon.instance, ReferenceTiles.CONTAINED_MATRIX, world, 0, 0, 0);
		return itemStack;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
}
