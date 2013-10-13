package net.playssa.awesomeitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AwesomeItem
{
	private static String AwesomeItemString = ChatColor.GREEN + "Awesome Item";
	@Setter
	@Getter
	private static int maxId;
	
	public enum Flag
	{
		BREAKBLOCKS("Break Blocks"),
		PLACEINCHEST("Place in Chest"),
		DROPONDEATH("Drop on Death"),
		DROP("Drop"),
		TAKEDURABILITY("Take Durability"),
		PLACEBLOCK("Place Block");
		
		@Getter
		private String name;
		private Flag(String name)
		{
			this.name = name;
		}
	}
	
	public static boolean isAwesomeItem(String lore)
	{
		if(lore.contains(AwesomeItemString))
			return true;
		return false;
	}
	
	
	@Setter
	@Getter
	private String displayName;
	@Setter
	@Getter
	private List<String> specialLore;
	@Setter
	@Getter
	private Material itemType;
	private HashMap<Flag, Boolean> flags;
	private HashMap<Enchantment, Integer> enchantments;
	private HashMap<EntityType, Double> entityDamageModifiers;
	@Setter
	@Getter
	private int leatherRed=0, leatherGreen=0, leatherBlue=0;
	@Getter
	private int itemId;
	@Setter
	@Getter
	private double sellValue=0.0;
	
	public AwesomeItem(Material itemType)
	{
		this.itemId = getNextId();
		this.displayName = "Item #" + itemId;
		this.itemType = itemType;
		this.specialLore = null;
		flags = new HashMap<Flag, Boolean>();
		enchantments = new HashMap<Enchantment, Integer>();
		entityDamageModifiers = new HashMap<EntityType, Double>();
		for(Flag f : Flag.values())
		{
			flags.put(f, true);
		}
		for(Enchantment e : Enchantment.values())
		{
			enchantments.put(e, 0);
		}
		for(EntityType et : EntityType.values())
		{
			if(et.isAlive())
				entityDamageModifiers.put(et, 1.0);
		}
	}
	
	public AwesomeItem(int itemId, Material itemType, String displayName, List<String> specialLore)
	{
		this.itemId = itemId;
		this.displayName = colorize(displayName);
		this.itemType = itemType;
		this.specialLore = specialLore;
		flags = new HashMap<Flag, Boolean>();
		enchantments = new HashMap<Enchantment, Integer>();
		entityDamageModifiers = new HashMap<EntityType, Double>();
		for(Flag f : Flag.values())
		{
			flags.put(f, true);
		}
		for(Enchantment e : Enchantment.values())
		{
			enchantments.put(e, 0);
		}
		for(EntityType et : EntityType.values())
		{
			if(et.isAlive())
				entityDamageModifiers.put(et, 1.0);
		}
	}
	
	private int getNextId()
	{		
		while(AwesomeItems.awesomeItems.containsKey(maxId))
		{
			maxId++;
		}			
		return maxId;
	}
	
	public static String colorize(String s)
	{
		return s.replaceAll("&(?<!&&)(?=[0-9a-fA-F])", "\u00A7").replaceAll("&&","&");
	}
	
	public static String decolorize(String s)
	{
		return s.replaceAll("\u00A7", "");
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
	
	public Double getEntityDamageModifier(EntityType et)
	{
		return entityDamageModifiers.get(et);
	}
	
	public Boolean getFlag(Flag f)
	{
		return flags.get(f);
	}
	
	public static AwesomeItem getAwesomeItem(ItemStack item)
	{
		if(item.hasItemMeta())
		{
			if(item.getItemMeta().hasLore())
			{
				String lore0 = item.getItemMeta().getLore().get(0);
				if(isAwesomeItem(lore0))
				{
					return AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
				}
			}
		}
		return null;
	}
	
	public ItemMeta getItemMeta(ItemMeta meta)
	{
		meta.setLore(getLore());
		meta.setDisplayName(getDisplayName());
		if(meta instanceof LeatherArmorMeta)
			((LeatherArmorMeta)meta).setColor(Color.fromRGB(leatherRed, leatherGreen, leatherBlue));
		return meta;
	}
	
	public void setEnchant(String enchant, int level)
	{
		enchantments.put(Enchantment.getByName(enchant), level);
	}
	
	public void setFlag(Flag f, boolean b)
	{
		flags.put(f, b);
	}
	
	public void setEntityDamageModifier(EntityType e, Double m)
	{
		entityDamageModifiers.put(e, m);
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
					lore.add(colorize(lo));
			}
		}
		StringBuilder mainLore = new StringBuilder();
		mainLore.append(AwesomeItemString+getItemIdLore());
		if(!flags.get(Flag.BREAKBLOCKS))
		{
			lore.add(ChatColor.RED + "Cannot break blocks.");
		}
		if(!flags.get(Flag.DROPONDEATH))
		{
			lore.add(ChatColor.AQUA + "Keep on death");
		}	
		lore.set(0, mainLore.toString());
		return lore;
	}//end getLore()

	private String getItemIdLore()
	{
		String id = String.valueOf(itemId);
		char[] digits = id.toCharArray();
		StringBuilder newId = new StringBuilder();
		for(char c : digits)
		{
			newId.append(ChatColor.getByChar(c).toString());
		}
		return newId.toString();
	}

	public static int getId(String lore)
	{
		lore = decolorize(lore.replace(AwesomeItemString, ""));
		int id = Integer.parseInt(lore);
		return id;
	}
}
