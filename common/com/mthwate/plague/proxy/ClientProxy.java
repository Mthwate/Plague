package com.mthwate.plague.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderSnowball;

import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponizedDisease.class, new RenderSnowball(ItemPlague.weaponizedDisease));
	}

	@Override
	public void playSound(String sound, float x, float y, float z, float volume, float pitch) {
		Minecraft.getMinecraft().sndManager.playSound(sound, x, y, z, volume, pitch);
	}
	
	@Override
	public void spawnParticle(String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		player.worldObj.spawnParticle(particle, posX, posY, posZ, velX, velY, velZ);
	}

}
