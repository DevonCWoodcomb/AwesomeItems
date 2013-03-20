package net.somethingsuperawesome.awesomeitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AwesomeItemsStoredPlayerItems
{
	private Player p;
	private List<ItemStack> items;
	
	public AwesomeItemsStoredPlayerItems(){}
	public AwesomeItemsStoredPlayerItems(Player pl, ItemStack is)
	{
		p=pl;
		items = new ArrayList<ItemStack>();
		addItem(is);
	}
	public void addItem(ItemStack is)
	{
		items.add(is);
	}
	public void restoreItems(Player pl)
	{
		for(ItemStack is : items)
		{
			pl.getInventory().addItem(is);
		}
	}
	public boolean isPlayer(Player pl)
	{
		return pl.getName().equalsIgnoreCase(p.getName());
	}
}
