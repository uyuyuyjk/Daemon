package tco.daemon;

import java.lang.reflect.Field;

import net.minecraft.src.*;

public class EntityUnstableArrow extends EntityArrow {

	public EntityUnstableArrow(World world) {
		super(world);
	}

	public EntityUnstableArrow(World world, EntityPlayer player, float power) {
		super(world, player, power);
	}
	
	//this is terrible, i know
	public void onUpdate(){
		super.onUpdate();
		try {
			boolean inGround = false;
			Field field = EntityArrow.class.getDeclaredField("inGround");
			field.setAccessible(true);
			if(field.getBoolean(this)){
				for (int i = 0; i < 32; ++i) {
					this.worldObj.spawnParticle("portal", this.posX, this.posY
							+ this.rand.nextDouble() * 2.0D, this.posZ,
							this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
				}

				if (!worldObj.isRemote) {
					if (shootingEntity != null && shootingEntity instanceof EntityPlayerMP) {
						EntityPlayerMP player = (EntityPlayerMP) shootingEntity;

						if (!player.serverForThisPlayer.serverShuttingDown && player.worldObj == this.worldObj) {
							player.setPositionAndUpdate(posX, posY, posZ);
							player.fallDistance = 0.0F;
							player.attackEntityFrom(DamageSource.fall, 5);
						}
					}
					setDead();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}
