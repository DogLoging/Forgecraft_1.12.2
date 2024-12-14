package com.modding.forgecraft.inventory;

import com.modding.forgecraft.tile.TileEntityFusionFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerFusionFurnace extends Container
{
	private final IInventory tileFusionFurnace;
	private final int sizeInventory;
	
	private int fuelFusionFurnace;
	private int processTotalBurn;
	
	private int timeFusion;
	private int totalTimeFusion;
	
	private int timeProcess;
	private int totalProcessTime;
	
	public ContainerFusionFurnace(InventoryPlayer playerInventory, IInventory inventory)
	{
		tileFusionFurnace = inventory;
		sizeInventory = tileFusionFurnace.getSizeInventory();
		
		this.addSlotToContainer(new Slot(tileFusionFurnace, TileEntityFusionFurnace.slotEnum.INPUT_SLOT1.ordinal(), 43, 39));
		this.addSlotToContainer(new Slot(tileFusionFurnace, TileEntityFusionFurnace.slotEnum.INPUT_SLOT2.ordinal(), 84, 39));
		
		this.addSlotToContainer(new SlotFusionFurnaceOutPut(playerInventory.player, tileFusionFurnace, TileEntityFusionFurnace.slotEnum.OUTPUT_SLOT.ordinal(), 148, 39));
	
		int i;
		
		for(i = 0; i < 3; ++i)
		{
			for(int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileFusionFurnace.isUsableByPlayer(player);
	}
}
