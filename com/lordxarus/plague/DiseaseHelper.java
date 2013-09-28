package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.lordxarus.plague.disease.Disease;

public class DiseaseHelper {
	
	public static void addDisease(Entity entity, Disease disease) {
		if (!isDiseaseActive(entity, disease)) {
			entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), 1);
			ModLogger.log(Level.INFO, disease.getUnlocalizedName() + " added to " + entity.getEntityName() + ".", true);
		}
	}
	
	public static boolean isDiseaseActive(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()) > 0);
	}
	
	public static void setDiseaseDuration(Entity entity, Disease disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), duration);
	}
	
	public static int getDiseaseDuration(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()));
	}
	
	public static List<Disease> getActiveDiseases(Entity entity) {
		List<Disease> active = new ArrayList();
		for(Disease disease : Plague.diseases) {
			if (isDiseaseActive(entity, disease)) {
				active.add(disease);
			}
		}
		return(active);
	}
	
	static void count(Entity entity) {
		List<Disease> diseases = getActiveDiseases(entity);
		for(Disease disease : diseases) {
			setDiseaseDuration(entity, disease, getDiseaseDuration(entity, disease) + 1);
		}
	}
	
	public static void spreadByAttack(LivingAttackEvent event, Disease disease, int modifier) {
		Entity entityVictim = event.entity;
		Entity entityAttacker = event.source.getEntity();
		if (disease.isVulnerable(entityVictim) && !DiseaseHelper.isDiseaseActive(entityVictim, disease)) {
			if (event.source.getEntity() != null) {
				if (event.source.getEntity() instanceof EntityLiving) {
					if(DiseaseHelper.isDiseaseActive(entityAttacker, disease)) {
						Random rand = new Random();
						if (rand.nextInt(modifier) == 0) {
							addDisease(entityVictim, disease);
							ModLogger.log(Level.INFO, entityVictim.getEntityName() + " contracted " + disease.name.toLowerCase().toLowerCase() + " from " + entityAttacker.getEntityName() + "!", true);
						}
					}
				}
			}
		}
	}
	
	public static void weakenAttribute(Entity entityInfected, Disease disease, Attribute attribute, double modifier) {
		if (entityInfected instanceof EntityLivingBase) {
			EntityLivingBase entity = ((EntityLivingBase)entityInfected);
			AttributeInstance attributeInstance = entity.getEntityAttribute(attribute);
			attributeInstance.setAttribute(attributeInstance.getBaseValue() - (modifier * attributeInstance.getBaseValue() * getDiseaseDuration(entity, disease)));
		}
	}
	
	public static void contract(Entity entity, Disease disease, int modifier) {
		Random rand = new Random();
		if (rand.nextInt(modifier) == 0) {
			DiseaseHelper.addDisease(entity, disease);
			ModLogger.log(Level.INFO, entity.getEntityName() + " contracted " + disease.name.toLowerCase() + "!", true);
		}
	}
}
