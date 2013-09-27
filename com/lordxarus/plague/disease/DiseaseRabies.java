package com.lordxarus.plague.disease;

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
	}
	
	public void entityAttack(LivingAttackEvent event) {
		Entity entityVictim = event.entity;
		Entity entityAttacker = event.source.getEntity();
		if (isVulnerable(entityVictim)) {
			if (event.source.getEntity() != null) {
				if (event.source.getEntity() instanceof EntityLiving) {
					if(DiseaseHelper.isDiseaseActive(entityAttacker, this)) {
						Random rand = new Random();
						if (rand.nextInt(100) == 0) {
							DiseaseHelper.addDisease(entityVictim, this);
							ModLogger.log(Level.INFO, entityVictim.getEntityName() + " contracted rabies from " + entityAttacker.getEntityName(), true);
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
