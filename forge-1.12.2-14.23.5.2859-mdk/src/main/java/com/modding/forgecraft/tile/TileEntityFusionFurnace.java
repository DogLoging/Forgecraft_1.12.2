package com.modding.forgecraft.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityFusionFurnace extends TileEntityLockable implements IInventory, ISidedInventory, ITickable
{
	public enum slotEnum
	{
		INPUT_SLOT, OUTPUT_SLOT
	}
	
    private NonNullList<ItemStack> itemStackArray = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	
	private int fuelFusionFurnace;
	
	//process final Burn
	private int processTotalBurn;
	
	//process return craft
	private int timeProcess;
	private int totalProcessTime;
	
	//process Burn
	private int timeFusion;
	private int totalFusionTime;
	
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
		return itemStackArray.size();
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemStackArray.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.itemStackArray, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.itemStackArray, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.itemStackArray.get(index);
		boolean isAlreadyInSlot = stack != null && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		itemStackArray.set(index, stack);
		
		if(stack != null && stack.getMaxStackSize() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if(index == slotEnum.INPUT_SLOT.ordinal() && !isAlreadyInSlot)
		{
			totalFusionTime = timeFusion;
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
			return fuelFusionFurnace;
		case 1:
			return timeFusion;
		case 2:
			return totalFusionTime;
		case 3:
			return timeProcess;
		case 4:
			return totalProcessTime;
		case 5:
			return processTotalBurn;
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
			fuelFusionFurnace = value;
			break;
		case 1:
			timeFusion = value;
			break;
		case 2:
			totalFusionTime = value;
			break;
		case 3:
			timeProcess = value;
			break;
		case 4:
			totalProcessTime = value;
			break;
		case 5:
			processTotalBurn = value;
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
		this.itemStackArray.clear();
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
	
	public void setCustoInventoryName(String customName)
	{
		this.tileEntityName = customName;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
        this.itemStackArray = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.itemStackArray);
        
        this.processTotalBurn = compound.getInteger("BurnTime");
        this.timeFusion = compound.getInteger("FusionTime");
        this.totalFusionTime = compound.getInteger("TotalFusionTime");
        
        this.timeProcess = compound.getInteger("Progress");
        this.totalProcessTime = compound.getInteger("FinalProgress");
        
        if(compound.hasKey("CustomName", 8))
        {
        	this.tileEntityName = compound.getString("CustomName");
        }
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.itemStackArray);
		
		compound.setInteger("BurnTime", (short)processTotalBurn);
		compound.setInteger("FusionTime", (short)timeFusion);
		compound.setInteger("TotalFusionTime", (short)totalFusionTime);
		
		compound.setInteger("Progress", (short)timeProcess);
		compound.setInteger("FinalProgress", (short)totalProcessTime);
		
		if(this.hasCustomName())
		{
			compound.setString("CustomName", tileEntityName);
		}
		
		return compound;
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

	@Override
	public void update()
	{
		
	}
	
}
