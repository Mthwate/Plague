package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.container.ContainerProcessor;

public class GuiProcessor extends GuiBase {

	public GuiProcessor(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, new ContainerProcessor(invPlayer, entity));
	}

}
