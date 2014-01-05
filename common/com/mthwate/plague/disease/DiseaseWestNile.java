package com.mthwate.plague.disease;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.mthwate.bookcase.EntityHelper;
import com.mthwate.plague.lib.DiseaseHelper;

public class DiseaseWestNile extends Disease {

	public DiseaseWestNile(double modifier) {
		super(modifier);
		addTarget(EntityPlayer.class);
		addTarget(EntityAnimal.class);
		addTarget(EntityTameable.class);
		addTarget(EntityVillager.class);
	}

	// randomly contracts disease
	void contract(Entity entity) {

		List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
		biomes.add(BiomeGenBase.jungle);
		biomes.add(BiomeGenBase.jungleHills);
		biomes.add(BiomeGenBase.swampland);

		if (biomes.contains(EntityHelper.getBiome(entity))) {
			DiseaseHelper.contract(entity, this, 1000000);
		}
	}

	void effect(Entity entity) {

		// weakens entity's attack
		DiseaseHelper.weakenAttribute(entity, this, SharedMonsterAttributes.attackDamage, 0.000004);

		// slows entity
		DiseaseHelper.weakenAttribute(entity, this, SharedMonsterAttributes.movementSpeed, 0.000004);

		// reduces knockback resistance
		DiseaseHelper.weakenAttribute(entity, this, SharedMonsterAttributes.knockbackResistance, 0.000004);
	}

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				contract(entity);
			}
		}
	}

}