package tco.daemon;

import net.minecraft.src.EntityChicken;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityChickenDaemon extends EntityChicken {

	private float explosionSize;
	private int explosionChain;

	public EntityChickenDaemon(World world) {
		super(world);
		setProjectileStats(1.5f, 1);
	}
	
	public void setProjectileStats(float size, int chain){
		explosionSize = size;
		explosionChain = chain;
	}

	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote && isEntityAlive()
				&& (isCollidedVertically || isCollidedHorizontally
						|| handleLavaMovement() || handleWaterMovement())) {
			if (explosionChain > 1) {
				worldObj.createExplosion(this, posX, posY, posZ, explosionSize);
				explosionChain--;
			} else if (explosionChain == 1) {
				worldObj.createExplosion(null, posX, posY, posZ, explosionSize);
				explosionChain = 0;
			}
		}
		super.onLivingUpdate();
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2) {
	}

	@Override
	protected int getDropItemId() {
		return 0;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);
		explosionSize = tagCompound.getFloat("ExplosionSize");
		explosionChain = tagCompound.getInteger("ExplosionChain");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setFloat("ExplosionSize", explosionSize);
		tagCompound.setInteger("ExplosionChain", explosionChain);
	}

}
