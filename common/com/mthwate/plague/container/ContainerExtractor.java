package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.slot.SlotMachine;

public class ContainerExtractor extends ContainerBase {

	public ContainerExtractor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		SlotMachine slotSyringe = new SlotMachine(entity, 0, 43, 35);
		SlotMachine slotVile = new SlotMachine(entity, 1, 79, 35);

		slotSyringe.addValidItem(ItemPlague.syringeFull.itemID);

		slotVile.addValidItem(ItemPlague.diseaseVileEmpty.itemID);

		addSlotToContainer(slotSyringe);
		addSlotToContainer(slotVile);
	}

}
