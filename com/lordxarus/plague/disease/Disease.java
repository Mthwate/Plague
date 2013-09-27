package com.lordxarus.plague.disease;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class Disease {
	
	public static String unlocalizedName;
	
	public Disease setUnlocalizedName(String newName) {
		unlocalizedName = newName;
		return(this);
	}
	
	public void entityUpdate(LivingUpdateEvent event) {
	}
	
	public void entityAttack(LivingAttackEvent event) {
	}
}
