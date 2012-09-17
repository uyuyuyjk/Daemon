package tco.daemon;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCursedOre extends BlockDaemon {

	public BlockCursedOre(int id) {
		super(id, Material.rock);
		setHardness(5.0F);
		setResistance(15.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.attackEntityFrom(DamageSource.magic, 1); //TODO cool fx
	}

}
