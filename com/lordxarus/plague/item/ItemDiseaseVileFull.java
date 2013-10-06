package com.lordxarus.plague.item;

import java.util.List;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.disease.Disease;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiseaseVileFull extends ItemBase {
	
	public ItemDiseaseVileFull(int id) {
		super(id);
		this.maxStackSize = 1;
	}

}
