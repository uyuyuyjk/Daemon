package tco.daemon.event;

import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import tco.daemon.EntityUnstableArrow;
import tco.daemon.ItemDagger;
import tco.daemon.ModDaemon;
import tco.daemon.util.UtilItem;

public class EventHandler {
	@ForgeSubscribe
	 public void onLivingDeath(LivingDeathEvent event){
		if(event.source.getSourceOfDamage() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			ItemStack weapon = player.getCurrentEquippedItem();
			if(weapon != null && weapon.getItem() instanceof ItemDagger){
				UtilItem.absorbSoul(event.entity, player);
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
}
