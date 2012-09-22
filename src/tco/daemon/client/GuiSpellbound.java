package tco.daemon.client;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import tco.daemon.machines.TileEntitySpellbound;

public class GuiSpellbound extends GuiMatrix {

	public GuiSpellbound(InventoryPlayer inventoryPlayer,
			TileEntitySpellbound tileEntity) {
		super(inventoryPlayer, tileEntity);
	}

}
