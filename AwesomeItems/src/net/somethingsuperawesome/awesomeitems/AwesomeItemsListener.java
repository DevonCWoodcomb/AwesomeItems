package net.somethingsuperawesome.awesomeitems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class AwesomeItemsListener implements Listener
{
	private final String legendaryCode = ""+ChatColor.RED + ChatColor.GREEN + ChatColor.BLUE + ChatColor.BLACK;
	private List<AwesomeItemsStoredPlayerItems> storedPlayers = new ArrayList<AwesomeItemsStoredPlayerItems>();
	//public Logger log = Logger.getLogger("Minecraft");
	public AwesomeItemsListener(){}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(event.getPlayer().getItemInHand().hasItemMeta())
		{
			if(event.getPlayer().getItemInHand().getItemMeta().hasLore())
			{
				if(event.getPlayer().getItemInHand().getItemMeta().getLore().get(0).startsWith(legendaryCode))
				{
					event.getPlayer().getItemInHand().setDurability((short)0);
				}
			}
		}
	}
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		 if(event.getItemDrop().getItemStack().hasItemMeta())
		 {
			 if(event.getItemDrop().getItemStack().getItemMeta().hasLore())
			 {
				 if(event.getItemDrop().getItemStack().getItemMeta().getLore().get(0).startsWith(legendaryCode))
				 {
					 event.setCancelled(true);
					 event.getPlayer().sendMessage(ChatColor.RED+"You can't drop a Legendary Item!");
				 }
			 }
		 }
		
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		boolean store = false;
		for(Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext();)
		{
			ItemStack is = it.next();
			//log.info("Item: "+ is.toString());
			if(is.hasItemMeta())
			{
				if(is.getItemMeta().hasLore())
				{
					//log.info("Lore: "+is.getItemMeta().getLore().get(0)+ " LC: "+legendaryCode);
					if(is.getItemMeta().getLore().get(0).startsWith(legendaryCode))
					{
						//log.info("Item matched, don't drop!");
						storePlayer(event.getEntity(), store, is);
						it.remove();
						store = true;
					}
				}
			}
		}
	}
	private void storePlayer(Player p, Boolean store, ItemStack is)
	{
		boolean stored = store;
		if(stored)
		{
			//log.info("Attempting to add");
			for(AwesomeItemsStoredPlayerItems spi : storedPlayers)
			{
				if(spi.isPlayer(p))
				{
					//log.info("adding");
					spi.addItem(is);
				}			
			}
		}
		if(!stored)
		{
			//log.info("Adding new");
			storedPlayers.add(new AwesomeItemsStoredPlayerItems(p, is));
		}
		
	}
	@EventHandler
	public void onPlayerResspawn(PlayerRespawnEvent event)
	{
		
		restoreItems(event.getPlayer());
	}

	private void restoreItems(Player p)
	{
		for(Iterator<AwesomeItemsStoredPlayerItems> it = storedPlayers.iterator(); it.hasNext();)
		{
			AwesomeItemsStoredPlayerItems spi = it.next();
			//log.info("there are some items here");
			if(spi.isPlayer(p))
			{
				//log.info("Found a match, restoring");
				spi.restoreItems(p);
				it.remove();
			}
		}
	}
}
