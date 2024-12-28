package com.modding.forgecraft.init;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.block.BlockFusionFurnace;
import com.modding.forgecraft.block.BlockModSlab;
import com.modding.forgecraft.block.BlockModStair;
import com.modding.forgecraft.block.BlockPillar;
import com.modding.forgecraft.block.item.ItemModSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static Block copper_block, steel_block, titanium_block, adamantium_block;
	
	public static Block copper_brick, steel_brick, titanium_brick, adamantium_brick;
	
	public static Block copper_pillar, steel_pillar, titanium_pillar, adamantium_pillar;
	
	public static Block copper_stair, steel_stair, titanium_stair, adamantium_stair;
	
	public static Block copper_slab, steel_slab, titanium_slab, adamantium_slab;
	public static Block copper_double_slab, steel_double_slab, titanium_double_slab, adamantium_double_slab;
	
	public static Block fusion_furnace;
	
	public static int id;
	public static Block[] listBlock = new Block[25];
	public static Item[] listItem = new Item[25];
	
	public static void initialization()
	{
		copper_block = register("copper_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0F).setResistance(5.0F));
		copper_brick = register("copper_brick", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0F).setResistance(5.0F));
		copper_pillar = register("copper_pillar", new BlockPillar(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0F).setResistance(5.0F));
		copper_stair = register("copper_stair", new BlockModStair(Material.IRON, copper_brick.getDefaultState()).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0F).setResistance(5.0F));
		
		copper_double_slab = register("copper_double_slab", new BlockModSlab("copper_double_slab", true, Material.IRON).setCreativeTab(null).setHardness(5.0F).setResistance(5.0F));
		copper_slab = registerSlab("copper_slab", new BlockModSlab("copper_slab", false , Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0F).setResistance(5.0F), copper_double_slab);
		
		steel_block = register("steel_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(10.0F).setResistance(10.0F));
		steel_brick = register("steel_brick", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(10.0F).setResistance(10.0F));
		steel_pillar = register("steel_pillar", new BlockPillar(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(10.0F).setResistance(10.0F));
		steel_stair = register("steel_stair", new BlockModStair(Material.IRON, steel_brick.getDefaultState()).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(10.0F).setResistance(10.0F));
		
		steel_double_slab = register("steel_double_slab", new BlockModSlab("steel_double_slab", true, Material.IRON).setCreativeTab(null).setHardness(10.0F).setResistance(10.0F));
		steel_slab = registerSlab("steel_slab", new BlockModSlab("steel_slab", false, Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(10.0F).setResistance(10.0F), steel_double_slab);
		
		titanium_block = register("titanium_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(15.0F).setResistance(15.0F));
		titanium_brick = register("titanium_brick", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(15.0F).setResistance(15.0F));
		titanium_pillar = register("titanium_pillar", new BlockPillar(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(15.0F).setResistance(15.0F));
		titanium_stair = register("titanium_stair", new BlockModStair(Material.IRON, titanium_brick.getDefaultState()).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(15.0F).setResistance(15.0F));
		
		titanium_double_slab = register("titanium_double_slab", new BlockModSlab("titanium_double_slab", true, Material.IRON).setCreativeTab(null).setHardness(15.0F).setResistance(15.0F));
		titanium_slab = registerSlab("titanium_slab", new BlockModSlab("titanium_slab", false, Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(15.0F).setResistance(15.0F), titanium_double_slab);
		
		adamantium_block = register("adamantium_block", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(25.0F).setResistance(25.0F));
		adamantium_brick = register("adamantium_brick", new Block(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(25.0F).setResistance(25.0F));
		adamantium_pillar = register("adamantium_pillar", new BlockPillar(Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(25.0F).setResistance(25.0F));
		adamantium_stair = register("adamantium_stair", new BlockModStair(Material.IRON, adamantium_brick.getDefaultState()).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(25.0F).setResistance(25.0F));
		
		adamantium_double_slab = register("adamantium_double_slab", new BlockModSlab("adamantium_double_slab", true, Material.IRON).setCreativeTab(null).setHardness(25.0F).setResistance(25.0F));
		adamantium_slab = registerSlab("adamantium_slab", new BlockModSlab("adamantium_slab", false, Material.IRON).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(25.0F).setResistance(25.0F), adamantium_double_slab);
		
		fusion_furnace = register("fusion_furnace", new BlockFusionFurnace().setCreativeTab(CreativeTabs.DECORATIONS).setHardness(50.0F).setResistance(99.0F));
	}
	
	public static void initializeHarvestLevels()
	{
		copper_block.setHarvestLevel("pickaxe", 1);
		steel_block.setHarvestLevel("pickaxe", 2);
		titanium_block.setHarvestLevel("pickaxe", 3);
		adamantium_block.setHarvestLevel("pickaxe", 3);
		
		copper_brick.setHarvestLevel("pickaxe", 1);
		steel_brick.setHarvestLevel("pickaxe", 2);
		titanium_brick.setHarvestLevel("pickaxe", 3);
		adamantium_brick.setHarvestLevel("pickaxe", 3);
		
		copper_pillar.setHarvestLevel("pickaxe", 1);
		steel_pillar.setHarvestLevel("pickaxe", 2);
		titanium_pillar.setHarvestLevel("pickaxe", 3);
		adamantium_pillar.setHarvestLevel("pickaxe", 3);
		
		copper_stair.setHarvestLevel("pickaxe", 1);
		steel_stair.setHarvestLevel("pickaxe", 2);
		titanium_stair.setHarvestLevel("pickaxe", 3);
		adamantium_stair.setHarvestLevel("pickaxe", 3);
		
		fusion_furnace.setHarvestLevel("pickaxe", 3);
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
	
	public static Block registerSlab(String name, Block slab_half, Block slab_double)
	{
		slab_half.setUnlocalizedName(name);
		
		listBlock[id] = slab_half.setRegistryName(Main.MODID + ":" + name);
		listItem[id] = new ItemModSlab(slab_half, (BlockSlab)slab_half, (BlockSlab)slab_double).setRegistryName(Main.MODID + ":" + name);
		
		++id;
		
		return slab_half;
	}
}
