package tco.daemon.item;

import net.minecraft.src.ItemStack;

public class ItemShardGlass extends ItemShard {
	public static final int DAMAGE_CHARGED = 8;

	public ItemShardGlass(int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int damage) {
		return iconIndex + damage / 4;
	}

	@Override
	public String getItemNameIS(ItemStack stack){
		switch(stack.getItemDamage()){
		case DAMAGE_CHARGED:
			return "item.soulShardCharged";
		default:
			return "item.glassShard";
		}
	}
}
