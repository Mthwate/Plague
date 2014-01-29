package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.slot.SlotMachine;

public class ContainerProcessor extends ContainerBase {

	public ContainerProcessor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		SlotMachine slotVile = new SlotMachine(entity, 0, 43, 35);
		SlotMachine slotSyringe = new SlotMachine(entity, 1, 79, 35);
		
		slotVile.addValidItem(ItemPlague.diseaseVileFull.itemID);
		slotSyringe.addValidItem(ItemPlague.syringeEmpty.itemID);

		addSlotToContainer(slotVile);
		addSlotToContainer(slotSyringe);
	}

}