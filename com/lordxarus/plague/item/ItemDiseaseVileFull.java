package com.lordxarus.plague.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDiseaseVileFull extends ItemBase {
	
	public ItemDiseaseVileFull(int id) {
		super(id);
		this.maxStackSize = 1;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		boolean complete = stack.getTagCompound().getBoolean("complete");
		if ((!complete) && (entity instanceof EntityPlayer)) {
			stack.getTagCompound().setBoolean("complete", true);
		}
	}

}
