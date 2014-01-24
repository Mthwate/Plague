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
import com.mthwate.plague.lib.InstrumentHelper;

public class ItemSyringeEmpty extends ItemBase {

	public ItemSyringeEmpty(int id) {
		super(id);
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		for (Disease disease : InstrumentHelper.getRemnants(itemStack)) {
			if (Plague.rand.nextInt(100) < 50) {
				DiseaseHelper.addDisease(player, disease);
			}
		}

		--itemStack.stackSize;

		ItemStack syringeFullStack = new ItemStack(ItemPlague.syringeFull);
		syringeFullStack.setTagCompound(new NBTTagCompound());
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

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

}
