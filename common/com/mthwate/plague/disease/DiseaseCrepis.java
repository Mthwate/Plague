package com.mthwate.plague.disease;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.DamageSourcePlague;
import com.mthwate.plague.Plague;
import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseCrepis extends Disease {

	public DiseaseCrepis(double modifier) {
		super(modifier);
		addTarget(EntityLivingBase.class);
	}

	public void effect(EntityLivingBase entity) {
		if (!(entity instanceof EntityCreeper)) {
			if (Plague.rand.nextInt((int) TimeHelper.mcToTick(100000, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entity, this)) {

				entity.attackEntityFrom(DamageSourcePlague.disease, 0.1F);
				Plague.proxy.playSound(entity.worldObj, "mob.creeper.say", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 2.0F, 1.0F);
				int hissCount = entity.getEntityData().getInteger(Plague.modid + ".disease." + getUnlocalizedName() + ".hissCount") + 1;
				entity.getEntityData().setInteger(Plague.modid + ".disease." + getUnlocalizedName() + ".hissCount", hissCount);
				if (hissCount >= 5) {
					entity.attackEntityFrom(DamageSourcePlague.disease, entity.getHealth() + entity.getMaxHealth());
				}

			}
		}
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
			if (!(entity instanceof EntityCreeper)) {
				int tickDuration = DiseaseHelper.getDiseaseDuration(entity, this);
				double mcDuration = TimeHelper.tickToMc(tickDuration);
				int strength = (int) (mcDuration / (48 * 60 * 60));
				if (strength > 0) {
					if (strength > 10) {
						strength = 10;
					}
					double x = entity.posX;
					double y = entity.posY;
					double z = entity.posZ;
					entity.worldObj.createExplosion(entity, x, y, z, strength, true);
				}
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
				if (event.entityLiving instanceof EntityCreeper) {
					DiseaseHelper.contract(entity, this, 100000);
				}
			}
		}
	}

}
