package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.container.ContainerExtractor;

public class GuiExtractor extends GuiBase {

	public GuiExtractor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.extractor, new ContainerExtractor(invPlayer, entity));
	}

}
