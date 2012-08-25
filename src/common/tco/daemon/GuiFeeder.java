package tco.daemon;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

public class GuiFeeder extends GuiContainer {

	public GuiFeeder(InventoryPlayer inventoryPlayer,
			TileEntityFeeder tileEntity) {
		super(new ContainerFeeder(inventoryPlayer, tileEntity));
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Feeder", 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		int texture = mc.renderEngine
				.getTexture("/tco/daemon/sprites/feeder.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
