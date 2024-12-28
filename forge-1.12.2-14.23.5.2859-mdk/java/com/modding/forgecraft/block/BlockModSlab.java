package com.modding.forgecraft.block;

import java.util.Random;

import com.modding.forgecraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockModSlab extends BlockSlab
{
	private String name;
	private boolean isDouble;
	
	public BlockModSlab(String name, boolean var, Material material)
	{
		super(material);
		
		this.name = name;
		this.isDouble = var;
		this.useNeighborBrightness = true;
		this.setDefaultState(this.isDouble ? this.getDefaultState() : this.getDefaultState().withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM));
	}

	@Override
	public String getUnlocalizedName(int meta)
	{
		return this.getUnlocalizedName();
	}

	@Override
	public boolean isDouble()
	{
		return this.isDouble && this.name.contains("double");
	}

	@Override
	public IProperty<?> getVariantProperty()
	{
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack)
	{
		return null;
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(!this.isDouble())
		{
			return this.getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
		}
		
		return this.getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		if(!this.isDouble())
		{
			if(state.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM)
			{
				return 0;
			}
			else if(state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
			{
				return 1;
			}
		}
		
		return 0;
	}

    public Block getDroppedSlab()
    {
    	if(this == ModBlocks.copper_double_slab)
    	{
    		return ModBlocks.copper_slab;
    	}
    	else if(this == ModBlocks.steel_double_slab)
    	{
    		return ModBlocks.steel_slab;
    	}
    	else if(this == ModBlocks.titanium_double_slab)
    	{
    		return ModBlocks.titanium_slab; 
    	}
    	else if(this == ModBlocks.adamantium_double_slab)
    	{
    		return ModBlocks.adamantium_slab;
    	}
    	else
    	{
    		return this;
    	}
    }
	
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return this.isDouble() ? iblockstate : (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM) : iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP));
    }
    
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this.getDroppedSlab());
	}
	
	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
    	return new ItemStack(this.getDroppedSlab(), 1, 0);
    }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return this.isDouble ? new BlockStateContainer(this, new IProperty[0]) : new BlockStateContainer(this, new IProperty[] {HALF});
	}
}
