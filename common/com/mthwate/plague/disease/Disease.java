package com.mthwate.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.mthwate.bookcase.LangHelper;

public class Disease {

	private String unlocalizedName;

	// called when an entity is attacked
	public void entityAttack(LivingAttackEvent event) {}

	// called when an entity dies
	public void entityDeath(LivingDeathEvent event) {}

	// called when an entity spawns
	public void entitySpawn(LivingSpawnEvent event) {}

	public void entityUpdate(LivingUpdateEvent event) {}

	public String getName() {
		return LangHelper.getLocalization("disease." + unlocalizedName + ".name");
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	// checks if an entity can catch the disease
	public boolean isVulnerable(Entity entity) {
		return false;
	}

	public Disease setUnlocalizedName(String newName) {
		unlocalizedName = newName;
		return this;
	}
}
