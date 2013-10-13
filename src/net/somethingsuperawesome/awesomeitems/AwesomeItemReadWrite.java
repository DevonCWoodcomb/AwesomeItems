package net.somethingsuperawesome.awesomeitems;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.somethingsuperawesome.awesomeitems.AwesomeItem.Flag;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

public class AwesomeItemReadWrite
{
	private static File itemDataFile;
	public static void readAll()
	{
		AwesomeItems.awesomeItems = new HashMap<Integer, AwesomeItem>();
		itemDataFile = new File(AwesomeItems.plugin.getDataFolder(), "Items");
		itemDataFile.mkdirs();
		for(File itemFile : itemDataFile.listFiles())
		{
			readItem(itemFile);
		}
	}
	
	public static void readItem(File itemFile)
	{
		FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);
		String displayName, itemType, itemIdString;
		int itemId;
		List<String> specialLore;
		itemIdString = itemFile.getName();
		itemIdString = itemIdString.substring(0, itemIdString.length()-4);
		AwesomeItems.log.info("Reading file: " + itemFile.getName() + " stripped id: " + itemIdString);
		try
		{
			itemId = Integer.parseInt(itemIdString);
		}
		catch(Exception e)
		{
			AwesomeItems.log.info(ChatColor.RED + "Invalid file name, item not read: " + itemIdString);
			return;
		}	
		
		if(!itemConfig.isString("Display Name"))
			itemConfig.set("Display Name", "");
		displayName = itemConfig.getString("Display Name");
		
		specialLore = itemConfig.getStringList("Special Lore");
		
		if(!itemConfig.isString("Item Type"))
			itemConfig.set("Item Type", "");
		itemType = itemConfig.getString("Item Type");
		Material material = Material.getMaterial(itemType);
		if(material == null)
		{
			AwesomeItems.log.info(ChatColor.RED + "Invalid Material type: " + itemType);
			return;
		}
		
		AwesomeItem readItem = new AwesomeItem(itemId, material, displayName, specialLore);
		
		if(!itemConfig.isDouble("Sell Value"))
			itemConfig.set("Sell Value", 0.0);
		readItem.setSellValue(itemConfig.getDouble("Sell Value"));
		
		if(material.equals(Material.LEATHER_BOOTS) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_HELMET))
		{
			if(!itemConfig.isInt("Leather Armor Color.Red"))
				itemConfig.set("Leather Armor Color.Red", 0);
			readItem.setLeatherRed(itemConfig.getInt("Leather Armor Color.Red"));

			if(!itemConfig.isInt("Leather Armor Color.Green"))
				itemConfig.set("Leather Armor Color.Green", 0);
			readItem.setLeatherGreen(itemConfig.getInt("Leather Armor Color.Green"));
			
			if(!itemConfig.isInt("Leather Armor Color.Blue"))
				itemConfig.set("Leather Armor Color.Blue", 0);
			readItem.setLeatherBlue(itemConfig.getInt("Leather Armor Color.Blue"));
		}
		
		for(Flag f : Flag.values())
		{
			if(!itemConfig.isBoolean("Flag."+f.getName()))
				itemConfig.set("Flag."+f.getName(), true);
			readItem.setFlag(f, itemConfig.getBoolean("Flag."+f.getName()));
		}
		
		for(Enchantment e : Enchantment.values())
		{
			if(!itemConfig.isInt("Enchantment."+e.getName()))
				itemConfig.set("Enchantment."+e.getName(), 0);
			readItem.setEnchant(e, itemConfig.getInt("Enchantment."+e.getName()));
		}
		
		for(EntityType et : EntityType.values())
		{
			if(et.isAlive())
			{
				if(!itemConfig.isDouble("Entity Damage Modifier."+et.toString()))
					itemConfig.set("Entity Damage Modifier."+et.toString(), 1.0);
				readItem.setEntityDamageModifier(et, itemConfig.getDouble("Entity Damage Modifier."+et.toString()));
			}
		}
		
		try
		{
			itemConfig.save(itemFile);
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		if(itemId > AwesomeItem.getMaxId())
			AwesomeItem.setMaxId(itemId);
		
		AwesomeItems.awesomeItems.put(itemId, readItem);
	}
	
	public static void writeItem(AwesomeItem item)
	{
		itemDataFile = new File(AwesomeItems.plugin.getDataFolder(), "Items");
		itemDataFile.mkdirs();
		File itemFile = new File(itemDataFile, item.getItemId() + ".yml");
		try
		{
			itemFile.createNewFile();
			FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);
			itemConfig.set("Display Name", item.getDisplayName());
			Material material = item.getItemType();
			itemConfig.set("Item Type", material.toString());
			itemConfig.set("Sell Value", item.getSellValue());
			
			if(material.equals(Material.LEATHER_BOOTS) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_HELMET))
			{
				itemConfig.set("Leather Armor Color.Red", item.getLeatherRed());

				itemConfig.set("Leather Armor Color.Green", item.getLeatherGreen());
				
				itemConfig.set("Leather Armor Color.Blue", item.getLeatherBlue());
			}
					
			for(Flag f : Flag.values())
			{
				itemConfig.set("Flag." + f.getName(), item.getFlag(f));
			}
			for(Enchantment e : Enchantment.values())
			{
				itemConfig.set("Enchantment."+e.getName(), item.getEnchantLevel(e));
			}
			for(EntityType et : EntityType.values())
			{
				if(et.isAlive())
				{
					itemConfig.set("Entity Damage Modifier."+et.toString(), item.getEntityDamageModifier(et));
				}
			}
			itemConfig.save(itemFile);	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
