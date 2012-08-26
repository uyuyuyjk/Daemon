package tco.daemon;

import java.util.List;
import java.util.Random;
import net.minecraft.src.*;
	
/**
 * An Item that spawns one WolfCreation
 */
public class ItemWolfOrb extends ItemDaemon {
	
	private Random rand = new Random();

	protected ItemWolfOrb(int id) {
		super(id);
	}

	/**
	 * Spawns a WolfCreation and mounts the player on it
	 * @return this method did something
	 */
	public boolean onItemUseFirst(ItemStack par1ItemStack,
			EntityPlayer player, World world, int x, int y,
			int z, int i) {
		
		// create and place entity
		EntityWolfCreation e = new EntityWolfCreation(world);
		e.setOwner(player.username);
		e.setPosition(x, y + 1, z);

		if (!world.isRemote) {
			world.spawnEntityInWorld(e);
			//player.mountEntity(e);
		}

		//particle fx
		for (int count = 0; count < 128; count++) {
			world.spawnParticle("portal",
					x + (rand.nextDouble() - 0.5d),
					y + 2 * rand.nextDouble(),
					z + (rand.nextDouble() - 0.5d),
					(rand.nextDouble() - 0.5d) * 2.0d,
					rand.nextDouble(),
					(rand.nextDouble() - 0.5d) * 2.0d);
		}
		//sound fx
		world.playSoundAtEntity(e, "mob.endermen.portal", 1.0F, 1.0F);
		return true;
	}
}
