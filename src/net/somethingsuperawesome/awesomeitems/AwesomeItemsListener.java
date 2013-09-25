package net.somethingsuperawesome.awesomeitems;


//import java.util.logging.Logger;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import org.bukkit.inventory.ItemStack;

public class AwesomeItemsListener implements Listener
{
	//private final String legendaryCode = ""+ChatColor.RED + ChatColor.GREEN + ChatColor.BLUE + ChatColor.BLACK;
	//private List<AwesomeItemsStoredPlayerItems> storedPlayers = new ArrayList<AwesomeItemsStoredPlayerItems>();
	public Logger log = Logger.getLogger("Minecraft");
	public AwesomeItemsListener(){}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event)
	{
		ItemStack item = event.getPlayer().getItemInHand();
		if(item.hasItemMeta())
		{
			if(item.getItemMeta().hasLore())
			{
				String lore0 = item.getItemMeta().getLore().get(0);
				if(AwesomeItem.isAwesomeItem(lore0))
				{
					if(!AwesomeItem.canBreakBlock(lore0))
					{
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED+"You can't break blocks with this item!");
						return;
					}
					if(!AwesomeItem.canTakeDurability(lore0))
					{
						//log.info("I am here, max dura is = " + item.getType().getMaxDurability()+" and current is: "+item.getDurability());
						item.setDurability((short)0);
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		 if(event.getItemDrop().getItemStack().hasItemMeta())
		 {
			 if(event.getItemDrop().getItemStack().getItemMeta().hasLore())
			 {
				 String lore0 = event.getItemDrop().getItemStack().getItemMeta().getLore().get(0);
				 if(AwesomeItem.isAwesomeItem(lore0))
				 {
					 if(!AwesomeItem.canDrop(lore0))
					 {
						 event.setCancelled(true);
						 event.getPlayer().sendMessage(ChatColor.RED+"You can't drop this Item!");
					 }
				 }
			 }
		 }
		
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		ItemStack[] items = event.getEntity().getInventory().getContents();
		ItemStack[] armor = event.getEntity().getInventory().getArmorContents();
		for(int k = 0; k < items.length; k++)
		{
			ItemStack is = items[k];
			breakto: 
			{
				if(is != null && is.hasItemMeta())
				{
					if(is.getItemMeta().hasLore())
					{
						String lore0 = is.getItemMeta().getLore().get(0);
						if(AwesomeItem.isAwesomeItem(lore0))
						{
							if(!AwesomeItem.dropOnDeath(lore0))
							{
								event.getDrops().remove(is);
								break breakto;
							}
						}
					}
				}
				items[k] = null;
			}
		}
		for(int k = 0; k < armor.length; k++)
		{
			ItemStack is = armor[k];
			breaktwo: 
			{
				if(is != null && is.hasItemMeta())
				{
					if(is.getItemMeta().hasLore())
					{
						String lore0 = is.getItemMeta().getLore().get(0);
						if(AwesomeItem.isAwesomeItem(lore0))
						{
							if(!AwesomeItem.dropOnDeath(lore0))
							{
								event.getDrops().remove(is);
								break breaktwo;
							}
						}
					}
				}
				armor[k] = null;
			}
		}
		returnItems(event.getEntity(), items);
		returnArmor(event.getEntity(), armor);
	}
	public void returnItems(final Player player, final ItemStack[] is)
	{
		Bukkit.getScheduler().runTaskAsynchronously(AwesomeItems.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				player.getInventory().setContents(is);
				
			}
		});
	}
	
	public void returnArmor(final Player player, final ItemStack[] is)
	{
		Bukkit.getScheduler().runTaskAsynchronously(AwesomeItems.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				player.getInventory().setArmorContents(is);
				
			}
		});
	}
}
