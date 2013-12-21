package com.mthwate.plague.disease;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.mthwate.bookcase.LangHelper;

public class Disease {

	private String unlocalizedName;
	
	private List<Class<? extends EntityLivingBase>> targets;
	
	private double luckModifier;
	
	public Disease(double modifier) {
		luckModifier = modifier;
		targets = new ArrayList<Class<? extends EntityLivingBase>>();
	}

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
	public boolean isVulnerable(EntityLivingBase entity) {
		for (Class<? extends EntityLivingBase> target : targets) {
			if (target.isInstance(entity)) {
				return true;
			}
		}
		
		return false;
	}

	public Disease setUnlocalizedName(String newName) {
		unlocalizedName = newName;
		return this;
	}
	
	public void addTarget(Class<? extends EntityLivingBase> target) {
		targets.add(target);
	}
	
	public double getModifier() {
		return luckModifier;
	}
}
