package com.mthwate.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.slot.SlotMachine;

public class ContainerBoiler extends ContainerBase {

	public ContainerBoiler(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);
		
		SlotMachine slotFuel = new SlotMachine(entity, 0, 79, 17);
		SlotMachine slotWater = new SlotMachine(entity, 1, 79, 51);
		SlotMachine slotItem = new SlotMachine(entity, 2, 43, 51);
		
		slotFuel.addValidItem(Item.coal.itemID);
		slotWater.addValidItem(Item.bucketWater.itemID);
		slotItem.addValidItem(ItemPlague.syringeEmpty.itemID);
		slotItem.addValidItem(ItemPlague.diseaseVileEmpty.itemID);

		// adds the container's slots
		addSlotToContainer(slotFuel);
		addSlotToContainer(slotWater);
		addSlotToContainer(slotItem);
	}
}
