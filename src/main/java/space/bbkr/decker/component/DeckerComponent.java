package space.bbkr.decker.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import space.bbkr.decker.game.Dungeon;
import space.bbkr.decker.game.Run;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

public class DeckerComponent implements ComponentV3 {
	private final List<Dungeon> dungeons = new ArrayList<>();
	private final Map<Dungeon, List<Run>> runs = new HashMap<>();
	private final World world;

	public DeckerComponent(World world) {
		this.world = world;
	}

	public IntSet getDungeonIds() {
		IntSet ints = new IntArraySet();
		for (int i = 0; i < dungeons.size(); i++) {
			ints.add(i);
		}
		return ints;
	}

	public Dungeon getDungeon(int id) {
		return dungeons.get(id);
	}

	public void addDungeon(Dungeon dungeon) {
		dungeons.add(dungeon);
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		dungeons.clear();
		CompoundTag dungeonsTag = tag.getCompound("Dungeons");
		for (String key : dungeonsTag.getKeys()) {
			int id = Integer.parseInt(key);
			Dungeon dungeon = new Dungeon(world);
			dungeon.fromTag(dungeonsTag.getCompound(key));
			dungeons.add(id, dungeon);
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		CompoundTag dungeonsTag = new CompoundTag();
		for (int i = 0; i < dungeons.size(); i++) {
			Dungeon dungeon = dungeons.get(i);
			dungeonsTag.put(Integer.toString(i), dungeon.toTag(new CompoundTag()));
		}
		tag.put("Dungeons", dungeonsTag);
	}
}
