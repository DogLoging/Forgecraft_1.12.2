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
    private final IInventory tileFusion;

    public GuiFusionFurnace(InventoryPlayer parInventoryPlayer, IInventory parInventoryGrinder)
    {
        super(new ContainerFusionFurnace(parInventoryPlayer,  parInventoryGrinder));
        
        player = parInventoryPlayer;
        tileFusion = parInventoryGrinder;
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tileFusion.getDisplayName().getUnformattedText();
        
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
        
        String fuelText = this.tileFusion.getField(5) + " / " + 5000;
        fontRenderer.drawString(fuelText, 50, 44, 0x7d7d7d);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(fusionGuiTextures);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);
        
        if(TileEntityFusionFurnace.isFuel(tileFusion))
        {
	        int fuelLevel = getFuelLevel(24);
	        drawTexturedModalRect(marginHorizontal + 48, marginVertical + 55, 4, 167, fuelLevel, 14);
        }
        
        int timeFusionLevel = getTimeFusionLevel(13);
        drawTexturedModalRect(marginHorizontal + 47, marginVertical + 35 - timeFusionLevel, 176, 13 - timeFusionLevel, 14, timeFusionLevel);
        
        int timeprocessLevel = getProcessTimeLevel(24);
        drawTexturedModalRect(marginHorizontal + 91 , marginVertical + 22, 176, 14, timeprocessLevel + 1, 16);
    }
    
    private int getProcessTimeLevel(int pixel)
    {
        int currentProcess = this.tileFusion.getField(1);
        int maxProcess = this.tileFusion.getField(3); 
        
        return maxProcess != 0 && currentProcess != 0 ? currentProcess * pixel / maxProcess : 0;
    }

    private int getTimeFusionLevel(int pixel)
    {
        int currentTime = this.tileFusion.getField(0);
        int maxTime = this.tileFusion.getField(2);
       
        return maxTime != 0 && currentTime != 0 ? currentTime * pixel / maxTime : 0;
    }

    private int getFuelLevel(int pixel)
    {
        int currentFuel = this.tileFusion.getField(5);
        int maxFuel = (5000 - 4) / 4;
        
        return maxFuel != 0 && currentFuel != 0 ? currentFuel * pixel / maxFuel : 0;
    }
}
