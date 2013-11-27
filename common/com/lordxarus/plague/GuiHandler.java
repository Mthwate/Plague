package com.lordxarus.plague;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lordxarus.plague.container.ContainerAnalyzer;
import com.lordxarus.plague.container.ContainerExtractor;
import com.lordxarus.plague.container.ContainerProcessor;
import com.lordxarus.plague.container.ContainerWeaponizer;
import com.lordxarus.plague.gui.GuiAnalyzer;
import com.lordxarus.plague.gui.GuiExtractor;
import com.lordxarus.plague.gui.GuiProcessor;
import com.lordxarus.plague.gui.GuiWeaponizer;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;
import com.lordxarus.plague.tileentity.TileEntityExtractor;
import com.lordxarus.plague.tileentity.TileEntityProcessor;
import com.lordxarus.plague.tileentity.TileEntityWeaponizer;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		IInventory inv = null;
		if (entity instanceof IInventory) {
			inv = (IInventory) entity;
		}
		switch (id) {
			case 0:
				if (inv instanceof TileEntityExtractor) {
					return new GuiExtractor(player.inventory, inv);
				} else if (inv instanceof TileEntityAnalyzer) {
					return new GuiAnalyzer(player.inventory, inv);
				} else if (inv instanceof TileEntityProcessor) {
					return new GuiProcessor(player.inventory, inv);
				} else if (inv instanceof TileEntityWeaponizer) {
					return new GuiWeaponizer(player.inventory, inv);
				} else {
					return null;
				}
			default:
				return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		IInventory inv = null;
		if (entity instanceof IInventory) {
			inv = (IInventory) entity;
		}
		switch (id) {
			case 0:
				if (inv instanceof TileEntityExtractor) {
					return new ContainerExtractor(player.inventory, inv);
				} else if (inv instanceof TileEntityAnalyzer) {
					return new ContainerAnalyzer(player.inventory, inv);
				} else if (inv instanceof TileEntityProcessor) {
					return new ContainerProcessor(player.inventory, inv);
				} else if (inv instanceof TileEntityWeaponizer) {
					return new ContainerWeaponizer(player.inventory, inv);
				} else {
					return null;
				}
			default:
				return null;
		}
	}

}
