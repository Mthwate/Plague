package com.mthwate.plague.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBase extends Container {

	private IInventory tileEntity;

	public ContainerBase(InventoryPlayer invPlayer, IInventory entity) {
		tileEntity = entity;

		// adds the players hotbar
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}

		// adds the players inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack itemstack = null;
		Slot slot = this.getSlot(slotId);

		//if the slot has items in it
		if (slot != null && slot.getHasStack()) {
			
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			//if the item is originating from the players inventory
			if (slotId < 36) {
			
				boolean a = true;
				
				for (int i=36; i<this.inventorySlots.size(); i++) {
					if (this.getSlot(i).isItemValid(itemstack1)) {
						if (this.mergeItemStack(itemstack1, i, i+1, true)) {
							a = false;
							break;
						}
					}
				}
				
				if(a) {
					return null;
				}
				
				
				
				
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

}