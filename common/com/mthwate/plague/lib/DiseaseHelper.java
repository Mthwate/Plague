package com.mthwate.plague.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;

public class DiseaseHelper {

	// gives the entity the specified disease
	public static void addDisease(Entity entity, Disease disease) {
		if (disease != null) {
			if (!isDiseaseActive(entity, disease)) {
				entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), 1);
				Plague.logger.log(Level.INFO, disease.getName() + " added to " + entity.getEntityName() + ".", true);
			}
		}
	}

	// gives the entity a small chance of catching the disease Randomly
	public static void contract(Entity entity, Disease disease, int modifier) {
		if (Plague.rand.nextInt((int) (modifier * disease.getModifier())) == 0) {
			DiseaseHelper.addDisease(entity, disease);
			Plague.logger.log(Level.INFO, entity.getEntityName() + " contracted " + disease.getName().toLowerCase() + "!", true);
		}
	}

	// increases the duration (in ticks) of all diseases an entity has by 1
	public static void count(Entity entity) {
		List<Disease> diseases = getActiveDiseases(entity);
		for (Disease disease : diseases) {
			setDiseaseDuration(entity, disease, getDiseaseDuration(entity, disease) + 1);
		}
	}

	// gets all diseases and entity has
	public static List<Disease> getActiveDiseases(Entity entity) {
		List<Disease> active = new ArrayList<Disease>();
		for (Disease disease : Plague.diseases) {
			if (isDiseaseActive(entity, disease)) {
				active.add(disease);
			}
		}
		return active;
	}

	// gets the length of time an entity has had a disease
	public static int getDiseaseDuration(Entity entity, Disease disease) {
		return entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName());
	}

	public static Disease getDiseaseFromString(String diseaseName) {
		for (Disease disease : Plague.diseases) {
			if (disease.getUnlocalizedName().equalsIgnoreCase(diseaseName)) {
				return disease;
			}
		}
		return null;
	}

	// checks if the entity has the specified disease
	public static boolean isDiseaseActive(Entity entity, Disease disease) {
		return entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()) > 0;
	}

	// sets the length of time an entity has had a disease
	public static void setDiseaseDuration(Entity entity, Disease disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), duration);
	}

	// gives an infected entity a chance to spread their disease to others within the radius
	public static void spread(Entity entityCarrier, Disease disease, double radius, int modifier) {
		@SuppressWarnings("unchecked")
		List<Entity> entities = entityCarrier.worldObj.getEntitiesWithinAABBExcludingEntity(entityCarrier, entityCarrier.boundingBox.expand(radius, radius, radius));
		for (Entity entity : entities) {
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entityTarget = (EntityLivingBase) entity;
				if (disease.isVulnerable(entityTarget) && !DiseaseHelper.isDiseaseActive(entityTarget, disease)) {
					if (Plague.rand.nextInt((int) (modifier * disease.getModifier())) == 0) {
						DiseaseHelper.addDisease(entityTarget, disease);
						Plague.logger.log(Level.INFO, entityTarget.getEntityName() + " contracted " + disease.getName().toLowerCase() + " from " + entityCarrier.getEntityName() + "!", true);
					}
				}
			}
		}
	}

	// gives an attacked entity a chance of catching its attacker's disease
	public static void spreadByAttack(LivingAttackEvent event, Disease disease, int modifier) {

		// gets the attacker and the victim of the attack
		EntityLivingBase entityVictim = event.entityLiving;
		Entity entityAttacker = event.source.getEntity();

		// checks if the entity is able to contract the disease and does not already have it
		if (disease.isVulnerable(entityVictim) && !DiseaseHelper.isDiseaseActive(entityVictim, disease)) {

			if (event.source.getEntity() != null) {
				if (event.source.getEntity() instanceof EntityLiving) {
					if (DiseaseHelper.isDiseaseActive(entityAttacker, disease)) {
						if (Plague.rand.nextInt((int) (modifier * disease.getModifier())) == 0) {
							addDisease(entityVictim, disease);
							Plague.logger.log(Level.INFO, entityVictim.getEntityName() + " contracted " + disease.getName().toLowerCase() + " from " + entityAttacker.getEntityName() + "!", true);
						}
					}
				}
			}

		}
	}

	// weakens the specified attribute of an entity
	public static void weakenAttribute(Entity entityInfected, Disease disease, Attribute attribute, double modifier) {
		if (entityInfected instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityInfected;
			if (entity.getAttributeMap().getAllAttributes().contains(attribute)) {
				AttributeInstance attributeInstance = entity.getEntityAttribute(attribute);
				attributeInstance.setAttribute(attributeInstance.getBaseValue() - modifier * attributeInstance.getBaseValue() * getDiseaseDuration(entity, disease));
			}
		}
	}
	
	public static void setDamaged(Entity entityInfected) {
		if (entityInfected instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) entityInfected;
			entity.getEntityData().setInteger(Plague.modid + ".damaged.cooldown", (int) TimeHelper.timeToTick(5));
		}
	}
	
	public static void reduceDamage(Entity entityInfected) {
		if (entityInfected instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) entityInfected;
			entity.getEntityData().setInteger(Plague.modid + ".damaged.cooldown", entity.getEntityData().getInteger(Plague.modid + ".damaged.cooldown"));
		}
	}

	public static boolean wasDamaged(Entity entityInfected) {
		if (entityInfected instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) entityInfected;
			return(entity.getEntityData().getInteger(Plague.modid + ".damaged.cooldown") > 0);
		}
		return true;
	}
	
	//sets the strain of the specified entity's disease
	public static void setStrain(Entity entity, Disease disease, String strain) {
		if (disease != null) {
			entity.getEntityData().setString(Plague.modid + ".disease." + disease.getUnlocalizedName() + ".strain", strain);
		}
	}
	
	// generates a random 5 digit string to represent the strain
	public static String getNewStrain() {
		String strain = "";
		for (int i=0; i<5; i++) {
			strain += Plague.rand.nextInt(10);
		}
		return strain;
	}
}
