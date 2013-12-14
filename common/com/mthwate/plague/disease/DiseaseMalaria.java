package com.mthwate.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseMalaria extends Disease {

	void effect(Entity entityCarrier) {}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.contract(entity, this, 100000000);
			}
		}
	}

	// checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if (entity instanceof EntityPlayer || entity instanceof EntityAnimal || entity instanceof EntityTameable || entity instanceof EntityVillager) {
			return true;
		}
		return false;
	}

}
