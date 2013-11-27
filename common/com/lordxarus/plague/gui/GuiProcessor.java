package com.lordxarus.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.container.ContainerProcessor;

public class GuiProcessor extends GuiBase {

	public GuiProcessor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.processor, new ContainerProcessor(invPlayer, entity));
	}

}
