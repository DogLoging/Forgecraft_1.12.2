package com.modding.forgecraft.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;

public class TileEntityFusionFurnace extends TileEntityLockable implements IInventory, ISidedInventory
{
	public enum slotEnum
	{
		INPUT_SLOT, OUTPUT_SLOT
	}
	
	private ItemStack[] itemStackArray = new ItemStack[4];
	
	private int fuelFurnace;
	private int timeFusion;
	private int speedFusion;
	private int totalTime;
	private String tileEntityName;
	
	private static final int[] slot_top = new int[]
			{
					slotEnum.INPUT_SLOT.ordinal()
			};
	private static final int[] slot_bottom = new int[]
			{
					slotEnum.OUTPUT_SLOT.ordinal()
			};
	
	private static final int[] slot_side = new int[] {};
	
	@Override
	public int getSizeInventory()
	{
		return itemStackArray.length;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemStackArray[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if(itemStackArray[index] != null)
		{
			ItemStack stack;
			
			if(itemStackArray[index].getMaxStackSize() <= count)
			{
				stack = itemStackArray[index];
				itemStackArray[index] = null;
				
				return stack;
			}
			else
			{
				stack = itemStackArray[index].splitStack(count);
				
				if(itemStackArray[index].getMaxStackSize() == 0)
				{
					itemStackArray[index] = null;
				}
				
				return stack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if(itemStackArray[index] != null) 
		{
			ItemStack stack = itemStackArray[index];
			itemStackArray[index] = null;
			
			return stack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		boolean isAlreadyInSlot = stack != null && stack.isItemEqual(itemStackArray[index]) && ItemStack.areItemStacksEqual(stack, itemStackArray[index]);
		itemStackArray[index] = stack;
		
		if(stack != null && stack.getMaxStackSize() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if(index == slotEnum.INPUT_SLOT.ordinal() && !isAlreadyInSlot)
		{
			totalTime = timeFusion;
			timeFusion = 0;
			markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index == slotEnum.INPUT_SLOT.ordinal() ? true : false;
	}

	@Override
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return fuelFurnace;
		case 1:
			return speedFusion;
		case 2:
			return timeFusion;
		case 3:
			return totalTime;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			fuelFurnace = value;
			break;
		case 1:
			speedFusion = value;
			break;
		case 2:
			timeFusion = value;
			break;
		case 3:
			totalTime = value;
			break;
			default:
				break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < itemStackArray.length; i++)
		{
			itemStackArray[i] = null;
		}
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? tileEntityName : "container.fusion";
	}

	@Override
	public boolean hasCustomName()
	{
		return tileEntityName != null && tileEntityName.length() > 0;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return "forgecraft:fusion_furnace_gui";
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slot_bottom : (side == EnumFacing.UP ? slot_top : slot_side);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return true;
	}
	
}
