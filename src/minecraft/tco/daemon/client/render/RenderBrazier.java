package tco.daemon.client.render;

import net.minecraft.src.Block;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBrazier implements ISimpleBlockRenderingHandler {

	public RenderBrazier() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(block.getMixedBrightnessForBlock(
				renderer.blockAccess, x, y, z));
		float var6 = 1.0F;
		int var7 = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;
		float var12;

		if (EntityRenderer.anaglyphEnable) {
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}

		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		short var16 = 154;
		var12 = 0.125F;
		renderer.renderSouthFace(block, (x - 1.0F + var12),
				y, z, var16);
		renderer.renderNorthFace(block, (x + 1.0F - var12),
				y, z, var16);
		renderer.renderWestFace(block, x, y,
				(z - 1.0F + var12), var16);
		renderer.renderEastFace(block, x, y,
				(z + 1.0F - var12), var16);
		short var17 = 139;
		renderer.renderTopFace(block, x,
				(y - 1.0F + 0.25F), z, var17);
		renderer.renderBottomFace(block, x,
				(y + 1.0F - 0.75F), z, var17);
		int var14 = renderer.blockAccess.getBlockMetadata(x, y, z);

		if (var14 > 0) {
			short var15 = 205;

			if (var14 > 3) {
				var14 = 3;
			}

			renderer.renderTopFace(
					block,
					x,
					(y - 1.0F + (6.0F + var14 * 3.0F) / 16.0F),
					z, var15);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return 0;
	}

}
