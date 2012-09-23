package tco.daemon;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.machines.TileEntityDaemon;
import tco.daemon.util.UtilItem;

public class TileEntityDaemonAltar extends TileEntityDaemon {

	private boolean active = false;
	private int timer = 0;
	private List<EntityLiving> champions = new LinkedList<EntityLiving>();

	@Override
	public void updateMatrix(){
		super.updateMatrix();
		if(!ModDaemon.proxy.isSimulating()){return;}
		checkActive();
		if(active) {
			List<EntityLiving> dead = new LinkedList<EntityLiving>();
			for(EntityLiving e : champions) {
				if(e.isDead){
					dead.add(e);
					UtilItem.dropItem(worldObj, new ItemStack(Item.goldNugget), xCoord, yCoord, zCoord);
				}
			}
			champions.removeAll(dead);
			if(champions.size() < 5 && worldObj.rand.nextInt(champions.size() + 1) == 0) {
				summonChampion(new EntityZombie(worldObj), 2);
			}
		} else {
			for(EntityLiving e : champions) {
				if(!e.isDead){
					e.attackEntityFrom(DamageSource.outOfWorld, 1337);
				}
			}
			champions.clear();
		}
	}

	public void checkActive() {
		int x, y, z;
		x = xCoord - 2;
		y = yCoord - 1;
		z = zCoord - 2;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(worldObj.getBlockId(x + i, y, z + j) != Block.obsidian.blockID) {
					active = false;
					return;
				}
			}
		}
		active = true;
	}

	public void summonChampion(EntityLiving entity, int radius){
		double x = xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * radius;
		double y = yCoord + worldObj.rand.nextInt(3);
		double z = zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * radius;
		entity.setLocationAndAngles(x, y, z, worldObj.rand.nextFloat() * 360.0F, 0.0F);
		champions.add(entity);
		worldObj.spawnEntityInWorld(entity);
	}


	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		active = tagCompound.getBoolean("Active");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("Active", active);
	}

	@Override
	public String getInvName() {
		return "daemonAltar.name";
	}

}
