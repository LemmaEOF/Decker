package gay.lemmaeof.decker.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class CoinItem extends PolyItem {
	public CoinItem(Settings settings) {
		super(Items.GOLD_NUGGET, settings);
	}

	@Override
	public int getPolymerCustomModelData(ItemStack stack, @Nullable ServerPlayerEntity player) {
		return stack.getCount() == 64? 3 : stack.getCount() / 16;
	}
}
