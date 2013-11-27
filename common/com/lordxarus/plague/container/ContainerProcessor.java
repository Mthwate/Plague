package com.lordxarus.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerProcessor extends ContainerBase {

	public ContainerProcessor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);

		// adds the container's slots
		addSlotToContainer(new Slot(entity, 0, 43, 35));
		addSlotToContainer(new Slot(entity, 1, 79, 35));
	}

}