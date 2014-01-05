package com.mthwate.plague;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.container.ContainerAnalyzer;
import com.mthwate.plague.container.ContainerAnalyzerElectric;
import com.mthwate.plague.container.ContainerBoiler;
import com.mthwate.plague.container.ContainerExtractor;
import com.mthwate.plague.container.ContainerExtractorElectric;
import com.mthwate.plague.container.ContainerProcessor;
import com.mthwate.plague.container.ContainerWeaponizer;
import com.mthwate.plague.gui.GuiAnalyzer;
import com.mthwate.plague.gui.GuiAnalyzerElectric;
import com.mthwate.plague.gui.GuiBoiler;
import com.mthwate.plague.gui.GuiExtractor;
import com.mthwate.plague.gui.GuiExtractorElectric;
import com.mthwate.plague.gui.GuiProcessor;
import com.mthwate.plague.gui.GuiWeaponizer;
import com.mthwate.plague.tileentity.TileEntityAnalyzer;
import com.mthwate.plague.tileentity.TileEntityAnalyzerElectric;
import com.mthwate.plague.tileentity.TileEntityBoiler;
import com.mthwate.plague.tileentity.TileEntityExtractor;
import com.mthwate.plague.tileentity.TileEntityExtractorElectric;
import com.mthwate.plague.tileentity.TileEntityProcessor;
import com.mthwate.plague.tileentity.TileEntityWeaponizer;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		IInventory inv = null;
		if (entity instanceof IInventory) {
			inv = (IInventory) entity;
		}
		switch (id) {
			case 0:
				if(inv instanceof TileEntityExtractor) {
					return new GuiExtractor(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityAnalyzer) {
					return new GuiAnalyzer(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityProcessor) {
					return new GuiProcessor(player.inventory, inv);
				}

				if(inv instanceof TileEntityWeaponizer) {
					return new GuiWeaponizer(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityBoiler) {
					return new GuiBoiler(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityExtractorElectric) {
					return new GuiExtractorElectric(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityAnalyzerElectric) {
					return new GuiAnalyzerElectric(player.inventory, inv);
				}
				
				return null;
			default:
				return null;
		}
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		IInventory inv = null;
		if (entity instanceof IInventory) {
			inv = (IInventory) entity;
		}
		switch (id) {
			case 0:
				if(inv instanceof TileEntityExtractor) {
					return new ContainerExtractor(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityAnalyzer) {
					return new ContainerAnalyzer(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityProcessor) {
					return new ContainerProcessor(player.inventory, inv);
				}

				if(inv instanceof TileEntityWeaponizer) {
					return new ContainerWeaponizer(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityBoiler) {
					return new ContainerBoiler(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityExtractorElectric) {
					return new ContainerExtractorElectric(player.inventory, inv);
				}
				
				if(inv instanceof TileEntityAnalyzerElectric) {
					return new ContainerAnalyzerElectric(player.inventory, inv);
				}
				
				return null;
			default:
				return null;
		}
	}

}
