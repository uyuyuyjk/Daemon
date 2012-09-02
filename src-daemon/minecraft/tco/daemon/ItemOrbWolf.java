package tco.daemon;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Facing;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

/**
 * An Item that spawns one WolfCreation
 */
public class ItemOrbWolf extends ItemDaemon {

	private Random rand = new Random();

	protected ItemOrbWolf(int id) {
		super(id);
		setMaxStackSize(1);
	}

	/**
	 * Spawns a WolfCreation and mounts the player on it
	 * 
	 * @return this method did something
	 */
	@Override
	public boolean tryPlaceIntoWorld(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int facing, float par8,
			float par9, float par10) {
		if (!world.isRemote) {
			x += Facing.offsetsXForSide[facing] + 0.5;
			y += Facing.offsetsYForSide[facing] + 0.5;
			z += Facing.offsetsZForSide[facing] + 0.5;

			EntityWolfCreation wolf = new EntityWolfCreation(world);
			wolf.setOwner(player.username);
			wolf.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F,
					0.0F);
			world.spawnEntityInWorld(wolf);
			wolf.playLivingSound();
		}

		// particle fx
		for (int count = 0; count < 16; count++) {
			world.spawnParticle("portal", x + (rand.nextDouble() - 0.5d), y + 2
					* rand.nextDouble(), z + (rand.nextDouble() - 0.5d),
					(rand.nextDouble() - 0.5d) * 2.0d, rand.nextDouble(),
					(rand.nextDouble() - 0.5d) * 2.0d);
		}
		// sound fx
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
		return true;
	}

}
