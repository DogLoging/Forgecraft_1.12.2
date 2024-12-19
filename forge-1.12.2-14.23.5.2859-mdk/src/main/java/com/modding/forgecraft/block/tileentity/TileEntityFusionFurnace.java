package com.modding.forgecraft.block.tileentity;

import com.modding.forgecraft.block.BlockFusionFurnace;
import com.modding.forgecraft.block.container.ContainerFusionFurnace;
import com.modding.forgecraft.crafting.FusionRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFusionFurnace extends TileEntityLockable implements IInventory, ITickable
{
	private NonNullList<ItemStack> fusionItemStacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	
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
	public boolean hasCustomName()
	{
		return tileEntityName != null && !tileEntityName.isEmpty();
	}
	
	public void setCustomName(String customName)
	{
		this.tileEntityName = customName;
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.tileEntityName : "container.name";
	}
	
	@Override
	public int getSizeInventory()
	{
		return fusionItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : fusionItemStacks)
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
		return fusionItemStacks.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.fusionItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.fusionItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.fusionItemStacks.get(index);
		boolean isAlreadyInSlot = stack != null && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		fusionItemStacks.set(index, stack);
		
		if(stack != null && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if(index == 0 && index + 1 == 1 && !isAlreadyInSlot)
		{
			ItemStack itemstack2 = (ItemStack)fusionItemStacks.get(index + 1);
			
			totalFusionTime = getFusionTime(itemstack, itemstack2);
			timeFusion = 0;
			
			totalProcessTime = getFusionTime(itemstack, itemstack2);
			timeProcess = 0;
			
			processTotalBurn = 0;
			
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
        this.fusionItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.fusionItemStacks);
        
        this.processTotalBurn = compound.getInteger("BurnTime");
        this.timeFusion = compound.getInteger("FusionTime");
        this.totalFusionTime = compound.getInteger("TotalFusionTime");
        
        this.timeProcess = compound.getInteger("Progress");
        this.totalProcessTime = compound.getInteger("FinalProgress");
        
        this.fuelFusionFurnace = getItemFuel(this.fusionItemStacks.get(2));
        
        if(compound.hasKey("CustomName", 8))
        {
        	this.tileEntityName = compound.getString("CustomName");
        }
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)processTotalBurn);
		compound.setInteger("FusionTime", (short)timeFusion);
		compound.setInteger("TotalFusionTime", (short)totalFusionTime);
		
		compound.setInteger("Progress", (short)timeProcess);
		compound.setInteger("FinalProgress", (short)totalProcessTime);
		
		ItemStackHelper.saveAllItems(compound, this.fusionItemStacks);
		
		if(this.hasCustomName())
		{
			compound.setString("CustomName", tileEntityName);
		}
		
		return compound;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
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
			ItemStack stack = this.fusionItemStacks.get(2); //combustivel
			
			if(this.isFuel() || !stack.isEmpty() && !((((ItemStack)this.fusionItemStacks.get(0)).isEmpty()) || ((ItemStack)this.fusionItemStacks.get(1)).isEmpty()))
			{
				if(!isFuel() && canFusion())
				{
					this.fuelFusionFurnace = getItemFuel(stack);
					
					if(isFuel())
					{
						flag_1 = true;
						
						if(!stack.isEmpty())
						{
	                         Item item = stack.getItem();
	                         stack.shrink(1);
	                         
	                         if(stack.isEmpty())
	                         {
	                        	 ItemStack item_1 = item.getContainerItem(stack);
	                        	 this.fusionItemStacks.set(2, item_1);
	                         }
						}
					}
				}
				
				if(isFuel() && canFusion())
				{
					++timeFusion;
					
					if(this.timeFusion == this.totalFusionTime)
					{
						this.processTotalBurn += 1;
						this.timeFusion = 0;
						this.totalFusionTime = getFusionTime(this.fusionItemStacks.get(0), this.fusionItemStacks.get(1));
						
						if(processTotalBurn == 10)
						{
							timeProcess += 1;
							this.fuelFusionFurnace -= 1;
							
							if(timeProcess == totalProcessTime)
							{
								timeProcess = 0;
								totalProcessTime = getFusionTime(this.fusionItemStacks.get(0), this.fusionItemStacks.get(1));
								fusionItem();
								
								flag_1 = true;
							}
							
							this.processTotalBurn = 0;
						}
					}
				}
				else
				{
					timeFusion = 0;
					timeProcess = 0;
					processTotalBurn = 0;
				}
			}
			else if(!isFuel() && this.timeFusion > 0)
			{
				this.timeFusion = MathHelper.clamp(this.timeFusion - 2, 0, this.totalFusionTime);
			}
			
            if (flag != this.isFuel())
            {
                flag_1 = true;
                BlockFusionFurnace.setState(this.isFuel(), world, pos);
            }
		}
		
		if(flag_1)
		{
			this.markDirty();
		}
	}
	
	private int getFusionTime(ItemStack slot_1, ItemStack slot_2)
	{
		if(this.fuelFusionFurnace > 200)
		{
			return 400;
		}
		else
		{
			return 200;
	
		}
	}
	
	private boolean canFusion()
	{
		if(((ItemStack)this.fusionItemStacks.get(0)).isEmpty() && ((ItemStack)this.fusionItemStacks.get(1)).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack stack = FusionRecipes.instance().getFusionResult(this.fusionItemStacks.get(0), this.fusionItemStacks.get(1));
			
			if(stack.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack result = this.fusionItemStacks.get(3);
				
				if(result.isEmpty())
				{
					return true;
				}
				else if(!result.isItemEqual(stack))
				{
					return false;
				}
				
				int res = result.getCount() + result.getCount();
				
				return res <= this.getInventoryStackLimit() && res <= result.getMaxStackSize();
			}
		}
	}
	
	private void fusionItem()
	{
        if (this.canFusion())
        {
            ItemStack input1 = this.fusionItemStacks.get(0);
            ItemStack input2 = this.fusionItemStacks.get(1);
            ItemStack result = FusionRecipes.instance().getFusionResult(input1, input2);

            ItemStack output = this.fusionItemStacks.get(3);

            if (output.isEmpty())
            {
                this.fusionItemStacks.set(3, result.copy());
            }
            else if (output.isItemEqual(result))
            {
                output.grow(result.getCount());
            }

            input1.shrink(1);
            input2.shrink(1);
        }
	}
	
	@SuppressWarnings("deprecation")
	private static int getItemFuel(ItemStack stack)
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
                return 25;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOOL))
            {
                return 30;
            }
            else if (item == Item.getItemFromBlock(Blocks.CARPET))
            {
                return 4;
            }
            else if (item == Item.getItemFromBlock(Blocks.LADDER))
            {
                return 15;
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
                return 45;
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
            if (item == Items.BLAZE_ROD)
            {
                return 150;
            }
            else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
            {
                return 20;
            }
            
            return GameRegistry.getFuelValue(stack);
        }
	}
	
	public static boolean isItemFuel(ItemStack stack)
	{
		return getItemFuel(stack) > 0;
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
		if(index == 3)
		{
			return false;
		}
		else if(index != 2)
		{
			return true;
		}
		else
		{
			return isItemFuel(stack);
		}
	}
	
	@Override
	public String getGuiID()
	{
		return "forgecraft:fusion_furnace";
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFusionFurnace(playerInventory, this);
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
		return 6;
	}

	@Override
	public void clear()
	{
		this.fusionItemStacks.clear();
	}
}
