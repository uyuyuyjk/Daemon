package tco.daemon;

import net.minecraft.src.*;

public class EntityChickenDaemon extends EntityChicken {

	private float explosionSize;
	private int explosionChain;

	public EntityChickenDaemon(World world) {
		super(world);
	}
	
	public void setProjectileStats(float size, int chain){
		explosionSize = size;
		explosionChain = chain;
	}

	public void onLivingUpdate() {
		if (!worldObj.isRemote && isEntityAlive()
				&& (isCollidedVertically || isCollidedHorizontally || this.inWater)) {
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

	protected void dropFewItems(boolean par1, int par2) {
	}

	protected int getDropItemId() {
		return 0;
	}

	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);
		explosionSize = tagCompound.getFloat("ExplosionSize");
		explosionChain = tagCompound.getInteger("ExplosionChain");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setFloat("ExplosionSize", explosionSize);
		tagCompound.setFloat("ExplosionChain", explosionChain);
	}

}
