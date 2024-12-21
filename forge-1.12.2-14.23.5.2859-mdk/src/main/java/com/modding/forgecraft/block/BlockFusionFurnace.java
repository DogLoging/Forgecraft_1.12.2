package com.modding.forgecraft.block;

import java.util.Random;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.block.tileentity.TileEntityFusionFurnace;
import com.modding.forgecraft.init.ModBlocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFusionFurnace extends BlockContainer implements ITileEntityProvider
{	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool PROCESS = PropertyBool.create("process");
	public static boolean isFuel;
	
	public BlockFusionFurnace()
	{
		super(Material.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PROCESS, false));
	}
	
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	return Item.getItemFromBlock(ModBlocks.fusion_furnace);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.fusion_furnace);
    }
    
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
        if (!world.isRemote)
        {
            player.openGui(Main.instance, Main.GUI_ENUM.FUSION.ordinal(), world, pos.getX(), pos.getY(), pos.getZ()); 
        }
        
        return true;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if(!world.isRemote)
		{
			IBlockState north = world.getBlockState(pos.north());
			IBlockState south = world.getBlockState(pos.south());
			IBlockState west = world.getBlockState(pos.west());
			IBlockState east = world.getBlockState(pos.east());
			
			EnumFacing facing = (EnumFacing)state.getValue(FACING);
			
			if(facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
			{
				facing = EnumFacing.SOUTH;
			}
			else if(facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
			{
				facing = EnumFacing.NORTH;
			}
			else if(facing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) 
			{
				facing = EnumFacing.EAST;
			}
			else if(facing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
			{
				facing = EnumFacing.WEST;
			}
			
			world.setBlockState(pos, state.withProperty(FACING, facing), 2);
		}
	}
	
	   @SideOnly(Side.CLIENT)
	    @SuppressWarnings("incomplete-switch")
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
	        if(isFuel)
	        {
	            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
	            double d0 = (double)pos.getX() + 0.5D;
	            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
	            double d2 = (double)pos.getZ() + 0.5D;
	            double d4 = rand.nextDouble() * 0.6D - 0.3D;

	            if (rand.nextDouble() < 0.1D)
	            {
	                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
	            }

	            switch (enumfacing)
	            {
	                case WEST:
	                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
	                    break;
	                case EAST:
	                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
	                    break;
	                case NORTH:
	                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
	                    break;
	                case SOUTH:
	                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
	                    worldIn.spawnParticle(EnumParticleTypes.LAVA, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
	            }
	        }
	    }
	
	public static void setState(boolean active, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		TileEntity tileEntity = world.getTileEntity(pos);
		
		if(active)
		{
            world.setBlockState(pos, ModBlocks.fusion_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(PROCESS, true), 3);
		}
		else
		{
            world.setBlockState(pos, ModBlocks.fusion_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(PROCESS, false), 3);
		}
		
		isFuel = active;
		
		if(tileEntity != null)
		{
			tileEntity.validate();
			world.setTileEntity(pos, tileEntity);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFusionFurnace();
	}
	
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    	world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
    	return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof TileEntityFusionFurnace)
        {
            InventoryHelper.dropInventoryItems(world, pos, (TileEntityFusionFurnace)tileentity);
        }

        super.breakBlock(world, pos, state);
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
    	return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mir)
    {
    	return state.withRotation(mir.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {PROCESS, FACING});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
}
