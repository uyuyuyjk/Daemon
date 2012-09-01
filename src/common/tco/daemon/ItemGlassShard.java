package tco.daemon;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemGlassShard extends ItemDaemon{
	public static final int DAMAGE_CHARGED = 8;

	protected ItemGlassShard(int id) {
		super(id);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
		
	public int getIconFromDamage(int damage) {
		return iconIndex + damage / 4;
	}
	
	public String getItemNameIS(ItemStack stack){
		switch(stack.getItemDamage()){
		case DAMAGE_CHARGED:
			return "item.glassShardCharged";
		default:
			return "item.glassShard";
		}
	}
}
