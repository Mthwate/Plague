package com.mthwate.plague.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.mthwate.plague.DamageSourcePlague;
import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.lib.DiseaseHelper;

public class ItemSyringeEmpty extends ItemBaseFood {

	public ItemSyringeEmpty(int id) {
		super(id, 0, 0F, false);
		maxStackSize = 1;
		setAlwaysEdible();
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		for (Disease disease : Plague.diseases) {
			if (itemStack.getTagCompound().getBoolean(disease.getUnlocalizedName())) {
				if (Plague.rand.nextInt(100) < 50) {
					DiseaseHelper.addDisease(player, disease);
				}
			}
		}
		
		--itemStack.stackSize;
		
		ItemStack syringeFullStack = new ItemStack(ItemPlague.syringeFull);
		syringeFullStack.setTagCompound(new NBTTagCompound());
		syringeFullStack.getTagCompound().setString("owner", player.username);
		for (Disease disease : DiseaseHelper.getActiveDiseases(player)) {
			syringeFullStack.getTagCompound().setBoolean(disease.getUnlocalizedName(), true);
		}
		if (itemStack.stackSize <= 0) {
			player.attackEntityFrom(DamageSourcePlague.syringe, 1);
			return syringeFullStack;
		}
		player.inventory.addItemStackToInventory(syringeFullStack);
		player.attackEntityFrom(DamageSourcePlague.syringe, 1);
		return itemStack;
	}

}
