package net.somethingsuperawesome.awesomeitems;

import lombok.Getter;

import org.bukkit.ChatColor;

public enum Rarity
{
	//List of Enum options, add them here once we figure them all out.
	COMMON(1,ChatColor.GRAY.toString()),
	UNCOMMON(2,ChatColor.WHITE.toString()),
	RARE(3,ChatColor.RED.toString());
	
	@Getter
	private final int rarityLevel;
	@Getter
	private final String rarityColor;
	private Rarity(int rlevel, String rcolor)
	{
		rarityLevel = rlevel;
		rarityColor = rcolor;
	}
}