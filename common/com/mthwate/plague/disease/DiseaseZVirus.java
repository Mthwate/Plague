package com.mthwate.plague.disease;

import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.DamageSourcePlague;
import com.mthwate.plague.Plague;
import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseZVirus extends Disease {

	void effect(Entity entityCarrier) {

		// damages self
		if (Plague.rand.nextInt((int) TimeHelper.mcToTick(2500, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entityCarrier, this)) {
			entityCarrier.attackEntityFrom(DamageSourcePlague.disease, 3);
			Plague.logger.log(Level.INFO, entityCarrier.getEntityName() + " was hurt by " + entityCarrier.getEntityName() + "'s " + getName() + ".", true);
		}

		// weakens entity's attack
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.attackDamage, 0.000004);

		// slows entity
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.movementSpeed, 0.000004);

		// reduces knockback resistance
		DiseaseHelper.weakenAttribute(entityCarrier, this, SharedMonsterAttributes.knockbackResistance, 0.000004);
	}

	// called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 100);
	}

	// called when an entity dies
	@Override
	public void entityDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (DiseaseHelper.isDiseaseActive(entity, this)) {
			if (!(entity instanceof EntityZombie)) {
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				EntityZombie zombie = new EntityZombie(entity.worldObj);
				zombie.setPosition(x, y, z);
				entity.worldObj.spawnEntityInWorld(zombie);
			}
		}
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				DiseaseHelper.spread(entity, this, 5.0, 10000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				if (event.entityLiving instanceof EntityZombie) {
					DiseaseHelper.contract(entity, this, 100000);
				}
			}
		}
	}

	// checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if (entity instanceof EntityPlayer || entity instanceof EntityVillager || entity instanceof EntityZombie) {
			return true;
		}
		return false;
	}
}
