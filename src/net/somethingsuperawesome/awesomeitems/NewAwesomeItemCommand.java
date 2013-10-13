package net.somethingsuperawesome.awesomeitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class NewAwesomeItemCommand implements CommandExecutor
{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender.hasPermission("awesomeitems.create"))
		{
			if(args.length > 0)
			{
				Material material;
				try
				{				
					material = Material.getMaterial(Integer.parseInt(args[0]));
				}
				catch(Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Invalid Material Id.");
					return true;
				}
				
				AwesomeItem commandItem = new AwesomeItem(material);
				AwesomeItems.awesomeItems.put(commandItem.getItemId(), commandItem);
				AwesomeItemReadWrite.writeItem(commandItem);
				return true;
			}
		}
		return false;
	}
}
