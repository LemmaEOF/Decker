package space.bbkr.decker.compat;

import java.util.HashMap;

import io.github.theepicblock.polymc.api.PolyMcEntrypoint;
import io.github.theepicblock.polymc.api.item.ItemPoly;
import io.github.theepicblock.polymc.api.register.PolyRegistry;
import io.github.theepicblock.polymc.resource.JsonModel;
import io.github.theepicblock.polymc.resource.ResourcePackMaker;
import space.bbkr.decker.Decker;
import space.bbkr.decker.registry.DeckerItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class PolyMCLoader implements PolyMcEntrypoint {
	@Override
	public void registerPolys(PolyRegistry polyRegistry) {
		polyRegistry.registerItemPoly(DeckerItems.COIN, new ItemPoly() {
			@Override
			public ItemStack getClientItem(ItemStack stack) {
				ItemStack retStack = new ItemStack(Items.EMERALD, stack.getCount());
				retStack.getOrCreateTag().putInt("CustomModelData", retStack.getCount() == 64? 3 : retStack.getCount() / 16);
				return retStack;
			}

			@Override
			public void AddToResourcePack(Item item, ResourcePackMaker packMaker) {
				JsonModel model = new JsonModel();
				model.parent = "item/generated";
				for (int i = 0; i < 4; i++) {
					Identifier modelName = new Identifier(Decker.MODID, "coin_" + i);
					JsonModel.Override override = new JsonModel.Override();
					override.predicate = new HashMap<>();
					override.predicate.put("custom_model_data", (double) i);
					override.model = modelName.toString();
					model.addOverride(override);

					//TODO: necessary? test soon...
//					JsonModel child = new JsonModel();
//					child.parent = "item/generated";
//					child.textures = new HashMap<>();
//					child.textures.put("layer0", modelName.toString());
//					packMaker.putPendingItemModel(modelName, child);
				}
				packMaker.putPendingItemModel(new Identifier("emerald"), model);
			}
		});
	}
}
