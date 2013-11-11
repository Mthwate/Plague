package com.lordxarus.plague.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.disease.Disease;
import com.mthwate.bookcase.Rand;

public class DiseaseHelper {
	
	//gives the entity the specified disease
	public static void addDisease(Entity entity, Disease disease) {
		if (!isDiseaseActive(entity, disease)) {
			entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), 1);
			Plague.logger.log(Level.INFO, disease.getName() + " added to " + entity.getEntityName() + ".", true);
		}
	}
	
	//checks if the entity has the specified disease
	public static boolean isDiseaseActive(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()) > 0);
	}

	//sets the length of time an entity has had a disease
	public static void setDiseaseDuration(Entity entity, Disease disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), duration);
	}

	//gets the length of time an entity has had a disease
	public static int getDiseaseDuration(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()));
	}
	
	//gets all diseases and entity has
	public static List<Disease> getActiveDiseases(Entity entity) {
		List<Disease> active = new ArrayList();
		for(Disease disease : Plague.diseases) {
			if (isDiseaseActive(entity, disease)) {
				active.add(disease);
			}
		}
		return(active);
	}
	
	//increases the duration of all diseases an entity has by 1 (the duration is measured in ticks)
	public static void count(Entity entity) {
		List<Disease> diseases = getActiveDiseases(entity);
		for(Disease disease : diseases) {
			setDiseaseDuration(entity, disease, getDiseaseDuration(entity, disease) + 1);
		}
	}
	
	//gives an attacked entity a chance of catching its attacker's disease
	public static void spreadByAttack(LivingAttackEvent event, Disease disease, int modifier) {
		
		//gets the attacker and the victim of the attack
		Entity entityVictim = event.entity;
		Entity entityAttacker = event.source.getEntity();
		
		//checks if the entity is able to contract the disease and does not already have it
		if (disease.isVulnerable(entityVictim) && !DiseaseHelper.isDiseaseActive(entityVictim, disease)) {
			
			if (event.source.getEntity() != null) {
				if (event.source.getEntity() instanceof EntityLiving) {
					if(DiseaseHelper.isDiseaseActive(entityAttacker, disease)) {
						if (Rand.nextInt(modifier) == 0) {
							addDisease(entityVictim, disease);
							Plague.logger.log(Level.INFO, entityVictim.getEntityName() + " contracted " + disease.getName().toLowerCase() + " from " + entityAttacker.getEntityName() + "!", true);
						}
					}
				}
			}
			
		}
	}
	
	//weakens the specified attribute of an entity
	public static void weakenAttribute(Entity entityInfected, Disease disease, Attribute attribute, double modifier) {
		if (entityInfected instanceof EntityLivingBase) {
			EntityLivingBase entity = ((EntityLivingBase)entityInfected);
			if (entity.getAttributeMap().getAllAttributes().contains(attribute)) {
				AttributeInstance attributeInstance = entity.getEntityAttribute(attribute);
				attributeInstance.setAttribute(attributeInstance.getBaseValue() - (modifier * attributeInstance.getBaseValue() * getDiseaseDuration(entity, disease)));
			}
		}
	}
	
	//gives the entity a small chance of catching the disease Randomly
	public static void contract(Entity entity, Disease disease, int modifier) {
		if (Rand.nextInt(modifier) == 0) {
			DiseaseHelper.addDisease(entity, disease);
			Plague.logger.log(Level.INFO, entity.getEntityName() + " contracted " + disease.getName().toLowerCase() + "!", true);
		}
	}
	
	//gives an infected entity a chance to spread their disease to others within the radius
	public static void spread(Entity entityCarrier, Disease disease, double radius, int modifier) {
		List<Entity> entities = entityCarrier.worldObj.getEntitiesWithinAABBExcludingEntity(entityCarrier, entityCarrier.boundingBox.expand(radius, radius, radius));
		for (Entity entityTarget : entities) {
			if (disease.isVulnerable(entityTarget) && !DiseaseHelper.isDiseaseActive(entityTarget, disease)) {
				if (Rand.nextInt(modifier) == 0) {
					DiseaseHelper.addDisease(entityTarget, disease);
					Plague.logger.log(Level.INFO, entityTarget.getEntityName() + " contracted " + disease.getName().toLowerCase() + " from " + entityCarrier.getEntityName() + "!", true);
				}
			}
		}
	}
}
