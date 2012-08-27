package tco.daemon;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemDaemon extends Item {
	
	private EnumRarity rarity;

	protected ItemDaemon(int id) {
		super(id);
		rarity = EnumRarity.common;
		setTextureFile("/tco/daemon/sprites/daemonitems.png");
		setTabToDisplayOn(CreativeTabs.tabMisc);
	}
	
	public Item setRarity(EnumRarity r){
		rarity = r;
		return this;
	}
	
	public EnumRarity getRarity(ItemStack itemStack) {
		if(itemStack.isItemEnchanted() && rarity != EnumRarity.epic){
			return EnumRarity.rare;
		}
		return rarity;
	}

}
