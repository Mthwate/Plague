package com.lordxarus.plague.disease;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class DiseaseWestNile extends Disease {
	
	public void entityUpdate(LivingUpdateEvent event) {
	}
	
	public void entityAttack(LivingAttackEvent event) {
	}
	
	boolean isVulnerable(Entity entity) {
		if(
			entity instanceof EntityPlayer
			||
			entity instanceof EntityAnimal
			||
			entity instanceof EntityTameable
			||
			entity instanceof EntityVillager
		) {
			return(true);
		} else {
			return(false);
		}
	}
	
}
