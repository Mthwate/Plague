package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerAnalyzer extends ContainerBase {

	public ContainerAnalyzer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		// adds the container's slots
		addSlotToContainer(new Slot(entity, 0, 79, 35));
	}

}