package gay.lemmaeof.decker;

import gay.lemmaeof.decker.registry.DeckerSounds;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gay.lemmaeof.decker.registry.DeckerCommands;
import gay.lemmaeof.decker.registry.DeckerItems;

public class Decker implements ModInitializer {
	public static final String MODID = "decker";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		DeckerItems.init();
		DeckerCommands.register();
		DeckerSounds.init();
	}
}
