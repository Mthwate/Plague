package com.lordxarus.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.lib.DiseaseHelper;
import com.mthwate.bookcase.TimeHelper;

public class DiseaseCrepis extends Disease {

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if(isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.spread(entity, this, 5.0, 1000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				if (event.entityLiving instanceof EntityCreeper) {
					DiseaseHelper.contract(entity, this, 10000);
				}
			}
		}
	}
	
	//called when an entity dies
	@Override
	public void entityDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (DiseaseHelper.isDiseaseActive(entity, this)) {
			if (!(entity instanceof EntityCreeper)) {
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				int tickDuration = DiseaseHelper.getDiseaseDuration(entity, this);
				double mcDuration  = TimeHelper.tickToMc(tickDuration);
				int strength = (int) (mcDuration / (48 * 60 * 60));
				if (strength > 7) {
					strength = 7;
				}
				entity.worldObj.createExplosion(entity, x, y, z, strength, true);
			}
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
			entity instanceof EntityLivingBase
		) {
			return(true);
		} else {
			return(false);
		}
	}

}
