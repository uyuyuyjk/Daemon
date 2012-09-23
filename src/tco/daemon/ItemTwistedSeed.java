package tco.daemon;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.util.ReferenceConfigs;

public class ItemTwistedSeed extends ItemOrbUnstable {

	public static final int TELEPORT_DISTANCE = 64;

	protected ItemTwistedSeed(int id) {
		super(id);
	}

	@Override
	public boolean tryPlaceIntoWorld(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(ModDaemon.proxy.isSimulating() && DaemonEnergy.drainEnergy(player, 0, ReferenceConfigs.DECAY_ENERGY_TWISTED, 0)) {
			ForgeDirection direction = ForgeDirection.values()[side].getOpposite();
			int dx = direction.offsetX, dy = direction.offsetY, dz = direction.offsetZ;
			if (dx != 0 || dy != 0 || dz != 0) {
				for(int i = 1; i < TELEPORT_DISTANCE; i++) {
					int blockId = world.getBlockId(x + dx * i, y + dy * i, z + dz * i);
					if(blockId == Block.bedrock.blockID) {
						break;
					}
					if(blockId == 0) {
						player.setPositionAndUpdate(x + dx * i, y + dy * i, z + dz * i);
						break;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		list.add("Unstable V");
	}

}
