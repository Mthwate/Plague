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

public class DiseaseMalaria extends Disease {
	
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
	
	void effect(Entity entityCarrier) {

		Random rand = new Random();
		
	}
	
	
	void contract(Entity entity) {
		Random rand = new Random();
	//	if (entity.)
		if (rand.nextInt(100000000) == 0) {
			DiseaseHelper.addDisease(entity, this);
			ModLogger.log(Level.INFO, entity.getEntityName() + " contracted rabies!", true);
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
