package net.playssa.awesomeitems;


//import java.util.logging.Logger;

import java.util.logging.Logger;

import net.playssa.awesomeitems.AwesomeItem.Flag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AwesomeItemsListener implements Listener
{
	public Logger log = Logger.getLogger("Minecraft");
	public AwesomeItemsListener(){}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event)
	{
		ItemStack item = event.getPlayer().getItemInHand();
		AwesomeItem eventItem = AwesomeItem.getAwesomeItem(item);
		if(eventItem == null)
			return;
		if(!eventItem.getFlag(Flag.BREAKBLOCKS))
		{
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED+"You can't break blocks with this item!");
			return;
		}
		if(!eventItem.getFlag(Flag.TAKEDURABILITY))
		{
			repairItem(item);
		}
	}
	public void repairItem(final ItemStack is)
	{
		Bukkit.getScheduler().runTaskAsynchronously(AwesomeItems.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				AwesomeItems.log.info("Repairing an item of type: "+ is.getType());
				is.setDurability((short)0);
			}
		});
	}	
	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		AwesomeItem eventItem = AwesomeItem.getAwesomeItem(event.getItemInHand());
		if(eventItem == null)
			return;
		event.getPlayer().sendMessage("You cannot place Awesome Items.");
		event.setCancelled(true);
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
					 AwesomeItem eventItem = AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
					 if(!eventItem.getFlag(Flag.DROP))
					 {
						 event.setCancelled(true);
						 event.getPlayer().sendMessage(ChatColor.RED+"You can't drop this Item!");
					 }
				 }
			 }
		 }
		
	}
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onAttack(EntityDamageByEntityEvent event)
	{
		if(event.getDamager().getType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getDamager();
			ItemStack weapon = player.getItemInHand();
			AwesomeItem eventItem = AwesomeItem.getAwesomeItem(weapon);
			if(eventItem != null)
			{
				event.setDamage(event.getDamage() * eventItem.getEntityDamageModifier(event.getEntityType()));
				if(!eventItem.getFlag(Flag.TAKEDURABILITY))
				{
					repairItem(weapon);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onDamage(EntityDamageEvent event)
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getEntity();
			ItemStack[] armor = player.getInventory().getArmorContents();
			for(ItemStack is : armor)
			{
				AwesomeItem eventItem = AwesomeItem.getAwesomeItem(is);
				if(!eventItem.getFlag(Flag.TAKEDURABILITY))
				{
					repairItem(is);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event)
	{
		ItemStack eventItem;
		Inventory eventInv = event.getInventory();
		AwesomeItems.log.info("Inventory Type = " + eventInv.getType()+" slot type: "+event.getSlotType());
		if(!(eventInv.getType() == InventoryType.CREATIVE || eventInv.getType() == InventoryType.PLAYER || (eventInv.getType() == InventoryType.CRAFTING && !(event.getSlotType() == InventoryType.SlotType.CRAFTING))))
		{
			if(event.isShiftClick())
			{
				eventItem = event.getCurrentItem();	
				String lore0;
				try
				{
					lore0 = eventItem.getItemMeta().getLore().get(0);
				}
				catch(NullPointerException e)
				{
					return;
				}
				if(AwesomeItem.isAwesomeItem(lore0))
				{
					if(eventInv.getType() == InventoryType.CHEST || eventInv.getType() == InventoryType.ENDER_CHEST)
					{
						AwesomeItem eventAItem = AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
						if(!eventAItem.getFlag(Flag.PLACEINCHEST))
						{
							((Player)(event.getWhoClicked())).sendMessage(ChatColor.RED + "You can't put that there!");
							event.setCancelled(true);
							return;
						}
					}
					else
					{
						((Player)(event.getWhoClicked())).sendMessage(ChatColor.RED + "You can't put that there!");
						event.setCancelled(true);
						return;
					}
					
				}
			}
			else
			{
				eventItem = event.getCursor();
				String lore0;
				try
				{
					lore0 = eventItem.getItemMeta().getLore().get(0);
				}
				catch(NullPointerException e)
				{
					return;
				}
				if(AwesomeItem.isAwesomeItem(lore0))
				{
					if(eventInv.getType() == InventoryType.CHEST || eventInv.getType() == InventoryType.ENDER_CHEST)
					{
						if (event.getRawSlot() < eventInv.getSize())
						{
							AwesomeItem eventAItem = AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
							if(!eventAItem.getFlag(Flag.PLACEINCHEST))
							{
								((Player)(event.getWhoClicked())).sendMessage(ChatColor.RED + "You can't put that there!");
								event.setCancelled(true);
								return;
							}
						}
					}
					else
					{
						if (event.getRawSlot() < eventInv.getSize())
						{
							((Player)(event.getWhoClicked())).sendMessage(ChatColor.RED + "You can't put that there!");
							event.setCancelled(true);
							return;
						}
					}
				}
			}	
		}
	}	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
							AwesomeItem eventItem = AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
							if(!eventItem.getFlag(Flag.DROPONDEATH))
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
							AwesomeItem eventItem = AwesomeItems.awesomeItems.get(AwesomeItem.getId(lore0));
							if(!eventItem.getFlag(Flag.DROPONDEATH))
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
