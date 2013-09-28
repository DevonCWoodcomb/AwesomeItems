package net.somethingsuperawesome.awesomeitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AwesomeItemsCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length > 0 && sender instanceof Player)
		{
			for(AwesomeItem item : AwesomeItems.awesomeItems)
			{
				if(item.getItemName().equalsIgnoreCase(args[0]))
				{
					((Player) sender).getInventory().addItem(item.getItemStack());
					return true;
				}
			}
		}
		return false;
	}
	
}
