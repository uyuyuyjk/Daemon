package tco.daemon.item;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import tco.daemon.util.ReferenceConfigs;

public class ItemDaemon extends Item {

	private EnumRarity rarity;

	public ItemDaemon(int id) {
		super(id);
		rarity = EnumRarity.common;
		setTextureFile(ReferenceConfigs.TEXTURE_ITEMS);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	public Item setRarity(EnumRarity r){
		rarity = r;
		return this;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		if(itemStack.isItemEnchanted() && rarity != EnumRarity.epic){
			return EnumRarity.rare;
		}
		return rarity;
	}

}
