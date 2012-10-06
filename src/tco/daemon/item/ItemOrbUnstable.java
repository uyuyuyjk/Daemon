package tco.daemon.item;

import java.util.List;

import net.minecraft.src.EntityEnderPearl;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.ModDaemon;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.energy.DaemonEnergy;

public class ItemOrbUnstable extends ItemDaemon {

	public ItemOrbUnstable(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		list.add("Unstable II");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		if (player.ridingEntity == null &&
				DaemonEnergy.drainEnergy(player, 0, ReferenceConfigs.DECAY_ENERGY_UNSTABLE, 0)) {
			world.playSoundAtEntity(player, "random.bow", 0.5F,
					0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (ModDaemon.proxy.isSimulating()) {
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
			}

			return itemStack;
		}
		return itemStack;
	}

}
