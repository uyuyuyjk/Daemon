package tco.daemon;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import tco.daemon.matrix.IMatrixAction;
import tco.daemon.matrix.IMatrixActivator;
import tco.daemon.matrix.MatrixActionConduct;

public class ItemDagger extends ItemDaemon implements IMatrixActivator {

	private int damage;

	protected ItemDagger(int id, EnumToolMaterial mat) {
		super(id);
		setMaxDamage(2 * mat.getMaxUses());
		damage = 3 + mat.getDamageVsEntity();
		setMaxStackSize(1);
		setFull3D();
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

	@Override
	public IMatrixAction getAction() {
		return new MatrixActionConduct();
	}

}
