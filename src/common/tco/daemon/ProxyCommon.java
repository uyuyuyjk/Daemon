package tco.daemon;

import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import tco.daemon.client.GuiMatrixPortable;
import cpw.mods.fml.common.network.IGuiHandler;

public class ProxyCommon implements IGuiHandler{
	
	public int renderBrazierId;

	public void registerRenderInformation() {}
	
	public void absorbSoul(Entity victim, EntityPlayer player){
		InventoryPlayer inventory = player.inventory;
		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			Item item = stack.getItem();
			if(item instanceof ItemGlassShard){
				int damage = stack.getItemDamage();
				if(damage < ItemGlassShard.DAMAGE_CHARGED){
					stack.setItemDamage(damage + 1);
					return;
				}
			}
		}
		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack == null) continue;
			Item item = stack.getItem();
			if(item instanceof ItemOrb){
				//TODO implement
				return;
			}
		}
	}
	
	@ForgeSubscribe
	 public void onLivingDeath(LivingDeathEvent event){
		if(event.source.getSourceOfDamage() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			ItemStack weapon = player.getCurrentEquippedItem();
			if(weapon != null && weapon.getItem() instanceof ItemDagger){
				absorbSoul(event.entity, player);
			}
		}
	}
	
	@ForgeSubscribe
	public void onArrowLoose(ArrowLooseEvent event){
		EntityPlayer player = event.entityPlayer;
		if(player.inventory.hasItem(ModDaemon.instance.arrowUnstable.shiftedIndex)){            
            float power = event.charge / 20.0F;
            power = (power * power + power * 2.0F) / 3.0F;

			if (power < 0.1D) {
				return;
			}
			if (power > 1.0F) {
				power = 1.0F;
			}

            EntityArrow arrow = new EntityUnstableArrow(player.worldObj, player, power * 2.0F);

            player.worldObj.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (0.2F + 1.2F) + power * 0.5F);

			if (!player.worldObj.isRemote) {
				player.worldObj.spawnEntityInWorld(arrow);
			}

			player.inventory.consumeInventoryItem(ModDaemon.instance.arrowUnstable.shiftedIndex);
            event.bow.damageItem(1, player);
			event.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onArrowNock(ArrowNockEvent event){
		if(event.entityPlayer.inventory.hasItem(ModDaemon.instance.arrowUnstable.shiftedIndex)){
			event.entityPlayer.setItemInUse(event.result, event.result.getItem().getMaxItemUseDuration(event.result));
			event.setCanceled(true);
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
