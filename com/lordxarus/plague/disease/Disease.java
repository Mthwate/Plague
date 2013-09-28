package com.lordxarus.plague.disease;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class Disease {
	
	private String unlocalizedName;
	
	public Disease setUnlocalizedName(String newName) {
		this.unlocalizedName = newName;
		return(this);
	}
	
	public String getUnlocalizedName() {
		return(this.unlocalizedName);
	}
	
	public void entityUpdate(LivingUpdateEvent event) {
	}
	
	public void entityAttack(LivingAttackEvent event) {
	}
}
