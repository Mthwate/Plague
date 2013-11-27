package com.lordxarus.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.container.ContainerWeaponizer;

public class GuiWeaponizer extends GuiBase {

	public GuiWeaponizer(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.weaponizer, new ContainerWeaponizer(invPlayer, entity));
	}

}