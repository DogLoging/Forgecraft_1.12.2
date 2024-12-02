package com.modding.forgecraft.tile;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;

public class FusionFurnaceTileEntity extends TileEntityLockable implements ITickable
{
	private ItemStack[] inventory = new ItemStack[4];
	
	private int fuel = 0;
	private int cookTime = 0;
	private int totalCookTime = 100;
	
	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : inventory)
		{
			if(stack != null && !stack.isEmpty())
			{
				return false;
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if(inventory[index] != null)
		{
			ItemStack stack;
			
			if(inventory[index].getCount() <= count)
			{
				stack = inventory[index];
				inventory[index] = ItemStack.EMPTY;
				
				return stack;
			}
			else
			{
				stack = inventory[index].splitStack(count);
				
				if(inventory[index].getCount() <= count)
				{
					inventory[index] = ItemStack.EMPTY;
				}
				
				return stack;
			}
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = inventory[index];
		inventory[index] = ItemStack.EMPTY;
		
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inventory[index] = stack;
		
		if(stack != null && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
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
		return world.getTileEntity(pos) == this && player.getDistanceSq(pos) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		switch(index)
		{
		case 0:
			return true;
		case 1:
			return true;
		case 2:
			return isFuel(stack);
		case 4:
			return false;
			default:
				return false;
		}
	}

	private boolean isFuel(ItemStack stack)
	{
		return stack.getItem() == Items.LAVA_BUCKET;
	}

	@Override
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.fuel;
		case 1:
			return this.cookTime;
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
			this.fuel = value;
			break;
		case 1:
			this.cookTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 2;
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < inventory.length; i++)
		{
			inventory[i] = ItemStack.EMPTY;
		}
	}

	@Override
	public String getName()
	{
		return "fusion furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return null;
	}

	@Override
	public void tick()
	{
		if(this.fuel > 0)
		{
			this.fuel = fuel - 10;
			
			if(canFusion())
			{
				cookTime++;
				
				if(cookTime >= totalCookTime)
				{
					fuionItem();
					cookTime = 0;
				}
			}
			else
			{
				cookTime = 0;
			}
		}
		else if(canFusion() && hasFuel())
		{
			this.fuel = 10;
		}
	}

	private boolean hasFuel()
	{
		return this.fuel != 0;
	}

	private void fuionItem()
	{
		
	}

	private boolean canFusion()
	{
		return true;
	}
}
