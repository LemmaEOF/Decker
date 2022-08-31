package gay.lemmaeof.decker.init;

import gay.lemmaeof.decker.Decker;

import gay.lemmaeof.decker.item.CoinItem;
import gay.lemmaeof.decker.item.PolyItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeckerItems {
	public static Item COIN;
	public static Item KEY;

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(Decker.MODID, "group"), () -> new ItemStack(COIN));

	public static void init() {
		COIN = register("coin", new CoinItem(new Item.Settings().group(GROUP)));
		KEY = register("key", new PolyItem(Items.TRIPWIRE_HOOK, new Item.Settings().group(GROUP)));
	}

	public static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(Decker.MODID, name), item);
	}
}
