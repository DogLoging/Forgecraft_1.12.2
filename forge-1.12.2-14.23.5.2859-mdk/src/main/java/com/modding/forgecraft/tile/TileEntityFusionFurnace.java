package com.modding.forgecraft.tile;

import com.modding.forgecraft.block.BlockFusionFurnace;
import com.modding.forgecraft.inventory.ContainerFusionFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFusionFurnace extends TileEntityLockable implements IInventory, ISidedInventory, ITickable
{
	public enum slotEnum
	{
		INPUT_SLOT1, INPUT_SLOT2, INPUT_FUEL, OUTPUT_SLOT
	}
	
	private static final int[] slot_top = new int[]
			{
					slotEnum.INPUT_SLOT1.ordinal(),
					slotEnum.INPUT_SLOT2.ordinal()
			};
	private static final int[] slot_bottom = new int[]
			{
					slotEnum.INPUT_FUEL.ordinal()
			};
	
	private static final int[] slot_side = new int[]
			{
					slotEnum.OUTPUT_SLOT.ordinal()
			};
	
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
	
	@Override
	public int getSizeInventory()
	{
		return itemStackArray.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : itemStackArray)
		{
			if(!stack.isEmpty())
			{
				return false;
			}
		}
		
		return true;
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
		
		if(index == slotEnum.INPUT_SLOT1.ordinal() && index == slotEnum.INPUT_SLOT2.ordinal() && !isAlreadyInSlot)
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
		return index == slotEnum.INPUT_SLOT1.ordinal() && index == slotEnum.INPUT_SLOT2.ordinal() ? true : false;
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
		return tileEntityName != null && !tileEntityName.isEmpty();
	}
	
	public void setCustomInventoryName(String customName)
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
		return new ContainerFusionFurnace(playerInventory, this);
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
	
	public boolean isFuel()
	{
		return this.fuelFusionFurnace > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isFuel(IInventory inventory)
	{
		return inventory.getField(0) > 0;
	}

	@Override
	public void update()
	{
		boolean flag = this.isFuel();
		boolean flag_1 = false;
		
		if(!world.isRemote)
		{
			ItemStack stack = this.itemStackArray.get(slotEnum.INPUT_FUEL.ordinal()); //combustivel
			
			if(this.isFuel() && !stack.isEmpty() && !((ItemStack)this.itemStackArray.get(slotEnum.INPUT_SLOT1.ordinal())).isEmpty() && !((ItemStack)this.itemStackArray.get(slotEnum.INPUT_SLOT2.ordinal())).isEmpty())
			{
				if(!isFuel() && canFusion())
				{
					this.fuelFusionFurnace = getItemFuelTime(stack);
					
					if(fuelFusionFurnace > 400)
					{
						fuelFusionFurnace = 400;
					}
					
					if(isFuel())
					{
						flag_1 = true;
						
						if(!stack.isEmpty() && this.fuelFusionFurnace < 400)
						{
	                         Item item = stack.getItem();
	                         stack.shrink(1);
	                         
	                         if(stack.isEmpty())
	                         {
	                        	 ItemStack item_1 = item.getContainerItem(stack);
	                        	 this.itemStackArray.set(slotEnum.INPUT_FUEL.ordinal(), item_1);
	                         }
						}
					}
				}
				
				if(isFuel() && canFusion())
				{
					++timeFusion;
					
					if(this.timeFusion == this.totalFusionTime)
					{
						int count = 0;
						
						this.processTotalBurn += 1;
						this.timeFusion = 0;
						this.totalFusionTime = getFusionTime(this.itemStackArray.get(slotEnum.INPUT_SLOT1.ordinal()), this.itemStackArray.get(slotEnum.INPUT_SLOT2.ordinal()));
						
						flag_1 = true;
						
						if(processTotalBurn >= 10)
						{
							count += 1;
							timeProcess += 1;
							processTotalBurn = 0;
							
							if(timeProcess == totalProcessTime)
							{
								timeProcess = 0;
								totalProcessTime = getFusionTime(this.itemStackArray.get(slotEnum.INPUT_SLOT1.ordinal()), this.itemStackArray.get(slotEnum.INPUT_SLOT2.ordinal()));
								fusionItem();
							}
							
							if(count >= 20)
							{
								count = 0;
								fuelFusionFurnace -= 1;
							}
						}
					}
				}
				
				if(flag != isFuel())
				{
					BlockFusionFurnace.setState(isFuel(), world, pos);
				}
			}
		}
		
		if(flag_1)
		{
			this.markDirty();
		}
	}

	private void fusionItem()
	{
		
	}

	private int getFusionTime(ItemStack slot_1, ItemStack slot_2)
	{
		return 200;
	}

	private int getItemFuelTime(ItemStack stack)
	{
		if(stack.isEmpty())
		{
			return 0;
		}
		else 
		{
			Item item = stack.getItem();
			
            if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
            {
                return 15;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOOL))
            {
                return 10;
            }
            else if (item == Item.getItemFromBlock(Blocks.CARPET))
            {
                return 6;
            }
            else if (item == Item.getItemFromBlock(Blocks.LADDER))
            {
                return 30;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
            {
                return 10;
            }
            else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
            {
                return 30;
            }
            else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
            {
                return 160;
            }
            else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
            {
                return 20;
            }
            else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
            {
                return 20;
            }
            else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
            {
                return 20;
            }
            else if (item == Items.STICK)
            {
                return 10;
            }
            else if (item != Items.BOW && item != Items.FISHING_ROD)
            {
                if (item == Items.SIGN)
                {
                    return 20;
                }
                else if (item == Items.COAL)
                {
                    return 160;
                }
                else if (item == Items.LAVA_BUCKET)
                {
                    return 200;
                }
                else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL)
                {
                    if (item == Items.BLAZE_ROD)
                    {
                        return 240;
                    }
                    else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
                    {
                        return 20;
                    }
                    else
                    {
                        return item instanceof ItemBoat ? 40 : 0;
                    }
                }
                else
                {
                    return 10;
                }
            }
            else
            {
                return 30;
            }
		}
	}
	
	private boolean canFusion()
	{
		return false;
	}
	
}
