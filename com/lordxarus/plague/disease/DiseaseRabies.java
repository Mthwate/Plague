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
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.DamageSourcePlague;
import com.lordxarus.plague.DiseaseHelper;
import com.lordxarus.plague.ModLogger;
import com.lordxarus.plague.TimeHelper;

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
	
	void effect(Entity entityCarrier) {

		Random rand = new Random();
		
		//attacks nearby players
		List<Entity> entities = entityCarrier.worldObj.getEntitiesWithinAABBExcludingEntity(entityCarrier, entityCarrier.boundingBox.expand(3.0D, 3.0D, 3.0D));
		for (Entity entityTarget : entities) {
			if (entityTarget instanceof EntityLiving || entityTarget instanceof EntityPlayer) {
				if (rand.nextInt(TimeHelper.timeToTick(20 * 1000, 0)) <= DiseaseHelper.getDiseaseDuration(entityCarrier, this)) {
					if (entityCarrier instanceof EntityLiving) {
						entityTarget.attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) entityCarrier), 1);
						ModLogger.log(Level.INFO, entityTarget.getEntityName() + " was attacked by " + entityCarrier.getEntityName() + " due to " + entityCarrier.getEntityName() + "'s rabies.", true);
					} else if (entityCarrier instanceof EntityPlayer) {
						entityTarget.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entityCarrier), 1);
						ModLogger.log(Level.INFO, entityTarget.getEntityName() + " was attacked by " + entityCarrier.getEntityName() + " due to " + entityCarrier.getEntityName() + "'s rabies.", true);
					}
				}
			}
		}
		
		//attacks self
		if (rand.nextInt(TimeHelper.timeToTick(20 * 1000, 0)) <= DiseaseHelper.getDiseaseDuration(entityCarrier, this)) {
			entityCarrier.attackEntityFrom(DamageSourcePlague.disease, 1);
			ModLogger.log(Level.INFO, entityCarrier.getEntityName() + " was hurt by " + entityCarrier.getEntityName() + "'s rabies.", true);
		}
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
