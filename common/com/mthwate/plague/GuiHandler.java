package com.mthwate.plague;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.container.ContainerAnalyzer;
import com.mthwate.plague.container.ContainerAnalyzerElectric;
import com.mthwate.plague.container.ContainerBoiler;
import com.mthwate.plague.container.ContainerExtractor;
import com.mthwate.plague.container.ContainerProcessor;
import com.mthwate.plague.container.ContainerWeaponizer;
import com.mthwate.plague.gui.GuiAnalyzer;
import com.mthwate.plague.gui.GuiAnalyzerElectric;
import com.mthwate.plague.gui.GuiBoiler;
import com.mthwate.plague.gui.GuiExtractor;
import com.mthwate.plague.gui.GuiProcessor;
import com.mthwate.plague.gui.GuiWeaponizer;
import com.mthwate.plague.tileentity.TileEntityAnalyzer;
import com.mthwate.plague.tileentity.TileEntityAnalyzerElectric;
import com.mthwate.plague.tileentity.TileEntityBoiler;
import com.mthwate.plague.tileentity.TileEntityExtractor;
import com.mthwate.plague.tileentity.TileEntityProcessor;
import com.mthwate.plague.tileentity.TileEntityWeaponizer;

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
				} else if (inv instanceof TileEntityBoiler) {
					return new GuiBoiler(player.inventory, inv);
				} else if (inv instanceof TileEntityAnalyzerElectric) {
					return new GuiAnalyzerElectric(player.inventory, inv);
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
				} else if (inv instanceof TileEntityBoiler) {
					return new ContainerBoiler(player.inventory, inv);
				} else if (inv instanceof TileEntityAnalyzerElectric) {
					return new ContainerAnalyzerElectric(player.inventory, inv);
				} else {
					return null;
				}
			default:
				return null;
		}
	}

}
