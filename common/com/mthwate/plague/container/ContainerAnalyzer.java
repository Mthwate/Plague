package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.slot.SlotMachine;

public class ContainerAnalyzer extends ContainerBase {

	public ContainerAnalyzer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		SlotMachine slotVile = new SlotMachine(entity, 0, 79, 35);
		
		slotVile.addValidItem(ItemPlague.diseaseVileFull.itemID);
		
		addSlotToContainer(slotVile);
	}

}