package com.lordxarus.plague;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lordxarus.plague.container.ContainerAnalyzer;
import com.lordxarus.plague.container.ContainerExtractor;
import com.lordxarus.plague.container.ContainerProcessor;
import com.lordxarus.plague.gui.GuiAnalyzer;
import com.lordxarus.plague.gui.GuiExtractor;
import com.lordxarus.plague.gui.GuiProcessor;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;
import com.lordxarus.plague.tileentity.TileEntityExtractor;
import com.lordxarus.plague.tileentity.TileEntityProcessor;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		switch(id) {
			case 0:
				if(entity != null && entity instanceof TileEntityExtractor) {
					return(new ContainerExtractor(player.inventory, (TileEntityExtractor) entity));
				} else if(entity != null && entity instanceof TileEntityAnalyzer) {
					return(new ContainerAnalyzer(player.inventory, (TileEntityAnalyzer) entity));
				} else if(entity != null && entity instanceof TileEntityProcessor) {
					return(new ContainerProcessor(player.inventory, (TileEntityProcessor) entity));
				} else {
					return(null);
				}
			default:
				return(null);
		}
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		switch(id) {
			case 0:
				if(entity != null && entity instanceof TileEntityExtractor) {
					return(new GuiExtractor(player.inventory, (TileEntityExtractor) entity));
				} else if(entity != null && entity instanceof TileEntityAnalyzer) {
					return(new GuiAnalyzer(player.inventory, (TileEntityAnalyzer) entity));
				} else if(entity != null && entity instanceof TileEntityProcessor) {
					return(new GuiProcessor(player.inventory, (TileEntityProcessor) entity));
				} else {
					return(null);
				}
			default:
				return(null);
		}
	}

}
