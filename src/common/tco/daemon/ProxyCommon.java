package tco.daemon;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import tco.daemon.client.GuiMatrixPortable;
import cpw.mods.fml.common.network.IGuiHandler;

public class ProxyCommon implements IGuiHandler{
	
	public int renderBrazierId;

	public void registerRenderInformation() {}
	
	public void absorbSoul(Entity victim, EntityPlayer player){
		
	}
	
	@ForgeSubscribe
	 public void onLivingDeath(LivingDeathEvent event){
		if(event.source.getSourceOfDamage() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			ItemStack weapon = player.getCurrentEquippedItem();
			if(weapon != null && weapon.getItem() == ModDaemon.instance.daggerSacrifice){
				absorbSoul(event.entity, player);
			}
		}
	}
		
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		if(id < 100){
			return ReferenceGui.getContainer(id, player, world, x, y, z);
		}
		switch (id) {
		case ReferenceGui.PORTABLE_MATRIX:
			return new ContainerMatrixPortable(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		if(id < 100){
			return ReferenceGui.getGui(id, player, world, x, y, z);
		}
		switch (id) {
		case ReferenceGui.PORTABLE_MATRIX:
			return new GuiMatrixPortable(player.inventory);
		}
		return null;
	}
	
}
