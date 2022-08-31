package gay.lemmaeof.decker;

import gay.lemmaeof.decker.api.Dungeon;
import gay.lemmaeof.decker.component.DungeonsComponent;
import gay.lemmaeof.decker.game.Run;
import gay.lemmaeof.decker.init.DeckerComponents;
import gay.lemmaeof.decker.init.DeckerSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gay.lemmaeof.decker.init.DeckerCommands;
import gay.lemmaeof.decker.init.DeckerItems;

public class Decker implements ModInitializer {
	public static final String MODID = "decker";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		DeckerItems.init();
		DeckerCommands.register();
		DeckerSounds.init();
		ServerTickEvents.START_WORLD_TICK.register(world -> {
			DungeonsComponent dungeons = DeckerComponents.DUNGEONS.get(world);
			for (String name : dungeons.getDungeonNames()) {
				Dungeon dungeon = dungeons.getDungeon(name);
				Run run = dungeon.getCurrentRun();
				if (run != null) run.tick();
			}
		});
	}
}
