package com.lordxarus.plague;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EventHooksPlague {

	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent event) {
	}

	@ForgeSubscribe
	public void onEntityAttack(LivingAttackEvent event) {
	}
}
