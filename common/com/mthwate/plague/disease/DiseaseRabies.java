package com.mthwate.plague.disease;

import java.util.List;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.DamageSourcePlague;
import com.mthwate.plague.Plague;
import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseRabies extends Disease {
	
	public DiseaseRabies(double modifier) {
		super(modifier);
		addTarget(EntityPlayer.class);
		addTarget(EntityAnimal.class);
		addTarget(EntityTameable.class);
		addTarget(EntityVillager.class);
	}

	void effect(EntityLivingBase entityCarrier) {

		// attacks nearby players
		@SuppressWarnings("unchecked")
		List<Entity> entities = entityCarrier.worldObj.getEntitiesWithinAABBExcludingEntity(entityCarrier, entityCarrier.boundingBox.expand(3.0D, 3.0D, 3.0D));
		for (Entity entityTarget : entities) {
			if (entityTarget instanceof EntityLiving || entityTarget instanceof EntityPlayer) {
				if (Plague.rand.nextInt((int) TimeHelper.mcToTick(1000, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entityCarrier, this)) {
					entityCarrier.swingItem();
					entityTarget.attackEntityFrom(DamageSource.causeMobDamage(entityCarrier), 1);
					Plague.logger.log(Level.INFO, entityTarget.getEntityName() + " was attacked by " + entityCarrier.getEntityName() + " due to " + entityCarrier.getEntityName() + "'s " + getName() + ".", true);
				}
			}
		}

		// attacks self
		if (Plague.rand.nextInt((int) TimeHelper.mcToTick(1000, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entityCarrier, this)) {
			entityCarrier.attackEntityFrom(DamageSourcePlague.disease, 1);
			Plague.logger.log(Level.INFO, entityCarrier.getEntityName() + " was hurt by " + entityCarrier.getEntityName() + "'s " + getName() + ".", true);
		}

		// weakens entity's attack
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.attackDamage, 0.000003);

		// slows entity
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.movementSpeed, 0.000003);

		// reduces knockback resistance
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.knockbackResistance, 0.000003);
	}

	// called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 100);
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				DiseaseHelper.spread(entity, this, 5.0, 10000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				DiseaseHelper.contract(entity, this, 100000000);
			}
		}
	}

}
