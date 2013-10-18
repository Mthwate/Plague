package com.lordxarus.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.lordxarus.plague.lib.DiseaseHelper;
import com.mthwate.bookcase.TimeHelper;

public class DiseaseCrepis extends Disease {

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if(isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.spread(entity, this, 6.0, 250);
			}
		}
	}
	
	//called when an entity dies
	@Override
	public void entityDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (!(entity instanceof EntityCreeper)) {
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
			int tickDuration = DiseaseHelper.getDiseaseDuration(entity, this);
			double mcDuration  = TimeHelper.tickToMc(tickDuration);
			int strength = (int) (mcDuration / 24);
			if (strength > 14) {
				strength = 14;
			}
			entity.worldObj.createExplosion(entity, x, y, z, strength, true);
		}
	}
	
	//called when an entity spawn
	@Override
	public void entitySpawn(LivingSpawnEvent event) {
		if (event.entityLiving instanceof EntityCreeper) {
			DiseaseHelper.contract(event.entityLiving, this, 100);
		}
	}
	
	//called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 100);
	}
	
	//checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if(
			entity instanceof EntityLiving
		) {
			return(true);
		} else {
			return(false);
		}
	}

}
