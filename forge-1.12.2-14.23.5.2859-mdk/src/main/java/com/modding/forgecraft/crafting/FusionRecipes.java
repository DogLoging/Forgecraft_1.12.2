package com.modding.forgecraft.crafting;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.modding.forgecraft.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FusionRecipes
{
	private static final FusionRecipes fusionRecipes = new FusionRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> fusionRecipeList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static FusionRecipes instance()
	{
		return fusionRecipes;
	}
	
	public FusionRecipes()
	{
		addFusionRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.copper_ingot), 10.0F);
		addFusionRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), new ItemStack(ModItems.steel_ingot), 10.0F);
		addFusionRecipe(new ItemStack(ModItems.copper_ingot), new ItemStack(Items.DIAMOND), new ItemStack(ModItems.titanium_ingot), 25.0F);
		addFusionRecipe(new ItemStack(ModItems.steel_ingot), new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModItems.adamantium_ingot), 25.0F);

	}
	
	public void addFusionRecipe(ItemStack slot_1, ItemStack slot_2, ItemStack result, float exp)
	{
		if(this.getFusionResult(slot_1, slot_2) != ItemStack.EMPTY) return;
		
		this.fusionRecipeList.put(slot_1, slot_2, result);
		this.experienceList.put(result, Float.valueOf(exp));
	}
	
	public ItemStack getFusionResult(ItemStack slot_1, ItemStack slot_2)
	{
        for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.fusionRecipeList.columnMap().entrySet())
        {
            if (this.comparationItem(slot_1, (ItemStack)entry.getKey()))
            {
                for(Entry<ItemStack, ItemStack> input : entry.getValue().entrySet())
                {
                	if(this.comparationItem(slot_2, entry.getKey()))
                	{
                		return (ItemStack)input.getValue();
                	}
                }
            }
        }

        return ItemStack.EMPTY;
	}

	private boolean comparationItem(ItemStack reult, ItemStack input)
	{
		return input.getItem() == reult.getItem() && input.getMetadata() == 32767 && input.getMetadata() == reult.getMetadata();
	}
	
    public float getSmeltingExperience(ItemStack stack)
    {
        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if (this.comparationItem(stack, entry.getKey()))
            {
                return ((Float)entry.getValue()).floatValue();
            }
        }

        return 0.0F;
    }
}
