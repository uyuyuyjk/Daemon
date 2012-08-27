package tco.daemon;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.World;

public class ItemDagger extends ItemDaemon {

	protected ItemDagger(int id) {
		super(id);
		setMaxStackSize(1);
		setFull3D();
	}
				
	public int getDamageVsEntity(Entity entity){
		return 2;
	}
	
}
