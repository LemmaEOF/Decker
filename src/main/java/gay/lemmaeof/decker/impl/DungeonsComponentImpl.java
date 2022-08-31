package gay.lemmaeof.decker.impl;

import gay.lemmaeof.decker.api.Dungeon;
import gay.lemmaeof.decker.component.DungeonsComponent;
import gay.lemmaeof.decker.game.DungeonImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DungeonsComponentImpl implements DungeonsComponent {
	private final World world;
	private final Map<String, Dungeon> dungeons = new HashMap<>();

	public DungeonsComponentImpl(World world) {
		this.world = world;
	}

	@Override
	public Collection<String> getDungeonNames() {
		return dungeons.keySet();
	}

	@Override
	public Dungeon getDungeon(String name) {
		return dungeons.get(name);
	}

	@Override
	public Dungeon createDungeon(String name) {
		Dungeon dungeon = new DungeonImpl(world);
		dungeons.put(name, dungeon);
		return dungeon;
	}

	@Override
	public void removeDungeon(String name) {
		dungeons.remove(name);
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtCompound dungeonsTag = new NbtCompound();
		for (String name : dungeons.keySet()) {
			Dungeon dungeon = dungeons.get(name);
			NbtCompound dungeonTag = new NbtCompound();
			dungeon.writeToNbt(dungeonTag);
			dungeonsTag.put(name, dungeonTag);
		}
		tag.put("Dungeons", dungeonsTag);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		dungeons.clear();
		NbtCompound dungeonsTag = tag.getCompound("Dungeons");
		for (String name : dungeonsTag.getKeys()) {
			NbtCompound dungeonTag = dungeonsTag.getCompound(name);
			Dungeon dungeon = new DungeonImpl(world);
			dungeon.readFromNbt(dungeonTag);
			dungeons.put(name, dungeon);
		}
	}
}
