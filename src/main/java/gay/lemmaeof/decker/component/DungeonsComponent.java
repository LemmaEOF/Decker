package gay.lemmaeof.decker.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import gay.lemmaeof.decker.api.Dungeon;
import gay.lemmaeof.decker.impl.DungeonsComponentImpl;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DungeonsComponent extends ComponentV3 {
	Collection<String> getDungeonNames();
	Dungeon getDungeon(String name);
	Dungeon createDungeon(String name);
	void removeDungeon(String name);
}
