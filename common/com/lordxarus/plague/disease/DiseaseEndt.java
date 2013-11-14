package com.lordxarus.plague.disease;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.lib.DiseaseHelper;
import com.mthwate.bookcase.TimeHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DiseaseEndt extends Disease {

	@Override
	public void entityUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if(isVulnerable(entity)) {
			if (DiseaseHelper.isDiseaseActive(entity, this)) {
				effect(entity);
				DiseaseHelper.spread(entity, this, 5.0, 10000);
			} else if (!DiseaseHelper.isDiseaseActive(entity, this)) {
				if (event.entityLiving instanceof EntityEnderman) {
					DiseaseHelper.contract(entity, this, 100000);
				}
			}
		}
	}
	
	public void effect(EntityLivingBase entity) {
		if (!(entity instanceof EntityEnderman)) {
			if (Plague.rand.nextInt((int) TimeHelper.mcToTick(100000, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entity, this)) {
				
				int radius = 10;//TODO dynamic
				int x = (int) (entity.posX + (Plague.rand.nextInt((radius*2)+1) - radius));
				int y = (int) (entity.posY + (Plague.rand.nextInt((radius*2)+1) - radius));
				int z = (int) (entity.posZ + (Plague.rand.nextInt((radius*2)+1) - radius));
				
				if ((entity.worldObj.getBlockId(x, y, z) == 0) && (entity.worldObj.getBlockId(x, y + 1, z) == 0)) {
					entity.setPositionAndUpdate(x + 0.5, y, z + 0.5);
					Minecraft.getMinecraft().sndManager.playSound("mob.endermen.portal", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 2.0F, 1.0F);
					//TODO add particles
				}
			}
			
			if (entity instanceof EntityPlayer) {
				if (Plague.rand.nextInt((int) TimeHelper.mcToTick(10000, 0, 0, 0)) <= DiseaseHelper.getDiseaseDuration(entity, this)) {
					EntityPlayer entityPlayer = (EntityPlayer) entity;
					int invSize = entityPlayer.inventory.getSizeInventory();
					
					int slotOne = Plague.rand.nextInt(invSize);
					int slotTwo = Plague.rand.nextInt(invSize + 1);
					
					ItemStack stackOne;
					ItemStack stackTwo;
					
					boolean exec = false;
					
					if (slotTwo == invSize) {
						exec = (entityPlayer.inventory.getStackInSlot(slotOne) != null);
						stackOne = null;
						stackTwo = null;
						slotTwo = slotOne;
					} else {
						stackOne = entityPlayer.inventory.getStackInSlot(slotOne);
						stackTwo = entityPlayer.inventory.getStackInSlot(slotTwo);
						exec = ((stackOne != null) || (stackTwo != null));
					}

					if (exec) {
						entityPlayer.inventory.setInventorySlotContents(slotOne, stackTwo);
						entityPlayer.inventory.setInventorySlotContents(slotTwo, stackOne);
						Minecraft.getMinecraft().sndManager.playSound("mob.endermen.portal", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 2.0F, 1.0F);
						//TODO add particles
					}
				}
			}
		}
	}
	
	//called when an entity dies
	@Override
	public void entityDeath(LivingDeathEvent event) {
		//TODO teleport inv
	}
	
	//called when an entity is attacked
	@Override
	public void entityAttack(LivingAttackEvent event) {
		DiseaseHelper.spreadByAttack(event, this, 100);
	}
	
	//checks if an entity can catch the disease
	@Override
	public boolean isVulnerable(Entity entity) {
		if(
			entity instanceof EntityLivingBase
		) {
			return(true);
		} else {
			return(false);
		}
	}
}
