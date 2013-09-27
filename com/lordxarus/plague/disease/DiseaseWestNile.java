package com.lordxarus.plague.disease;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.DiseaseHelper;
import com.lordxarus.plague.EntityHelper;
import com.lordxarus.plague.ModLogger;

public class DiseaseWestNile extends Disease {
	
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if(isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				contract(entity);
			}
		}
	}
	
	void effect(Entity entity) {
		weaken(entity);
	}
	
	void weaken(Entity entity) {
		if (entity instanceof EntityLiving) {
			EntityLiving entityL = ((EntityLiving)entity);
			AttributeInstance attackDamage = entityL.getEntityAttribute(SharedMonsterAttributes.attackDamage);
			AttributeInstance movementSpeed = entityL.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			double attackDamageBase = attackDamage.getBaseValue();
			double movementSpeedBase = movementSpeed.getBaseValue();
			attackDamage.setAttribute(attackDamageBase - (0.000004 * attackDamageBase * DiseaseHelper.getDiseaseDuration(entityL, this)));
			movementSpeed.setAttribute(movementSpeedBase - (0.000004 * movementSpeedBase * DiseaseHelper.getDiseaseDuration(entityL, this)));
		}
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityP = ((EntityPlayer)entity);
			AttributeInstance attackDamage = entityP.getEntityAttribute(SharedMonsterAttributes.attackDamage);
			AttributeInstance movementSpeed = entityP.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			double attackDamageBase = attackDamage.getBaseValue();
			double movementSpeedBase = movementSpeed.getBaseValue();
			attackDamage.setAttribute(attackDamageBase - (0.000004 * attackDamageBase * DiseaseHelper.getDiseaseDuration(entityP, this)));
			movementSpeed.setAttribute(movementSpeedBase - (0.000004 * movementSpeedBase * DiseaseHelper.getDiseaseDuration(entityP, this)));
		}
	}
	
	//randomly contracts disease
	void contract(Entity entity) {
		
		List<BiomeGenBase> biomes = new ArrayList();
		biomes.add(BiomeGenBase.jungle);
		biomes.add(BiomeGenBase.jungleHills);
		biomes.add(BiomeGenBase.swampland);
		
		if (biomes.contains(EntityHelper.getBiome(entity))) {
			Random rand = new Random();
			if (rand.nextInt(1000000) == 0) {
				DiseaseHelper.addDisease(entity, this);
				ModLogger.log(Level.INFO, entity.getEntityName() + " contracted west nile virus!", true);
			}
		}
	}
	
	boolean isVulnerable(Entity entity) {
		if(
			entity instanceof EntityPlayer
			||
			entity instanceof EntityAnimal
			||
			entity instanceof EntityTameable
			||
			entity instanceof EntityVillager
		) {
			return(true);
		} else {
			return(false);
		}
	}
	
}
