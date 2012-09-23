package tco.daemon.client.render;

import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.FMLTextureFX;
import cpw.mods.fml.common.FMLLog;

public class TextureFXCrystal extends FMLTextureFX {

	private Minecraft minecraft;

	protected String texture;
	protected int[] baseImage;
	protected int[] overlay;

	public TextureFXCrystal(Minecraft mc, int icon, String tex) {
		super(icon);
		minecraft = mc;
		texture = tex;
		RenderEngine re = mc.renderEngine;
		tileImage = re.getTexture(texture);
	}

	@Override
	public void setup(){
		super.setup();
		baseImage = new int[tileSizeBase];
		overlay = new int[tileSizeBase];
		try {
			BufferedImage img = ImageIO.read(minecraft.texturePackList.getSelectedTexturePack().getResourceAsStream("/gui/items.png"));
			int x = iconIndex % 16 * tileSizeBase;
			int y = iconIndex / 16 * tileSizeBase;
			img.getRGB(x, y, tileSizeBase, tileSizeBase, baseImage, 0, tileSizeBase);
			x = (iconIndex + 1) % 16 * tileSizeBase;
			y = (iconIndex + 1) / 16 * tileSizeBase;
			img.getRGB(x, y, tileSizeBase, tileSizeBase, overlay, 0, tileSizeBase);
		} catch (Exception e) {
			FMLLog.log(Level.WARNING, "Error loading cystal texture animations", e);
			setErrored(true);
		}
	}

	@Override
	public void onTick() {
		World world = FMLClientHandler.instance().getClient().theWorld;
		if(world == null) return;
		float angle = world.getCelestialAngle(1.0F);
		int phase = world.getMoonPhase(1.0F) / 3; //0,1 ,or 2
		for(int i = 0; i < baseImage.length; i++) {
			if(baseImage[i] == 0xD67FFF) {
				imageData[4 * i] = (byte) (overlay[i] >> 16 & 0xFF);
				imageData[4 * i + 1] = (byte) (overlay[i] >> 8 & 0xFF);
				imageData[4 * i + 2] = (byte) (overlay[i] & 0xFF);
			} else {
				imageData[4 * i] = (byte) (baseImage[i] >> 16 & 0xFF);
				imageData[4 * i + 1] = (byte) (baseImage[i] >> 8 & 0xFF);
				imageData[4 * i + 2] = (byte) (baseImage[i] & 0xFF);
			}
		}
	}

}
