package com.lordxarus.plague.disease;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class Disease {
	
	public static String name;
	
	public Disease setName(String newName) {
		name = newName;
		return(this);
	}
}
