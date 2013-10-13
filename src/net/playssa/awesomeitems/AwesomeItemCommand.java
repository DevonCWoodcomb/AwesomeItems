package net.playssa.awesomeitems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AwesomeItemCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			if(sender.hasPermission("awesomeitems.give"))
			{
				if(args.length > 0)
				{
					if(!AwesomeItems.awesomeItems.containsKey(Integer.parseInt(args[0])))
					{
						sender.sendMessage(ChatColor.RED + "No item with that Id");
						return true;
					}
					AwesomeItem commandItem;
					try
					{
						commandItem = AwesomeItems.awesomeItems.get(Integer.parseInt(args[0]));
					}
					catch(Exception e)
					{
						sender.sendMessage(ChatColor.RED + "Invalid ItemId!");
						return true;
					}
					if(args.length > 1)
					{
						Player target = matchPlayer(args[1]);
						if(target != null)
						{
							target.getInventory().addItem(commandItem.getItemStack());
							return true;
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "Could not find player!");
							return true;
						}
					}
					else
					{
						((Player)sender).getInventory().addItem(commandItem.getItemStack());
						return true;
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "You must specify an item!");
					return true;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
		}
		else if(sender instanceof ConsoleCommandSender)
		{
			if(args.length > 1)
			{
				if(!AwesomeItems.awesomeItems.containsKey(args[0]))
				{
					sender.sendMessage(ChatColor.RED + "No item with that Id");
					return true;
				}
				AwesomeItem commandItem;
				try
				{
					commandItem = AwesomeItems.awesomeItems.get(Integer.parseInt(args[0]));
				}
				catch(Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Invalid ItemId!");
					return true;
				}
				Player target = matchPlayer(args[1]);
				if(target != null)
				{
					target.getInventory().addItem(commandItem.getItemStack());
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Could not find player!");
					return true;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You must specify an item and a player!");
				return true;
			}
		}
		return false;
	}

	private Player matchPlayer(String target)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getName().equalsIgnoreCase(target))
			{
				return p;
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getName().toLowerCase().contains(target.toLowerCase()))
			{
				return p;
			}
		}
		return null;			
	}
	
}
