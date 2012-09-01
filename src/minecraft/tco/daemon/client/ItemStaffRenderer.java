package tco.daemon.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.IItemRenderer;

public class ItemStaffRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();

		RenderBlocks render = (RenderBlocks) data[0];
		EntityLiving entity = (EntityLiving) data[1];

		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				Minecraft.getMinecraft().renderEngine.getTexture(item.getItem()
						.getTextureFile()));

		Tessellator tesselator = Tessellator.instance;
		int iconIndex = entity.getItemIcon(item, 1337);
		float x1 = (iconIndex % 16 * 16) / 256.0F;
		float x2 = ((iconIndex % 16 * 16) + 15.99F) / 256.0F;
		float y1 = (iconIndex / 16 * 16) / 256.0F;
		float y2 = ((iconIndex / 16 * 16) + 15.99F) / 256.0F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(0, -0.3f, 0);
		float scale = 1.55F;
		GL11.glScalef(scale, scale, scale);
		//GL11.glRotatef(50, 0, 1.0F, 0);
		//GL11.glRotatef(335.0F, 0, 0, 1.0F);
		GL11.glTranslatef(0, -0.18f, 0.0f);
		
		renderItemIn2D(tesselator, x2, y1, x1, y2);
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
	
    public void renderItemIn2D(Tessellator tesselator, float par2, float par3, float par4, float par5){
        float one = 1.0F;
        float oneSixteenth = 0.0625F;
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(0, 0, 1.0F);
        tesselator.addVertexWithUV(0, 0, 0, par2, par5);
        tesselator.addVertexWithUV(one, 0, 0, par4, par5);
        tesselator.addVertexWithUV(one, 1.0, 0, par4, par3);
        tesselator.addVertexWithUV(0, 1.0, 0, par2, par3);
        tesselator.draw();
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(0, 0, -1.0F);
        tesselator.addVertexWithUV(0, 1.0, -oneSixteenth, par2, par3);
        tesselator.addVertexWithUV(one, 1.0, -oneSixteenth, par4, par3);
        tesselator.addVertexWithUV(one, 0, -oneSixteenth, par4, par5);
        tesselator.addVertexWithUV(0, 0, -oneSixteenth, par2, par5);
        tesselator.draw();
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(-1.0F, 0, 0);
        
        int i;
        float var9;
        float var10;
        float var11;

        for (i = 0; i < 16; ++i)
        {
            var9 = i / 16.0F;
            var10 = par2 + (par4 - par2) * var9 - 0.001953125F;
            var11 = one * var9;
            tesselator.addVertexWithUV(var11, 0, -oneSixteenth, var10, par5);
            tesselator.addVertexWithUV(var11, 0, 0, var10, par5);
            tesselator.addVertexWithUV(var11, 1.0, 0, var10, par3);
            tesselator.addVertexWithUV(var11, 1.0, -oneSixteenth, var10, par3);
        }

        tesselator.draw();
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(1.0F, 0, 0);
        for (i = 0; i < 16; ++i)
        {
            var9 = i / 16.0F;
            var10 = par2 + (par4 - par2) * var9 - 0.001953125F;
            var11 = one * var9 + 0.0625F;
            tesselator.addVertexWithUV(var11, 1.0, -oneSixteenth, var10, par3);
            tesselator.addVertexWithUV(var11, 1.0, 0, var10, par3);
            tesselator.addVertexWithUV(var11, 0, 0, var10, par5);
            tesselator.addVertexWithUV(var11, 0, -oneSixteenth, var10, par5);
        }
        tesselator.draw();
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(0, 1, 0);
        for (i = 0; i < 16; ++i)
        {
            var9 = i / 16.0F;
            var10 = par5 + (par3 - par5) * var9 - 0.001953125F;
            var11 = one * var9 + 0.0625F;
            tesselator.addVertexWithUV(0, var11, 0, par2, var10);
            tesselator.addVertexWithUV(one, var11, 0, par4, var10);
            tesselator.addVertexWithUV(one, var11, -oneSixteenth, par4, var10);
            tesselator.addVertexWithUV(0, var11, -oneSixteenth, par2, var10);
        }
        tesselator.draw();
        
        tesselator.startDrawingQuads();
        tesselator.setNormal(0, -1.0F, 0);
        for (i = 0; i < 16; ++i)
        {
            var9 = i / 16.0F;
            var10 = par5 + (par3 - par5) * var9 - 0.001953125F;
            var11 = one * var9;
            tesselator.addVertexWithUV(one, var11, 0, par4, var10);
            tesselator.addVertexWithUV(0, var11, 0, par2, var10);
            tesselator.addVertexWithUV(0, var11, -oneSixteenth, par2, var10);
            tesselator.addVertexWithUV(one, var11, -oneSixteenth, par4, var10);
        }
        tesselator.draw();
	}

}
