package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.container.ContainerAnalyzer;

public class GuiAnalyzer extends GuiBase {

	public GuiAnalyzer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.analyzer, new ContainerAnalyzer(invPlayer, entity));
	}

}
