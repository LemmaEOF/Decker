package gay.lemmaeof.decker.item;

import eu.pb4.polymer.api.item.PolymerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class PolyItem extends Item implements PolymerItem {
	private final Item substrate;

	public PolyItem(Item substrate, Settings settings) {
		super(settings);
		this.substrate = substrate;
	}

	@Override
	public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
		return substrate;
	}
}
