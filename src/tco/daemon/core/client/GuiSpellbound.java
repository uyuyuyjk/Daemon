package tco.daemon.core.client;

import net.minecraft.src.InventoryPlayer;
import tco.daemon.machines.TileEntitySpellbound;

public class GuiSpellbound extends GuiMatrix {

	public GuiSpellbound(InventoryPlayer inventoryPlayer,
			TileEntitySpellbound tileEntity) {
		super(inventoryPlayer, tileEntity);
	}

}
