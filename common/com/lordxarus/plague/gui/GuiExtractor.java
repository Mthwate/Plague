package com.lordxarus.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.container.ContainerExtractor;

public class GuiExtractor extends GuiBase {
	
	public GuiExtractor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.extractor, new ContainerExtractor(invPlayer, entity));
	}
	
}
