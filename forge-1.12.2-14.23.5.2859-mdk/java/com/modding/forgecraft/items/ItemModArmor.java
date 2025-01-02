package com.modding.forgecraft.items;

import java.util.List;
import java.util.UUID;

import com.modding.forgecraft.Main;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemModArmor extends ItemArmor
{
	private String armorName;
	private int attackDamage, health, moveSpeed, defense;
	
	public ItemModArmor(String name, ArmorMaterial material, int attackDamage, int health, int moveSpeed, int defense, EntityEquipmentSlot slot)
	{
		super(material, 0, slot);
		
		this.armorName = name;
		
        this.attackDamage = attackDamage;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.defense = defense;
	}
	
    public String getName()
    {
        return armorName;
    }
	
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
    	boolean leggings = this.getUnlocalizedName().contains("leggings");
    	String type1 = leggings ? "layer_2" : "layer_1";
    	
    	return Main.MODID + ":" + "textures/armor/" + this.armorName + "_" + type1 + ".png";
    }
    
    private boolean isFullArmorSet(EntityPlayer player)
    {
        ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        
        return head.getItem() instanceof ItemModArmor && 
        	   chest.getItem() instanceof ItemModArmor && 
        	   legs.getItem() instanceof ItemModArmor && 
        	   feet.getItem() instanceof ItemModArmor && 
        	   ((ItemModArmor) head.getItem()).getName().equals(armorName) &&
        	   ((ItemModArmor) chest.getItem()).getName().equals(armorName) &&
        	   ((ItemModArmor) legs.getItem()).getName().equals(armorName) &&
        	   ((ItemModArmor) feet.getItem()).getName().equals(armorName);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        if (!world.isRemote)
        {
            applyAttributes(player);
        }
    }

    private void applyAttributes(EntityPlayer player)
    {
        boolean hasFullSet = isFullArmorSet(player);
        
        if (hasFullSet)
        {
        	if(this.defense != 0)
        	{
	            double currentArmor = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).getBaseValue();
	            double newArmorValue = currentArmor + this.defense;
	            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(newArmorValue);
	        	
	            double currentArmorToughness = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue();
	            double newArmorToughnessValue = currentArmorToughness + (this.defense - 1);
	            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(newArmorToughnessValue);
	        }
            addAttributeModifier(player, SharedMonsterAttributes.ATTACK_DAMAGE, "ArmorAttackDamage", attackDamage, 0);
            addAttributeModifier(player, SharedMonsterAttributes.MAX_HEALTH, "ArmorMaxHealth", health, 0);
            addAttributeModifier(player, SharedMonsterAttributes.MOVEMENT_SPEED, "ArmorMoveSpeed", moveSpeed / 100.0, 1);
        }
        else
        {
            removeModifiers(player);
        }
    }

    private void addAttributeModifier(EntityPlayer player, IAttribute attribute, String name, double value, int operation)
    {
        IAttributeInstance instance = player.getEntityAttribute(attribute);

        if (instance != null && instance.getModifier(UUID.nameUUIDFromBytes(name.getBytes())) == null)
        {
            AttributeModifier modifier = new AttributeModifier(UUID.nameUUIDFromBytes(name.getBytes()), name, value, operation);
            instance.applyModifier(modifier);
        }
    }

    private void removeModifiers(EntityPlayer player)
    {
    	player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(0.0);
    	
        for (IAttribute attribute : new IAttribute[]{SharedMonsterAttributes.ATTACK_DAMAGE, SharedMonsterAttributes.MAX_HEALTH, SharedMonsterAttributes.MOVEMENT_SPEED})
        {
            IAttributeInstance instance = player.getEntityAttribute(attribute);

            if (instance != null)
            {
                for (AttributeModifier modifier : instance.getModifiers())
                {
                    if (modifier.getName().startsWith("Armor"))
                    {
                        instance.removeModifier(modifier);
                    }
                }
            }
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.getItem() instanceof ItemModArmor)
        {
            ItemModArmor armor = (ItemModArmor) stack.getItem();
            
            tooltip.add("§7Bonus full set: ");
            
            if (armor.attackDamage < 0)
            {
                tooltip.add("§c " + armor.attackDamage + "§c Attack Damage");
            }
            else if(armor.attackDamage != 0)
            {
            	tooltip.add("§9 +" + armor.attackDamage + "§9 Attack Damage");
            }
            
            if (armor.health < 0)
            {
                tooltip.add("§c " + armor.health + "§c Health");
            }
            else if(armor.health != 0)
            {
            	tooltip.add("§9 +" + armor.health + "§9 Health");
            }
            
            if (armor.moveSpeed < 0)
            {
                tooltip.add("§c " + (armor.moveSpeed / 100.0) + "%" + "§c Speed");
            }
            else if(armor.moveSpeed != 0)
            {
            	tooltip.add("§9 +" + (armor.moveSpeed / 100.0) + "%" + "§9 Speed");
            }
            
            if (armor.defense < 0)
            {
                tooltip.add("§c " + (armor.defense - 1) + "§c Armor Toughness");
            }
            else if(armor.defense != 0)
            {
            	tooltip.add("§9 +" + (armor.defense - 1) + "§9 Armor Toughness");
            }
            
            if (armor.defense < 0)
            {
                tooltip.add("§c " + armor.defense + "§c Armor");
            }
            else if(armor.defense != 0)
            {
            	tooltip.add("§9 +" + armor.defense + "§9 Armor");
            }
        }
    }
}
