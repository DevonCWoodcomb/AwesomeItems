//Testing
package net.somethingsuperawesome.awesomeitems;

import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AwesomeItems extends JavaPlugin
{
	public static AwesomeItems plugin;
	public static Set<AwesomeItem> awesomeItems;
	public static Logger log;
	public void onEnable()
	{
		plugin = this;
		log = Bukkit.getLogger();
		getServer().getPluginManager().registerEvents(new AwesomeItemsListener(), this);
		AwesomeItemReadWrite.readAll();
		getCommand("awesomeitem").setExecutor(new AwesomeItemsCommand());
	}	
}