package com.mthwate.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseVampyra extends Disease {

	public DiseaseVampyra(double modifier) {
		super(modifier);
		addTarget(EntityPlayer.class);
		addTarget(EntityVillager.class);
	}

	void effect(Entity entityCarrier) {
		if(entityCarrier.worldObj.isDaytime()) {
			if (entityCarrier.getBrightness(1.0F) > 0.5 && entityCarrier.worldObj.canBlockSeeTheSky((int) entityCarrier.posX, (int) entityCarrier.posY, (int) entityCarrier.posZ)) {
				entityCarrier.setFire((int) (TimeHelper.tickToMc(DiseaseHelper.getDiseaseDuration(entityCarrier, this))/(60*60*24)));
				DiseaseHelper.setDamaged(entityCarrier);
			}
		}

		// weakens entity's attack
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.attackDamage, 0.000005);

		// slows entity
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.movementSpeed, 0.000005);

		// reduces knockback resistance
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.knockbackResistance, 0.000005);
	}

	// called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 200);
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				DiseaseHelper.spread(entity, this, 5.0, 25000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.contract(entity, this, 1000000000);
			}
		}
	}

}
