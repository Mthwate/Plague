package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerBoiler extends ContainerBase {

	public ContainerBoiler(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		// adds the container's slots
		addSlotToContainer(new Slot(entity, 0, 79, 17));
		addSlotToContainer(new Slot(entity, 1, 79, 51));
		addSlotToContainer(new Slot(entity, 2, 43, 51));
	}
}
