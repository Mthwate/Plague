package com.lordxarus.plague;

import net.minecraft.entity.Entity;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityHelper {
	
	public static BiomeGenBase getBiome(Entity entity) {
		return(entity.worldObj.getWorldChunkManager().getBiomeGenAt((int) entity.posX, (int) entity.posZ));
	}

}
