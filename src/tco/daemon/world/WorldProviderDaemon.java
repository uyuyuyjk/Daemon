package tco.daemon.world;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.ChunkProviderEnd;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.WorldChunkManagerHell;
import net.minecraft.src.WorldProvider;

public class WorldProviderDaemon extends WorldProvider {

	public WorldProviderDaemon() {
		// TODO custom chunk manager and biomes
		worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 1.0F, 0.0F);
	}

	@Override
	public IChunkProvider getChunkProvider() {
		//TODO chunk providers
		return new ChunkProviderEnd(worldObj, worldObj.getSeed());
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public float getCloudHeight() {
		return 2.0F;
	}

	@Override
	public String getDimensionName() {
		return "Daemons Throne";
	}

}
