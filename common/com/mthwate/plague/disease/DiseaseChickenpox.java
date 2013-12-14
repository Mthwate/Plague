package com.mthwate.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseChickenpox extends Disease {

	void effect(Entity entityCarrier) {

		// weakens entity's attack
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.attackDamage, 0.0000025);

		// slows entity
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.movementSpeed, 0.0000025);

		// reduces knockback resistance
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.knockbackResistance, 0.0000025);
	}

	// called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 75);
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				DiseaseHelper.spread(entity, this, 5.0, 1000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.contract(entity, this, 1000000);
			}
		}
	}

	// checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if (entity instanceof EntityPlayer || entity instanceof EntityVillager) {
			return true;
		}
		return false;
	}

}
