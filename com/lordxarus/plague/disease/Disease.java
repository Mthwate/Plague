package com.lordxarus.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class Disease {
	
	private String name;
	private String unlocalizedName;
	
	public Disease setUnlocalizedName(String newName) {
		this.unlocalizedName = newName;
		return(this);
	}
	
	public String getUnlocalizedName() {
		return(this.unlocalizedName);
	}
	
	public Disease setName(String newName) {
		this.name = newName;
		return(this);
	}
	
	public String getName() {
		return(this.name);
	}
	
	public void entityUpdate(LivingUpdateEvent event) {
	}
	
	//called when an entity is attacked
	public void entityAttack(LivingAttackEvent event) {
	}
	
	//checks if an entity can catch the disease
	public boolean isVulnerable(Entity entity) {
		return(false);
	}
}
