package net.somethingsuperawesome.awesomeitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AwesomeItem
{
	private static String AwesomeItemString = ChatColor.GREEN + "Awesome Item";
	private static String AwesomeItemCanBreakBlock = ChatColor.RED.toString();
	private static String AwesomeItemCanTakeDurability = ChatColor.YELLOW.toString();
	private static String AwesomeItemCanDrop = ChatColor.BLUE.toString();
	private static String AwesomeItemDropOnDeath = ChatColor.GOLD.toString();
	private static String AwesomeItemCanPlaceInChest = ChatColor.AQUA.toString();
	
	public static boolean isAwesomeItem(String lore)
	{
		if(lore.contains(AwesomeItemString))
			return true;
		return false;
	}
	
	public static boolean canBreakBlock(String lore)
	{
		if(lore.contains(AwesomeItemCanBreakBlock))
			return true;
		return false;
	}
	
	public static boolean canTakeDurability(String lore)
	{
		if(lore.contains(AwesomeItemCanTakeDurability))
			return true;
		return false;
	}
	
	public static boolean canDrop(String lore)
	{
		if(lore.contains(AwesomeItemCanDrop))
			return true;
		return false;
	}
	
	public static boolean dropOnDeath(String lore)
	{
		if(lore.contains(AwesomeItemDropOnDeath))
			return true;
		return false;
	}
	
	public static boolean canPlaceInChest(String lore)
	{
		if(lore.contains(AwesomeItemCanPlaceInChest))
			return true;
		return false;
	}
	@Setter
	@Getter
	private boolean canPlaceInChest, dropOnDeath, canDrop, canTakeDurability, canBreakBlock;
	@Setter
	@Getter
	private String itemName, displayName;
	@Setter
	@Getter
	private List<String> specialLore;
	@Setter
	@Getter
	private Material itemType;
	private HashMap<Enchantment, Integer> enchantments;
	
	public AwesomeItem(String itemName, Material itemType)
	{
		this.itemName = itemName;
		this.displayName = itemName;
		this.itemType = itemType;
		this.specialLore = null;
		this.canPlaceInChest = true;
		this.dropOnDeath = true;
		this.canDrop = true;
		this.canTakeDurability = true;
		this.canBreakBlock = true;
		enchantments = new HashMap<Enchantment, Integer>();
		for(Enchantment e : Enchantment.values())
		{
			enchantments.put(e, 0);
		}
	}
	
	public AwesomeItem(String itemName, Material itemType, String displayName, List<String> specialLore, boolean canPlaceInChest, boolean dropOnDeath, boolean canDrop, boolean canTakeDurability, boolean canBreakBlock)
	{
		this.itemName = itemName;
		this.displayName = colorize(displayName);
		this.itemType = itemType;
		this.specialLore = specialLore;
		this.canPlaceInChest = canPlaceInChest;
		this.dropOnDeath = dropOnDeath;
		this.canDrop = canDrop;
		this.canTakeDurability = canTakeDurability;
		this.canBreakBlock = canBreakBlock;
		enchantments = new HashMap<Enchantment, Integer>();
		for(Enchantment e : Enchantment.values())
		{
			enchantments.put(e, 0);
		}
	}
	
	public static String colorize(String s)
	{
		return s.replaceAll("&(?<!&&)(?=[0-9a-fA-F])", "\u00A7").replace("&&","&");
	}
	
	public ItemStack getItemStack()
	{
		ItemStack itemstack = new ItemStack(itemType, 1);
		itemstack.addUnsafeEnchantments(getEnchantments());
		itemstack.setItemMeta(getItemMeta(itemstack.getItemMeta()));		
		
		return itemstack;
	}
	
	public int getEnchantLevel(Enchantment e)
	{
		return enchantments.get(e);
	}
	
	public ItemMeta getItemMeta(ItemMeta meta)
	{
		meta.setLore(getLore());
		meta.setDisplayName(getDisplayName());
		return meta;
	}
	
	public void setEnchant(String enchant, int level)
	{
		enchantments.put(Enchantment.getByName(enchant), level);
	}
	
	public void setEnchant(Enchantment e, int level)
	{
		enchantments.put(e, level);
	}
	
	public Map<Enchantment, Integer> getEnchantments()
	{
		Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		for(Enchantment e : Enchantment.values())
		{
			int value = enchantments.get(e);
			if(value>0)
				enchants.put(e, value);
		}	
		return enchants;
	}
	
	public List<String> getLore()
	{
		List<String> lore = new ArrayList<String>();
		lore.add("");
		if(specialLore != null)
		{
			for(String lo : specialLore)
			{
				if(lo != "")
					lore.add(lo);
			}
		}
		StringBuilder mainLore = new StringBuilder();
		mainLore.append(AwesomeItemString);
		if(canBreakBlock)
		{
			mainLore.append(AwesomeItemCanBreakBlock);
		}
		else
		{
			lore.add(ChatColor.RED + "Cannot break blocks.");
		}
		if(canPlaceInChest)
		{
			mainLore.append(AwesomeItemCanPlaceInChest);
		}
		if(dropOnDeath)
		{
			mainLore.append(AwesomeItemDropOnDeath);
		}
		else
		{
			lore.add(ChatColor.AQUA + "Keep on death");
		}
		if(canDrop)
		{
			mainLore.append(AwesomeItemCanDrop);
		}
		if(canTakeDurability)
		{
			mainLore.append(AwesomeItemCanTakeDurability);
		}		
		lore.set(0, mainLore.toString());
		return lore;
	}//end getLore()
}
