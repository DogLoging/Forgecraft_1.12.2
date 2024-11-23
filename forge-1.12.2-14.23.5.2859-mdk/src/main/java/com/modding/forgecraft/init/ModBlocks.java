package com.modding.forgecraft.init;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.block.BlockFusionFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static Block copper_block, steel_block, titanium_block, adamantium_block;
	
	public static Block fusion_furnace;
	
	public static int id;
	public static Block[] listBlock = new Block[4];
	public static Item[] listItem = new Item[4];
	
	public static void initialization()
	{
		copper_block = register("copper_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		steel_block = register("steel_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		titanium_block = register("titanium_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		adamantium_block = register("adamantium_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
	
		fusion_furnace = register("fusion_furnace", new BlockFusionFurnace());
	}
	
	public static void registerBlock(IForgeRegistry<Block> register)
	{
		register.registerAll(listBlock);
	}
	
	public static void registerItem(IForgeRegistry<Item> register)
	{
		register.registerAll(listItem);
	}
	
	public static Block register(String name, Block block)
	{
		block.setUnlocalizedName(name);
		
		listBlock[id] = block.setRegistryName(Main.MODID + ":" + name);
		listItem[id] = new ItemBlock(block).setRegistryName(Main.MODID + ":" + name);
		
		++id;
		
		return block;
	}
}
