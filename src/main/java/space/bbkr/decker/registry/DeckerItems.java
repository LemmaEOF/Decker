package space.bbkr.decker.registry;

import space.bbkr.decker.Decker;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeckerItems {
	public static Item COIN;
	public static Item KEY;

	public static void init() {
		COIN = register("coin", new Item(new Item.Settings().group(ItemGroup.MISC)));
		KEY = register("key", new Item(new Item.Settings().group(ItemGroup.MISC)));
	}

	public static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(Decker.MODID, name), item);
	}
}
