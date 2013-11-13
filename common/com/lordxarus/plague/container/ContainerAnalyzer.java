package com.lordxarus.plague.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.lordxarus.plague.tileentity.TileEntityAnalyzer;

public class ContainerAnalyzer extends ContainerBase {
	
	private TileEntityAnalyzer analyzer;

	public ContainerAnalyzer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity);
		
		//adds the container's slots
		this.addSlotToContainer(new Slot(entity, 0, 79, 35));
	}
	
}