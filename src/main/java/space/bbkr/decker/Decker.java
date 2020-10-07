package space.bbkr.decker;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.bbkr.decker.component.DeckerComponent;
import space.bbkr.decker.registry.DeckerCommands;
import space.bbkr.decker.registry.DeckerItems;

public class Decker implements ModInitializer {
	public static final String MODID = "decker";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		DeckerItems.init();
		DeckerCommands.register();
	}
}
