package com.modding.forgecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFusionFurnaceOutPut extends Slot
{
	private final EntityPlayer player;
	private int removeCount;
	
	public SlotFusionFurnaceOutPut(EntityPlayer player, IInventory tileFusionFurnace, int index, int xPosition, int yPosition)
	{
		super(tileFusionFurnace, index, xPosition, yPosition);
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int amout)
	{
		if(this.getHasStack())
		{
			removeCount += Math.min(amout, getStack().getMaxStackSize());
		}
		
		return super.decrStackSize(amout);
	}
	
	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack)
	{
		super.onTake(player, stack);
		return stack;
	}
}
