package com.lordxarus.plague.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.container.ContainerExtractor;
import com.lordxarus.plague.tileentity.TileEntityExtractor;

public class GuiExtractor extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation(Plague.modid, "textures/gui/" + Plague.blockExtractor.getUnlocalizedName().substring(5) + ".png");
	
	public GuiExtractor(InventoryPlayer invPlayer, TileEntityExtractor entity) {
		super(new ContainerExtractor(invPlayer, entity));
		
		xSize = 176;
		ySize = 166;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}
