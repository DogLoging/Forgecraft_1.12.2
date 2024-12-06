package com.modding.forgecraft.tile;

import com.modding.forgecraft.Main;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistryTileEntity
{
	@SuppressWarnings("deprecation")
	public static void initialization()
	{
		GameRegistry.registerTileEntity(TileEntityFusionFurnace.class, Main.MODID + "fusion_furnace");
	}
}
