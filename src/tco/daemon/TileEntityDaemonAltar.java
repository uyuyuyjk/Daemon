package tco.daemon;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import tco.daemon.machines.TileEntityDaemon;
import tco.daemon.util.UtilItem;

public class TileEntityDaemonAltar extends TileEntityDaemon {

	protected boolean active = false;
	protected int timer = 0;
	protected List<Entity> champions = new LinkedList<Entity>();

	@Override
	public void updateMatrix(){
		super.updateMatrix();
		if(!ModDaemon.proxy.isSimulating()){return;}
		checkActive();
		if(active) {
			timer--;
			if(timer <= 0) {
				timer = 0;
				destroyChampions();
			} else {
				removeDead();
				if(champions.size() == 0) {
					timer = 0;
					UtilItem.dropItem(worldObj, new ItemStack(Item.ingotGold), xCoord, yCoord, zCoord);
				}
			}
		} else {
			timer = 0;
			destroyChampions();
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

	public void challenge() {
		if(timer <= 0) {
			timer = 30;
			for(int i = 0; i < 10; i++) {
				summonChampion(EntityList.createEntityByName("Zombie", worldObj), 2);
			}
		}
	}

	public void summonChampion(Entity entity, int radius){
		double x = xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * radius;
		double y = yCoord + worldObj.rand.nextInt(3);
		double z = zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * radius;
		entity.setLocationAndAngles(x, y, z, worldObj.rand.nextFloat() * 360.0F, 0.0F);
		champions.add(entity);
		worldObj.spawnEntityInWorld(entity);
	}

	public void removeDead() {
		List<Entity> dead = new LinkedList<Entity>();
		for(Entity e : champions) {
			if(e.isDead){
				dead.add(e);
			}
		}
		champions.removeAll(dead);
	}

	public void destroyChampions() {
		for(Entity e : champions) {
			if(!e.isDead){
				e.attackEntityFrom(DamageSource.outOfWorld, 1337);
			}
		}
		champions.clear();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		destroyChampions();
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
