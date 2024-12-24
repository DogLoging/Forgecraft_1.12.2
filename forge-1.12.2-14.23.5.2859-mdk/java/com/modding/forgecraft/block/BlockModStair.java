package com.modding.forgecraft.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockModStair extends BlockStairs
{
	public BlockModStair(Material material, IBlockState state)
	{
		super(state);
		this.useNeighborBrightness = true;
	}
}
