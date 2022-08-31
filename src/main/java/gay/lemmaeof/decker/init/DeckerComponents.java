package gay.lemmaeof.decker.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import gay.lemmaeof.decker.Decker;
import gay.lemmaeof.decker.component.DungeonsComponent;
import gay.lemmaeof.decker.impl.DungeonsComponentImpl;
import net.minecraft.util.Identifier;

public class DeckerComponents implements WorldComponentInitializer {
	public static final ComponentKey<DungeonsComponent> DUNGEONS = ComponentRegistry.getOrCreate(new Identifier(Decker.MODID, "dungeons"), DungeonsComponent.class);


	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(DUNGEONS, DungeonsComponentImpl::new);
	}
}
