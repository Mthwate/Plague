package com.lordxarus.plague.disease;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.DiseaseHelper;
import com.lordxarus.plague.ModLogger;

public class DiseaseRabies extends Disease {
	
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if(isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				spread(entity);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				contract(entity);
			}
		}
	}
	
	void effect(Entity entity) {
		
	}
	
	void spread(Entity entityCarrier) {
		List<Entity> entities = entityCarrier.worldObj.getEntitiesWithinAABBExcludingEntity(entityCarrier, entityCarrier.boundingBox.expand(5.0D, 5.0D, 5.0D));
		for (Entity entityTarget : entities) {
			if (isVulnerable(entityTarget) && !DiseaseHelper.isDiseaseActive(entityTarget, this)) {
				Random rand = new Random();
				if (rand.nextInt(100000) == 0) {
					DiseaseHelper.addDisease(entityCarrier, this);
					ModLogger.log(Level.INFO, entityTarget.getEntityName() + " contracted rabies from " + entityCarrier.getEntityName() + "!", true);
				}
			}
		}
		
	}
	
	void contract(Entity entity) {
		Random rand = new Random();
		if (rand.nextInt(100000000) == 0) {
			DiseaseHelper.addDisease(entity, this);
			ModLogger.log(Level.INFO, entity.getEntityName() + " contracted rabies!", true);
		}
	}
	
	public void entityAttack(LivingAttackEvent event) {
		Entity entityVictim = event.entity;
		Entity entityAttacker = event.source.getEntity();
		if (isVulnerable(entityVictim) && !DiseaseHelper.isDiseaseActive(entityVictim, this)) {
			if (event.source.getEntity() != null) {
				if (event.source.getEntity() instanceof EntityLiving) {
					if(DiseaseHelper.isDiseaseActive(entityAttacker, this)) {
						Random rand = new Random();
						if (rand.nextInt(100) == 0) {
							DiseaseHelper.addDisease(entityVictim, this);
							ModLogger.log(Level.INFO, entityVictim.getEntityName() + " contracted rabies from " + entityAttacker.getEntityName() + "!", true);
						}
					}
				}
			}
		}
	}
	
	boolean isVulnerable(Entity entity) {
		if(
			entity instanceof EntityPlayer ||
			entity instanceof EntityAnimal ||
			entity instanceof EntityTameable ||
			entity instanceof EntityVillager
		) {
			return(true);
		} else {
			return(false);
		}
	}
	
}
