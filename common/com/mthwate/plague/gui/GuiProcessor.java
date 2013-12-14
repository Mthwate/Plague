package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.container.ContainerProcessor;

public class GuiProcessor extends GuiBase {

	public GuiProcessor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.processor, new ContainerProcessor(invPlayer, entity));
	}

}
