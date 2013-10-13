//Testing
package net.playssa.awesomeitems;

import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AwesomeItems extends JavaPlugin
{
	public static AwesomeItems plugin;
	public static Map<Integer, AwesomeItem> awesomeItems;
	public static Logger log;
	public void onEnable()
	{
		plugin = this;
		log = Bukkit.getLogger();
		getServer().getPluginManager().registerEvents(new AwesomeItemsListener(), this);
		AwesomeItemReadWrite.readAll();
		getCommand("awesomeitem").setExecutor(new AwesomeItemCommand());
		getCommand("newawesomeitem").setExecutor(new NewAwesomeItemCommand());
	}	
}