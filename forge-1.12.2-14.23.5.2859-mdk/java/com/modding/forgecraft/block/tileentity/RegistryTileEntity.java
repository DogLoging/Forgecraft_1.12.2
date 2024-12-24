package com.modding.forgecraft.block.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistryTileEntity
{
	@SuppressWarnings("deprecation")
	public static void initialization()
	{
		GameRegistry.registerTileEntity(TileEntityFusionFurnace.class, "tileEntityFusion");
	}
}
