package com.modding.forgecraft.inventory;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.block.container.ContainerFusionFurnace;
import com.modding.forgecraft.block.tileentity.TileEntityFusionFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFusionFurnace extends GuiContainer
{
    private static final ResourceLocation fusionGuiTextures = new ResourceLocation(Main.MODID + ":textures/gui/container/fusion_furnace_gui.png");
    private final InventoryPlayer player;
    private final IInventory tilefusionFurnace;

    public GuiFusionFurnace(InventoryPlayer parInventoryPlayer, IInventory parInventoryGrinder)
    {
        super(new ContainerFusionFurnace(parInventoryPlayer,  parInventoryGrinder));
        
        player = parInventoryPlayer;
        tilefusionFurnace = parInventoryGrinder;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tilefusionFurnace.getDisplayName().getUnformattedText();
        
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(fusionGuiTextures);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);
        
        int progressLevel = getProgressLevel(24);
        //drawTexturedModalRect(marginHorizontal + 79, marginVertical + 34, 176, 14, progressLevel + 1, 16);
        
        //int fuelBarWidth = 13; // Largura da barra de combustível
        int fuelBarHeight = 14; // Altura da barra de combustível
        drawTexturedModalRect(marginHorizontal + 48, marginVertical + 55, 4, 167, progressLevel, fuelBarHeight);
    }

	private int getProgressLevel(int i)
	{
		  int currentFuel = tilefusionFurnace.getField(0); // campo que contém a quantidade de combustível
		  int maxFuel = 100; // valor máximo de combustível suportado
		  return currentFuel * i / maxFuel;
	}
}
