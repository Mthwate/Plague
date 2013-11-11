package com.lordxarus.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.mthwate.bookcase.LangHelper;

public class Disease {
	
	private String unlocalizedName;
	
	public Disease setUnlocalizedName(String newName) {
		this.unlocalizedName = newName;
		return(this);
	}
	
	public String getUnlocalizedName() {
		return(this.unlocalizedName);
	}
	
	public String getName() {
		return(LangHelper.getLocalization("disease." + unlocalizedName + ".name"));
	}
	
	public void entityUpdate(LivingUpdateEvent event) {
	}
	
	//called when an entity is attacked
	public void entityAttack(LivingAttackEvent event) {
	}
	
	//called when an entity spawns
	public void entitySpawn(LivingSpawnEvent event) {
	}
	
	//called when an entity dies
	public void entityDeath(LivingDeathEvent event) {
	}
	
	//checks if an entity can catch the disease
	public boolean isVulnerable(Entity entity) {
		return(false);
	}
}
