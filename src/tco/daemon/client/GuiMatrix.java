package tco.daemon.client;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import tco.daemon.machines.ContainerMatrix;
import tco.daemon.machines.TileEntityDaemon;
import tco.daemon.util.ReferenceConfigs;

public class GuiMatrix extends GuiContainer {

	public GuiMatrix(InventoryPlayer inventoryPlayer,
			TileEntityDaemon tileEntity) {
		super(new ContainerMatrix(inventoryPlayer, tileEntity));
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(StatCollector.translateToLocal("matrix.name"), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		int texture = mc.renderEngine
				.getTexture(ReferenceConfigs.GUI_MATRIX);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
