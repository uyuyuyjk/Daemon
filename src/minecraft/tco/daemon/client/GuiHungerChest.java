package tco.daemon.client;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import tco.daemon.ContainerHungerChest;
import tco.daemon.TileEntityHungerChest;
import tco.daemon.util.ReferenceConfigs;

public class GuiHungerChest extends GuiContainer {

    private int inventoryRows = 3;

    public GuiHungerChest(InventoryPlayer inventoryPlayer,
			TileEntityHungerChest tileEntity) {
		super(new ContainerHungerChest(inventoryPlayer, tileEntity));
        ySize = 222 - 108 + inventoryRows * 18;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(StatCollector.translateToLocal("container.hungerChest"), 8, 6, 4210752);
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 4210752);
	}
	
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int texture = mc.renderEngine.getTexture(ReferenceConfigs.GUI_HUNGER_CHEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //x,y,u,v,width,height
        //(x,y) screen coords, (u,v) texture coords, (width,height) draw dimensions
        drawTexturedModalRect(x, y,                           0,   0, xSize, inventoryRows * 18 + 17);
        drawTexturedModalRect(x, y + inventoryRows * 18 + 17, 0, 126, xSize, 96);
    }

}