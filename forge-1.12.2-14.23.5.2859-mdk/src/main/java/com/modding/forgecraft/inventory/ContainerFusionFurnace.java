package com.modding.forgecraft.inventory;

import com.modding.forgecraft.crafting.FusionRecipes;
import com.modding.forgecraft.tile.TileEntityFusionFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFusionFurnace extends Container
{
	private final IInventory tileFusionFurnace;
	private final int sizeInventory;
	
	private int processTotalBurn;
	
	private int timeFusion;
	private int totalTimeFusion;
	
	private int timeProcess;
	private int totalProcessTime;
	
	public ContainerFusionFurnace(InventoryPlayer playerInventory, IInventory inventory)
	{
		tileFusionFurnace = inventory;
		sizeInventory = tileFusionFurnace.getSizeInventory();
		
		this.addSlotToContainer(new Slot(tileFusionFurnace, TileEntityFusionFurnace.slotEnum.INPUT_SLOT1.ordinal(), 26, 22));
		this.addSlotToContainer(new Slot(tileFusionFurnace, TileEntityFusionFurnace.slotEnum.INPUT_SLOT2.ordinal(), 67, 22));
		
		this.addSlotToContainer(new SlotFusionFurnaceOutPut(playerInventory.player, tileFusionFurnace, TileEntityFusionFurnace.slotEnum.OUTPUT_SLOT.ordinal(), 127, 22));
		this.addSlotToContainer(new SlotFuelFusionFurnace(tileFusionFurnace, TileEntityFusionFurnace.slotEnum.INPUT_FUEL.ordinal(), 26, 53));
		
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
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileFusionFurnace);
    }
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerListener = this.listeners.get(i);
			
			if(this.totalTimeFusion != this.tileFusionFurnace.getField(2))
			{
				icontainerListener.sendWindowProperty(this, 2, this.tileFusionFurnace.getField(2));
			}
			else if(this.timeFusion != this.tileFusionFurnace.getField(0))
			{
				icontainerListener.sendWindowProperty(this, 0, this.tileFusionFurnace.getField(0));
			}
			else if(this.totalProcessTime != this.tileFusionFurnace.getField(1))
			{
				icontainerListener.sendWindowProperty(this, 1, this.tileFusionFurnace.getField(1));
			}
			else if(this.timeProcess != this.tileFusionFurnace.getField(3))
			{
				icontainerListener.sendWindowProperty(this, 3, this.tileFusionFurnace.getField(3));
			}
			else if(this.processTotalBurn != this.tileFusionFurnace.getField(4))
			{
				icontainerListener.sendWindowProperty(this, 4, this.tileFusionFurnace.getField(4));
			}
		}
		
		this.timeFusion = this.tileFusionFurnace.getField(2);
		this.totalTimeFusion = this.tileFusionFurnace.getField(1);
		this.totalProcessTime = this.tileFusionFurnace.getField(0);
		this.timeProcess = this.tileFusionFurnace.getField(3);
		this.processTotalBurn = this.tileFusionFurnace.getField(4);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileFusionFurnace.setField(id, data);
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileFusionFurnace.isUsableByPlayer(player);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == TileEntityFusionFurnace.slotEnum.OUTPUT_SLOT.ordinal())
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != TileEntityFusionFurnace.slotEnum.INPUT_SLOT1.ordinal() && index != TileEntityFusionFurnace.slotEnum.INPUT_SLOT2.ordinal() && index != TileEntityFusionFurnace.slotEnum.INPUT_FUEL.ordinal())
            {
                if (!FusionRecipes.instance().getFusionResult(itemstack1, slot.getStack()).isEmpty())
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (TileEntityFusionFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
