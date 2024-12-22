package com.modding.forgecraft.inventory.slot;

import com.modding.forgecraft.block.tileentity.TileEntityFusionFurnace;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFuelFusionFurnace extends Slot
{
	public SlotFuelFusionFurnace(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
    public boolean isItemValid(ItemStack stack)
    {
        return TileEntityFusionFurnace.isItemFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(ItemStack stack)
    {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack)
    {
        return stack.getItem() == Items.BUCKET;
    }
}
