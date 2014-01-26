package com.mthwate.plague.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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
		return this.onUse(itemStack, player, player);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity) {
		player.inventory.setInventorySlotContents(player.inventory.currentItem, this.onUse(itemStack, player, entity));
		return true;
	}
	
	public ItemStack onUse(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity) {
		for (Disease disease : InstrumentHelper.getRemnants(itemStack)) {
			if (Plague.rand.nextInt(100) < 50) {
				DiseaseHelper.addDisease(entity, disease);
			}
		}

		--itemStack.stackSize;

		ItemStack syringeFullStack = new ItemStack(ItemPlague.syringeFull);
		syringeFullStack.setTagCompound(new NBTTagCompound());
		for (Disease disease : DiseaseHelper.getActiveDiseases(entity)) {
			syringeFullStack.getTagCompound().setBoolean(disease.getUnlocalizedName(), true);
		}
		
		entity.attackEntityFrom(DamageSourcePlague.syringe, 1);
		
		if (itemStack.stackSize <= 0) {
			return syringeFullStack;
		}
		
		player.inventory.addItemStackToInventory(syringeFullStack);
		return itemStack;
	}

}
