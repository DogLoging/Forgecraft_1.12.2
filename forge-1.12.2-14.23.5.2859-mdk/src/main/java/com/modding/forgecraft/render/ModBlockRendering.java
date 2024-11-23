package com.modding.forgecraft.render;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBlockRendering
{
	@SubscribeEvent
	public void onModelRegisterEvent(ModelRegistryEvent evnet)
	{
		register("copper_block", ModBlocks.copper_block);
		register("steel_block", ModBlocks.steel_block);
		register("titanium_block", ModBlocks.titanium_block);
		register("adamantium_block", ModBlocks.adamantium_block);
	}
	
	public static void register(String name, Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Main.MODID + ":" + name, "inventory"));
	}
}
