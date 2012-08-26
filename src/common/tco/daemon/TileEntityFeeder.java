package tco.daemon;

import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityCow;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class TileEntityFeeder extends TileEntityDaemon {

	private int maxCooldown;

	private int cooldown;

	public TileEntityFeeder() {
		inv = new ItemStack[1];
		cooldown = 0;
		maxCooldown = 20 * 10;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		cooldown++;

		if(cooldown > maxCooldown){
			cooldown = 0;
			ItemStack stack = inv[0];
			if (stack != null
					&&  stack.getItem() == Item.wheat
					&& stack.stackSize >= 2) {
				List<EntityAnimal> entityList = getEntitiesOfTypeInRange(EntityCow.class);
				if (entityList.size() >= 2) {
					attemptProcreation(entityList);
				}
			}
		}
	}
	
	public void applyMatrix(){
		//TODO effects
	}

	private List<EntityAnimal> getEntitiesOfTypeInRange(Class type){
		List<EntityAnimal> entityList = this.worldObj.getEntitiesWithinAABB(
				type,
				AxisAlignedBB.getBoundingBox(this.xCoord,
						this.yCoord, this.zCoord,
						(this.xCoord + 1),
						(this.yCoord + 1),
						(this.zCoord + 1))
						.expand(8.0D, 4.0D, 8.0D));
		return entityList;
	}

	private boolean attemptProcreation(List<EntityAnimal> entities){
		EntityAnimal parent1 = null, parent2 = null;
		for(EntityAnimal e : entities){
			if(e.getGrowingAge() == 0 ){
				if(parent1 == null)
					parent1 = e;
				else{
					parent2 = e;
					break;
				}
			}
		} 
		if(parent1 == null || parent2 == null)
			return false;

		EntityAnimal baby = parent1.spawnBabyAnimal(parent1);
		if (baby != null) {
			parent1.setGrowingAge(6000);//not sure how to limit it
			parent2.setGrowingAge(6000);
			baby.setGrowingAge(-24000);
			baby.setLocationAndAngles(parent1.posX, parent1.posY,
					parent1.posZ, parent1.rotationYaw,
					parent1.rotationPitch);

			worldObj.spawnEntityInWorld(baby);
			inv[0].stackSize -= 2;
			return true;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		cooldown = par1NBTTagCompound.getShort("Cooldown");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("Cooldown", (short)cooldown);
	}

	@Override
	public String getInvName() {
		return "container.feeder";
	}
	
}
