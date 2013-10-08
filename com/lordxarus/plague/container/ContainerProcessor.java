package com.lordxarus.plague.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.lordxarus.plague.tileentity.TileEntityProcessor;

public class ContainerProcessor extends Container {
	
	private TileEntityProcessor processor;

	public ContainerProcessor(InventoryPlayer invPlayer, TileEntityProcessor entity) {
		this.processor = entity;
		
		//adds the players hotbar
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}
		
		//adds the players inventory
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		//adds the container's slots
		this.addSlotToContainer(new Slot(entity, 0, 43, 35));
		this.addSlotToContainer(new Slot(entity, 1, 79, 35));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return(processor.isUseableByPlayer(player));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack = slot.getStack();
			ItemStack result = itemstack.copy();
			if(i >= 36) {
				if(!mergeItemStack(itemstack, 0, 36, false)) {
					return(null);
				}
			} else if(!mergeItemStack(itemstack, 36, 36 + processor.getSizeInventory(), false)) {
				return(null);
			}
			if(itemstack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			slot.onPickupFromSlot(player, itemstack);
			return(result);
		}
		return(null);
	}
	
}