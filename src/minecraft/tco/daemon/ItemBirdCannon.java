package tco.daemon;

import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumAction;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

//extend ItemBow in order to make it enchantable
public class ItemBirdCannon extends ItemBow {
	
	public static final int AMMO = Item.egg.shiftedIndex;

	protected ItemBirdCannon(int id) {
		super(id);
        setMaxStackSize(1);
        setMaxDamage(128);
		setTextureFile(ReferenceConfigs.TEXTURE_ITEMS);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world,
			EntityPlayer player, int useDuration) {
		//TODO more enchants
        int enchPower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack); //speed
        int enchPunch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack); //chain
        int enchFlame = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack); //size
        //free infinity enchantment for creative
		boolean enchInfinity = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

		if (enchInfinity || player.inventory.hasItem(AMMO)) {
			int usedDuration = getMaxItemUseDuration(itemStack) - useDuration;
			float multiplier = usedDuration / 20.0F;
			multiplier = (multiplier * multiplier + multiplier * 2.0f) / 3.0F;

			if (multiplier > 1.0) {
				multiplier = 1.0f;
			}
			if (multiplier > 0.5) {
				itemStack.damageItem(1, player);
				
				world.playSoundAtEntity(player, "mob.chickenhurt", 1.0F, 1.0F
						/ (itemRand.nextFloat() * 0.4F + 1.2F) + multiplier
						* 0.5F);

				if (!(enchInfinity)) {
					player.inventory.consumeInventoryItem(AMMO);
				}

				if (!world.isRemote) {
				Entity projectile = createChickenBomb(world, player,
						multiplier, enchPower, enchPunch, enchFlame);
					world.spawnEntityInWorld(projectile);
				}
			}
		}
	}
	
	private static Entity createChickenBomb(World world, Entity player, float multiplier, int power, int punch, int flame){
		double posX, posY, posZ, motionX, motionY, motionZ;
		float rotationYaw, rotationPitch;
		posX = player.posX;
		posY = player.posY + player.getEyeHeight();
		posZ = player.posZ;
		rotationYaw = player.rotationYaw;
		rotationPitch = player.rotationPitch;
		
        posX -= (MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        posY -= 0.1;
        posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
        motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionX *= multiplier + power / 2.0;
        motionY *= multiplier + power / 2.0;
        motionZ *= multiplier + power / 2.0;
        EntityChickenDaemon projectile = new EntityChickenDaemon(world);
		projectile.setProjectileStats(1.5f + flame / 2.0f, 1 + punch);
        projectile.setLocationAndAngles(posX, posY, posZ,
        		rotationYaw, rotationPitch);
        projectile.setVelocity(motionX, motionY, motionZ);
		return projectile;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World par2World,
			EntityPlayer player) {
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0
				|| player.capabilities.isCreativeMode
				|| player.inventory.hasItem(AMMO)) {
			player.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		}
		
		return itemStack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

}
