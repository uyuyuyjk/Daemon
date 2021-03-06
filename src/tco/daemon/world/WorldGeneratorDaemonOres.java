package tco.daemon.world;


import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import tco.daemon.ModDaemon;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorDaemonOres implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == BiomeGenBase.hell){

		}else if (biome == BiomeGenBase.sky){
		}else{
			generateOre(world, chunkX, chunkZ, random,
					new WorldGenMinable(ModDaemon.instance.blockCursedStone.blockID, 10),
					10, 10, 40);
			generateOre(world, chunkX, chunkZ, random,
					new WorldGenMinable(ModDaemon.instance.blockCrystalOre.blockID, 3),
					5, 0, 25);
		}
	}

	public void generateOre(World world, int cX, int cZ, Random random,
			WorldGenMinable gen, int deposits, int minH, int maxH){
		for (int i = 0; i < deposits; i++) {
			int x = cX + random.nextInt(16);
			int z = cZ + random.nextInt(16);
			int y = random.nextInt(maxH) + random.nextInt(maxH) + (minH - maxH);
			gen.generate(world, random, x, y, z);
		}
	}

}
