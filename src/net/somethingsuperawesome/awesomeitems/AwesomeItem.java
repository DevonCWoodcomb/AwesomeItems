package net.somethingsuperawesome.awesomeitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

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
	
	private boolean canPlaceInChest, dropOnDeath, canDrop, canTakeDurability, canBreakBlock;
	private String itemName, specialLore;
	
	public AwesomeItem(String itemName)
	{
		this.itemName = itemName;
		this.specialLore = "";
		this.canPlaceInChest = true;
		this.dropOnDeath = false;
		this.canDrop = false;
		this.canTakeDurability = false;
		this.canBreakBlock = false;
	}
	
	public AwesomeItem(String itemName, String specialLore, boolean canPlaceInChest, boolean dropOnDeath, boolean canDrop, boolean canTakeDurability, boolean canBreakBlock)
	{
		this.itemName = itemName;
		this.specialLore = specialLore;
		this.canPlaceInChest = canPlaceInChest;
		this.dropOnDeath = dropOnDeath;
		this.canDrop = canDrop;
		this.canTakeDurability = canTakeDurability;
		this.canBreakBlock = canBreakBlock;
	}
		
	public String getItemName()
	{
		return itemName;
	}
	
	public List<String> getLore()
	{
		List<String> lore = new ArrayList<String>();
		lore.add("");
		if(specialLore != "")
		lore.add(specialLore);
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
