package com.lordxarus.plague;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.DiseaseHelper;

public class EventHooksPlague {

	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityUpdate(event);
		}
		
		DiseaseHelper.count(event.entity);
	}

	@ForgeSubscribe
	//called when an entity is attacked
	public void onEntityAttack(LivingAttackEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityAttack(event);
		}
	}
}
