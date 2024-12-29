package com.modding.forgecraft.block.container;

import com.modding.forgecraft.block.tileentity.TileEntityFusionFurnace;
import com.modding.forgecraft.crafting.FusionRecipes;
import com.modding.forgecraft.inventory.slot.SlotFuelFusionFurnace;
import com.modding.forgecraft.inventory.slot.SlotFusionFurnaceOutPut;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFusionFurnace extends Container
{
	private final IInventory tileFusionFurnace;
	
	private int fuelFusionFurnace;
	private int processTotalBurn;
	
	private int timeFusion;
	private int totalTimeFusion;
	
	private int timeProcess;
	private int totalProcessTime;
	
	public ContainerFusionFurnace(InventoryPlayer playerInventory, IInventory inventory)
	{
		tileFusionFurnace = inventory;
		
		this.addSlotToContainer(new Slot(tileFusionFurnace, 0, 26, 22));
		this.addSlotToContainer(new Slot(tileFusionFurnace, 1, 67, 22));
		
		this.addSlotToContainer(new SlotFuelFusionFurnace(tileFusionFurnace, 2, 26, 53));
		this.addSlotToContainer(new SlotFusionFurnaceOutPut(playerInventory.player, tileFusionFurnace, 3, 127, 22));
		
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
			
			if(this.timeFusion != this.tileFusionFurnace.getField(0))
			{
				icontainerListener.sendWindowProperty(this, 0, this.tileFusionFurnace.getField(0));
			}
			
			if(this.timeProcess != this.tileFusionFurnace.getField(1))
			{
				icontainerListener.sendWindowProperty(this, 1, this.tileFusionFurnace.getField(1));
			}
			
			if(this.totalTimeFusion != this.tileFusionFurnace.getField(2))
			{
				icontainerListener.sendWindowProperty(this, 2, this.tileFusionFurnace.getField(2));
			}
			
			if(this.totalProcessTime != this.tileFusionFurnace.getField(3))
			{
				icontainerListener.sendWindowProperty(this, 3, this.tileFusionFurnace.getField(3));
			}
			
			if(this.processTotalBurn != this.tileFusionFurnace.getField(4))
			{
				icontainerListener.sendWindowProperty(this, 4, this.tileFusionFurnace.getField(4));
			}
			
			if(this.fuelFusionFurnace != this.tileFusionFurnace.getField(5))
			{
				icontainerListener.sendWindowProperty(this, 5, this.tileFusionFurnace.getField(5));
			}
		}
		
		this.timeFusion = this.tileFusionFurnace.getField(0);
		this.timeProcess = this.tileFusionFurnace.getField(1);
		this.totalTimeFusion = this.tileFusionFurnace.getField(2);
		this.totalProcessTime = this.tileFusionFurnace.getField(3);
		this.processTotalBurn = this.tileFusionFurnace.getField(4);
		this.fuelFusionFurnace = this.tileFusionFurnace.getField(5);
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
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack stack_1 = slot.getStack();
            stack = stack_1.copy();

            if (index == 3)
            {
                if (!this.mergeItemStack(stack_1, 4, 40, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack_1, stack);
            }
            else if (index != 2 && index != 1 && index != 0)
            {
                Slot slot_0 = this.inventorySlots.get(0);

                boolean handled = false;
                
                if (FusionRecipes.instance().isPartialIngredient(stack_1))
                {
                    if (this.mergeItemStack(stack_1, 0, 2, false))
                    {
                        handled = true;
                    }
                }

                if (!handled && !FusionRecipes.instance().getFusionResult(slot_0.getStack(), stack_1).isEmpty())
                {
                    if (this.mergeItemStack(stack_1, 1, 2, false))
                    {
                        handled = true;
                    }
                }

                if (!handled && TileEntityFusionFurnace.isItemFuel(stack_1))
                {
                    if (this.mergeItemStack(stack_1, 2, 3, false))
                    {
                        handled = true;
                    }
                }

                if (!handled)
                {
                    if (index >= 4 && index < 31)
                    {
                        if (!this.mergeItemStack(stack_1, 31, 40, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (index >= 31 && index < 40)
                    {
                        if (!this.mergeItemStack(stack_1, 4, 31, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
            else if (!this.mergeItemStack(stack_1, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }

            if (stack_1.isEmpty())
            {
            	slot.putStack(ItemStack.EMPTY);
            }
            else
            {
            	slot.onSlotChanged();
            }

            if (stack_1.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack_1);
        }

        return stack;
    }
}
