package tco.daemon.handlers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import tco.daemon.EntityArrowUnstable;
import tco.daemon.ItemDagger;
import tco.daemon.ModDaemon;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.util.ReferenceConfigs;

public class EventHandler {
	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			//fire amulets
			if(event.source.fireDamage()){
				if(player.inventory.hasItem(ModDaemon.instance.amuletInferno.shiftedIndex)
						&& DaemonEnergy.drainEnergy(player, ReferenceConfigs.DEATH_ENERGY_INFERNO, 0, 0)) {
					player.extinguish();
					event.ammount = 0;
				} else if(player.inventory.hasItem(ModDaemon.instance.amuletBlaze.shiftedIndex)
						&& DaemonEnergy.drainEnergy(player, ReferenceConfigs.DEATH_ENERGY_BLAZE, 0, 0)) {
					player.extinguish();
					event.ammount = Math.max(event.ammount - 2, 0);
				} else if(player.inventory.hasItem(ModDaemon.instance.amuletFire.shiftedIndex)
						&& DaemonEnergy.drainEnergy(player, ReferenceConfigs.DEATH_ENERGY_FIRE, 0, 0)) {
					event.ammount = Math.max(event.ammount - 1, 1);
				}
			}
			//unlife amulet
			else if(player.getHealth() <= event.ammount){
				int unlifeId = ModDaemon.instance.amuletUnlife.shiftedIndex;
				if(player.getHealth() <= event.ammount &&
						!"outOfWorld".equals(event.source.getDamageType())){
					if(DaemonEnergy.drainEnergy(player, ReferenceConfigs.ENERGY_UNDEATH,
							ReferenceConfigs.ENERGY_UNDEATH,
							ReferenceConfigs.ENERGY_UNDEATH) &&
							player.inventory.hasItem(unlifeId)) {
						player.heal(player.getMaxHealth() / 2);
						event.setCanceled(true);
					}
				}
			}
			//miner amulet
			else if("inWall".equals(event.source.getDamageType())) {}//negate
			else if("drown".equals(event.source.getDamageType())) {}//negate
			else if("fall".equals(event.source.getDamageType())) {}//reduce by 25%
			else if("explosion".equals(event.source.getDamageType())) {}//reduce by 25%
			//shield ring
			//TODO reduce by 50%
		}
	}

	@ForgeSubscribe
	public void onLivingDeath(LivingDeathEvent event){
		if(event.source.getSourceOfDamage() instanceof EntityPlayer) {
			//dagger
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			ItemStack weapon = player.getCurrentEquippedItem();
			if(weapon != null && weapon.getItem() instanceof ItemDagger){
				DaemonEnergy.absorbSoul(event.entity, player);
			}
		}
	}

	@ForgeSubscribe
	public void onLivingSetAttack(LivingSetAttackTargetEvent event) {
		if(event.entityLiving.isEntityUndead()) {
			event.entityLiving.setFire(5); //TODO lol
		}
	}

	@ForgeSubscribe
	public void onArrowLoose(ArrowLooseEvent event){
		EntityPlayer player = event.entityPlayer;
		if(player.inventory.hasItem(ModDaemon.instance.arrowUnstable.shiftedIndex)){
			float power = event.charge / 20.0F;
			power = (power * power + power * 2.0F) / 3.0F;

			if (power < 0.5D) {
				event.setCanceled(true);
				return;
			}
			if (power > 1.0F) {
				power = 1.0F;
			}

			EntityArrowUnstable arrow = new EntityArrowUnstable(player.worldObj, player, power * 2.0F);

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
