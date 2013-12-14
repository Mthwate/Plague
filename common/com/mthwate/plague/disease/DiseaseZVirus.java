package com.mthwate.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseZVirus extends Disease {

	// called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 100);
	}

	// called when an entity dies
	@Override
	public void entityDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (DiseaseHelper.isDiseaseActive(entity, this)) {
			if (!(entity instanceof EntityZombie)) {
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				EntityZombie zombie = new EntityZombie(entity.worldObj);
				zombie.setPosition(x, y, z);
				entity.worldObj.spawnEntityInWorld(zombie);
			}
		}
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.spread(entity, this, 5.0, 10000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				if (event.entityLiving instanceof EntityZombie) {
					DiseaseHelper.contract(entity, this, 100000);
				}
			}
		}
	}

	// checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if (entity instanceof EntityPlayer || entity instanceof EntityVillager || entity instanceof EntityZombie) {
			return true;
		}
		return false;
	}
}
