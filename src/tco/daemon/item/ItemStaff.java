package tco.daemon.item;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;

public class ItemStaff extends ItemDaemon {

	private int damage;

	public ItemStaff(int id, EnumToolMaterial mat) {
		super(id);
		setMaxDamage(2 * mat.getMaxUses());
		damage = 2 + mat.getDamageVsEntity();
		setMaxStackSize(1);
		setRarity(EnumRarity.common);
	}

	@Override
	public boolean hitEntity(ItemStack itemStack, EntityLiving entity, EntityLiving entity2) {
		itemStack.damageItem(1, entity2);
		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity){
		return damage;
	}

}
