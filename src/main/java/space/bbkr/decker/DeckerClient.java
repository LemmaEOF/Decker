package space.bbkr.decker;

import space.bbkr.decker.registry.DeckerItems;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

public class DeckerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(DeckerItems.COIN, new Identifier(Decker.MODID, "coin_size"),
				(stack, world, entity) -> stack.getCount() == 64? 3 : stack.getCount() / 16
		);
	}
}
