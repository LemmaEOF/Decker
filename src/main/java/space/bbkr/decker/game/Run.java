package space.bbkr.decker.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.loader.api.FabricLoader;

public class Run {
	private final Dungeon dungeon;
	private final Set<PlayerEntity> players = new HashSet<>();
	private final Int2ObjectMap<List<Runnable>> scheduledEvents = new Int2ObjectArrayMap<>();

	private int clanks = 0;
	private int time = 0;

	public Run(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	public void addPlayer(PlayerEntity player) {
		players.add(player);
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public Set<PlayerEntity> getPlayers() {
		return players;
	}

	public int getClanks() {
		return clanks;
	}

	public void addClank(int clank) {
		this.clanks += clank;
		for (BlockPos pos : dungeon.getGizmoPositions()) {
			dungeon.getGizmo(pos).updateClank(this, pos);
		}
		if (FabricLoader.getInstance().isModLoaded("polymc")) {
			players.forEach(player -> player.world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.749154f));
			schedule(6, () -> players.forEach(player -> player.world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.707107f)));
			schedule(12, () -> players.forEach(player -> player.world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.5F)));
			System.out.println(scheduledEvents.toString());
		} else {
			//TODO: play our own sound event here
		}
	}

	public void tick() {
		time++;
		List<Runnable> todos = scheduledEvents.getOrDefault(time, Collections.emptyList());
		for (Runnable todo : todos) {
			todo.run();
		}
		scheduledEvents.remove(time);
	}

	public void schedule(int delay, Runnable todo) {
		scheduledEvents.computeIfAbsent(time + delay, ArrayList::new).add(todo);
	}

	public void completeRun() {
		for (BlockPos pos : dungeon.getGizmoPositions()) {
			dungeon.getGizmo(pos).reset(dungeon, pos);
		}
	}
}
