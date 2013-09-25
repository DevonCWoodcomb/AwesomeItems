//Testing
package net.somethingsuperawesome.awesomeitems;

import org.bukkit.plugin.java.JavaPlugin;

public class AwesomeItems extends JavaPlugin
{
	public static AwesomeItems plugin;
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new AwesomeItemsListener(), this);
		plugin = this;
	}	
}