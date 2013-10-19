package com.lordxarus.plague;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.DiseaseHelper;

public class EventHooksPlague {

	@ForgeSubscribe
	//called when an entity updates (every tick)
	public void onEntityUpdate(LivingUpdateEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityUpdate(event);
		}
		
		//increases the duration of all active diseases
		DiseaseHelper.count(event.entity);
	}

	@ForgeSubscribe
	//called when an entity is attacked
	public void onEntityAttack(LivingAttackEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityAttack(event);
		}
	}

	@ForgeSubscribe
	//called when an entity spawns
	public void onEntitySpawn(LivingSpawnEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entitySpawn(event);
		}
	}

	@ForgeSubscribe
	//called when an entity spawns
	public void onEntityDeath(LivingDeathEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityDeath(event);
		}
	}
}
