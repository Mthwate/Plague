package com.lordxarus.plague;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lordxarus.plague.container.ContainerExtractor;
import com.lordxarus.plague.gui.GuiExtractor;
import com.lordxarus.plague.tileentity.TileEntityExtractor;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {
	
	public GuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(Plague.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		switch(id) {
			case 0:
				if(entity != null && entity instanceof TileEntityExtractor) {
					return(new ContainerExtractor(player.inventory, (TileEntityExtractor) entity));
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
				} else {
					return(null);
				}
			default:
				return(null);
		}
	}

}
