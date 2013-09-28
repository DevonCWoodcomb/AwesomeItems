package net.somethingsuperawesome.awesomeitems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

public class AwesomeItemReadWrite
{
	private static File itemDataFile;
	public static void readAll()
	{
		AwesomeItems.awesomeItems = new HashSet<AwesomeItem>();
		itemDataFile = new File(AwesomeItems.plugin.getDataFolder(), "Items");
		itemDataFile.mkdirs();
		for(File itemFile : itemDataFile.listFiles())
		{
			AwesomeItems.awesomeItems.add(readItem(itemFile));
		}
	}
	
	public static AwesomeItem readItem(File itemFile)
	{
		FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);
		boolean canPlaceInChest, dropOnDeath, canDrop, canTakeDurability, canBreakBlock;
		String itemName, displayName, itemType;
		List<String> specialLore;
		itemName = itemFile.getName();
		AwesomeItems.log.info(itemName);
		
		if(!itemConfig.isString("Display Name"))
			itemConfig.set("Display Name", "");
		displayName = itemConfig.getString("Display Name");
		
		if(!itemConfig.isString("Item Type"))
			itemConfig.set("Item Type", "");
		itemType = itemConfig.getString("Item Type");
		
		if(!itemConfig.isList("Special Lore"))
			itemConfig.set("Special Lore", new ArrayList<String>());
		specialLore = itemConfig.getStringList("Special Lore");
		
		if(!itemConfig.isBoolean("Can Place In Chest"))
			itemConfig.set("Can Place In Chest", true);
		canPlaceInChest = itemConfig.getBoolean("Can Place In Chest");
		
		if(!itemConfig.isBoolean("Drop On Death"))
			itemConfig.set("Drop On Death", true);
		dropOnDeath = itemConfig.getBoolean("Drop On Death");
		
		if(!itemConfig.isBoolean("Can Drop"))
			itemConfig.set("Can Drop", true);
		canDrop = itemConfig.getBoolean("Can Drop");
		
		if(!itemConfig.isBoolean("Can Take Durability"))
			itemConfig.set("Can Take Durability", true);
		canTakeDurability = itemConfig.getBoolean("Can Take Durability");
		
		if(!itemConfig.isBoolean("Can Break Block"))
			itemConfig.set("Can Break Block", true);
		canBreakBlock = itemConfig.getBoolean("Can Break Block");
		
		AwesomeItem readItem = new AwesomeItem(itemName, Material.getMaterial(itemType), displayName, specialLore, canPlaceInChest, dropOnDeath, canDrop, canTakeDurability, canBreakBlock);
		for(Enchantment e : Enchantment.values())
		{
			if(!itemConfig.isInt("Enchantment."+e.getName()))
				itemConfig.set("Enchantment."+e.getName(), 0);
			readItem.setEnchant(e, itemConfig.getInt("Enchantment."+e.getName()));
		}
		try
		{
			itemConfig.save(itemFile);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		return readItem;
	}
	
	public static void writeItem(AwesomeItem item)
	{
		itemDataFile = new File(AwesomeItems.plugin.getDataFolder(), "Items");
		itemDataFile.mkdirs();
		File itemFile = new File(itemDataFile, item.getItemName() + ".yml");
		try
		{
			itemFile.createNewFile();
			FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);
			itemConfig.set("Display Name", item.getDisplayName());
			itemConfig.set("Item Type", item.getItemType().toString());
			itemConfig.set("Special Lore", item.getSpecialLore());
			itemConfig.set("Can Place In Chest", item.isCanPlaceInChest());
			itemConfig.set("Drop On Death", item.isDropOnDeath());
			itemConfig.set("Can Drop", item.isCanDrop());
			itemConfig.set("Can Take Durability", item.isCanTakeDurability());
			itemConfig.set("Can Break Block", item.isCanBreakBlock());
			for(Enchantment e : Enchantment.values())
			{
				itemConfig.set("Enchantment."+e.getName(), item.getEnchantLevel(e));
			}
			itemConfig.save(itemFile);	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
