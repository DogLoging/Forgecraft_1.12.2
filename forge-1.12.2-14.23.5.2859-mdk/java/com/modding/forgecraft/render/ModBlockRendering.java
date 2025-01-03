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
		
		register("copper_brick", ModBlocks.copper_brick);
		register("steel_brick", ModBlocks.steel_brick);
		register("titanium_brick", ModBlocks.titanium_brick);
		register("adamantium_brick", ModBlocks.adamantium_brick);
		
		register("copper_pillar", ModBlocks.copper_pillar);
		register("steel_pillar", ModBlocks.steel_pillar);
		register("titanium_pillar", ModBlocks.titanium_pillar);
		register("adamantium_pillar", ModBlocks.adamantium_pillar);
		
		register("copper_stair", ModBlocks.copper_stair);
		register("steel_stair", ModBlocks.steel_stair);
		register("titanium_stair", ModBlocks.titanium_stair);
		register("adamantium_stair", ModBlocks.adamantium_stair);
		
		register("copper_slab", ModBlocks.copper_slab);
		register("copper_double_slab", ModBlocks.copper_double_slab);
		register("steel_slab", ModBlocks.steel_slab);
		register("steel_double_slab", ModBlocks.steel_double_slab);
		register("titanium_slab", ModBlocks.titanium_slab);
		register("titanium_double_slab", ModBlocks.titanium_double_slab);
		register("adamantium_slab", ModBlocks.adamantium_slab);
		register("adamantium_double_slab", ModBlocks.adamantium_double_slab);
		
		register("fusion_furnace", ModBlocks.fusion_furnace);
	}
	
	public static void register(String name, Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Main.MODID + ":" + name, "inventory"));
	}
}
