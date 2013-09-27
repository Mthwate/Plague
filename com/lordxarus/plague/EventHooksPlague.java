package com.lordxarus.plague;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.disease.Disease;

public class EventHooksPlague {

	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityUpdate(event);
		}
		
		DiseaseHelper.count(event.entity);
	}

	@ForgeSubscribe
	public void onEntityAttack(LivingAttackEvent event) {
		for(Disease disease : Plague.diseases) {
			disease.entityAttack(event);
		}
	}
}
