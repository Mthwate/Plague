package com.lordxarus.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.container.ContainerAnalyzer;

public class GuiAnalyzer extends GuiBase {

	public GuiAnalyzer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.analyzer, new ContainerAnalyzer(invPlayer, entity));
	}

}
