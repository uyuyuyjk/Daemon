package tco.daemon.item;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.ModDaemon;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.energy.DaemonEnergy;

public class ItemOrbStable extends ItemDaemon {

	private static final int blockId = Block.glass.blockID;

	public ItemOrbStable(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		int x = (int) player.posX, y = (int) player.posY, z = (int) player.posZ;
		if(ModDaemon.proxy.isSimulating() &&
				world.getBlockId(x, y, z) == 0 &&
				DaemonEnergy.drainEnergy(player, 0, 0, ReferenceConfigs.DISEASE_ENERGY_STABLE)) {
			if (!world.setBlockWithNotify(x, y, z, blockId)) {
				return itemStack;
			}
		}

		return itemStack;
	}

}
