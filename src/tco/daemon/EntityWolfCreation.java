package tco.daemon;

import java.util.Random;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

/**
 * A wolf that can be ridden on
 * 
 */
public class EntityWolfCreation extends EntityWolf {

	private Random rand = new Random();

	private String owner;

	// speed of entity on land and in the air
	private static final float vLand = 0.16f, vAir = 0.2f;

	// if player moves forward twice in 7/20 seconds, fly
	private int flyToggleTimer = 0;

	public EntityWolfCreation(World world) {
		super(world);
		// texture = "weirdWolf.png"; //which texture to use
		// entity is on the ground by default
		setFlying(false);
		// how high the entity can step when walking/sprinting (in blocks)
		stepHeight = 1.0f;
		yOffset = 0.6f;
	}

	/**
	 * Called when a player interacts with an entity Lets the player mount this
	 * entity
	 * 
	 * @param player
	 *            the player who right clicks this entity
	 * @return this method did something
	 */
	@Override
	public boolean interact(EntityPlayer player) {
		if (!worldObj.isRemote && owner != null && owner.equals(player.username)) {
			if (riddenByEntity == null || riddenByEntity == player) {
				player.mountEntity(this);
				return true;
			}
		}
		return false;
	}

	/**
	 * Update method for entity. Uses player input to control the entity
	 */
	@Override
	public void onUpdate() {

		if (flyToggleTimer > 0) {
			flyToggleTimer--;
		}

		if (getAge() > 20 * 10) {
			if (riddenByEntity == null) {
				//riddenByEntity.mountEntity(this);
			}
			// setDead();
		}

		if (isMounted() && worldObj.isRemote) {
			EntityClientPlayerMP player = (EntityClientPlayerMP) riddenByEntity;
			// align player and entity rotations
			rotationYaw = player.rotationYaw;
			prevRotationYaw = rotationYaw;
			// gets input for forward movement
			setMoveForward(player.movementInput.moveForward);

			// checks if player double taps forward
			boolean movingForward = moveForward >= 0.8f;
			if (!isFlying()) {
				player.movementInput.updatePlayerMoveState();
				boolean movingForward2 = player.movementInput.moveForward >= 0.8f;
				if (onGround && !movingForward && movingForward2) {
					if (flyToggleTimer == 0) {
						flyToggleTimer = 7;
					} else {
						setFlying(true); // fly if player taps forward twice
						// in
						// 7/20 seconds
						flyToggleTimer = 0;
					}
				}
			} else if (!movingForward) {
				setFlying(false); // land if player stops pressing forward
			} else {
				fallDistance = 0; // entity doesn't get hurt after falling
			}

			// sideways movement
			moveStrafing = player.movementInput.moveStrafe;
			// jumping
			if (player.movementInput.jump) {
				jump(0.6);
			}
		}

		super.onUpdate();
	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
		}
	}

	/**
	 * Overloaded jump method with jump speed as parameter, also lets the entity
	 * fly
	 * 
	 * @param motion
	 *            velocity of jump
	 */
	public void jump(double motion) {
		if (onGround || isFlying() || handleLavaMovement()
				|| handleWaterMovement()) {
			motionY = motion;
			isAirBorne = true;
			getJumpHelper().setJumping();
		}
	}

	/**
	 * @return max health of the entity
	 */
	@Override
	public int getMaxHealth() {
		return 200;
	}

	/**
	 * Used AI if this returns true. Return false if mounted so that the player
	 * can control this entity.
	 * 
	 * @return ai is enabled
	 */
	@Override
	public boolean isAIEnabled() {
		return !isMounted();
	}

	/**
	 * This method is called if AI is disabled. Do nothing.
	 */
	@Override
	public void updateEntityActionState() {
	}

	/**
	 * Helper for checking if the entity is mounted
	 * 
	 * @return Entity is mounted
	 */
	private boolean isMounted() {
		return riddenByEntity != null;
	}

	/**
	 * This entity sprints when it flies.
	 * 
	 * @return the entity is flying
	 */
	private boolean isFlying() {
		return isSprinting();
	}

	/**
	 * Entity is faster when flying
	 * 
	 * @param flag
	 *            flying
	 */
	private void setFlying(boolean flag) {
		setSprinting(flag);
		if (flag) {
			landMovementFactor = vAir;
			jumpMovementFactor = vAir;
		} else {
			landMovementFactor = vLand;
			jumpMovementFactor = vLand;
		}
	}

	@Override
	public void setOwner(String str) {
		owner = str;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setString("PlayerOwner", owner);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);
		setOwner(tagCompound.getString("PlayerOwner"));
	}
}
