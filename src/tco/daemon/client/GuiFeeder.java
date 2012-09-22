package tco.daemon.client;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import tco.daemon.machines.ContainerFeeder;
import tco.daemon.machines.TileEntityFeeder;
import tco.daemon.util.ReferenceConfigs;

public class GuiFeeder extends GuiContainer {

	public GuiFeeder(InventoryPlayer inventoryPlayer,
			TileEntityFeeder tileEntity) {
		super(new ContainerFeeder(inventoryPlayer, tileEntity));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		int texture = mc.renderEngine
				.getTexture(ReferenceConfigs.GUI_FEEDER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
