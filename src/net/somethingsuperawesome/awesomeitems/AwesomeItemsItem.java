package net.somethingsuperawesome.awesomeitems;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

public class AwesomeItemsItem
{
	private String name;//Used internally or for commands. Ex: /AI <name> could spawn the item.
	private String displayName;//What shows up in the game. Ex: "Pimp Ass-Sword"
	private Rarity rarity;//The item's rarity from enum. Ex: COMMON
	private String[] lore;//Item's lore, this shows up in-game under the name of the sword.
	private Material type;//The material type of the item. Ex: DIAMOND_SWORD
	private List<PotionEffect> constantPotEffects;//List of potion effects that apply constantly while hold/wearing the item
	private List<PotionEffect> onHitPotEffects;//List of potion effects to apply to (self or target?) when you hit.
}
