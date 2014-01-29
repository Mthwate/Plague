package com.mthwate.plague.slot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMachine extends Slot {
	
	List<Integer> validItems = new ArrayList<Integer>();

	public SlotMachine(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return validItems.contains(itemStack.itemID);
	}
	
	public void addValidItem(int id) {
		validItems.add(id);
	}

}
