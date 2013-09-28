package com.lordxarus.plague;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityHelper {
	
	public static BiomeGenBase getBiome(Entity entity) {
		return(entity.worldObj.getWorldChunkManager().getBiomeGenAt((int) entity.posX, (int) entity.posZ));
	}
	
	public static boolean getBlock(Entity entity, int radius, Block block) {
		int eX = (int) entity.posX;
		int eY = (int) entity.posY;
		int eZ = (int) entity.posZ;
		
		for (int x = eX - radius; x <= eX + radius; x++) {
			for (int y = eY - radius; y <= eY + radius; y++) {
				for (int z = eZ - radius; z <= eZ + radius; z++) {
					if (entity.worldObj.getBlockId(x, y, z) == block.blockID) {
						return(true);
					}
				}
			}
		}
		
		return(false);
	}

}
