package tco.daemon.client.render;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tco.daemon.EntityArrowUnstable;

public class RenderArrowUnstable extends Render {

	private String overrideTexture;

	public RenderArrowUnstable(String override) {
		overrideTexture = override;
	}

	//Start: from RenderArrow

	public void renderArrow(EntityArrowUnstable par1EntityArrow, double par2, double par4, double par6, float par8, float par9)
	{
		this.loadTexture(overrideTexture);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		//don't extrapolate rotations
		GL11.glRotatef(par1EntityArrow.rotationYaw  - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(par1EntityArrow.rotationPitch, 0.0F, 0.0F, 1.0F);
		Tessellator var10 = Tessellator.instance;
		byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (0 + var11 * 10) / 32.0F;
		float var15 = (5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (5 + var11 * 10) / 32.0F;
		float var19 = (10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float var21 = par1EntityArrow.arrowShake - par9;

		if (var21 > 0.0F)
		{
			float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
		}

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var19);
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var19);
		var10.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var19);
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var19);
		var10.draw();

		for (int var23 = 0; var23 < 4; ++var23)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			var10.startDrawingQuads();
			var10.addVertexWithUV(-8.0D, -2.0D, 0.0D, var12, var14);
			var10.addVertexWithUV(8.0D, -2.0D, 0.0D, var13, var14);
			var10.addVertexWithUV(8.0D, 2.0D, 0.0D, var13, var15);
			var10.addVertexWithUV(-8.0D, 2.0D, 0.0D, var12, var15);
			var10.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity var1, double var2, double var4, double var6,
			float var8, float var9) {
		this.renderArrow((EntityArrowUnstable) var1, var2, var4, var6, var8, var9);
	}

}
