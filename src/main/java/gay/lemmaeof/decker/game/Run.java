package gay.lemmaeof.decker.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gay.lemmaeof.decker.game.gizmo.Gizmo;
import gay.lemmaeof.decker.game.gizmo.TickingGizmo;
import gay.lemmaeof.decker.registry.DeckerSounds;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.loader.api.FabricLoader;

public class Run {
	private final Dungeon dungeon;
	private final Set<ServerPlayerEntity> players = new HashSet<>();
	private final Int2ObjectMap<List<Runnable>> scheduledEvents = new Int2ObjectArrayMap<>();

	private int clanks = 0;
	private int time = 0;

	public Run(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	public void addPlayer(ServerPlayerEntity player) {
		players.add(player);
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public Set<ServerPlayerEntity> getPlayers() {
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
		players.forEach(player -> {
			SoundEvent evt = DeckerSounds.CLANK.getSoundEffectFor(player);
			if (evt == DeckerSounds.CLANK) player.world.playSound(null, player.getX(), player.getY(), player.getZ(), evt, SoundCategory.BLOCKS, 1f, 1f);
			else {
				player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.749154f);
				schedule(6, () -> player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.707107f));
				schedule(12, () -> player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.BLOCKS, 1f, 0.5F));
			}
		});
	}

	public void tick() {
		time++;
		List<Runnable> todos = scheduledEvents.getOrDefault(time, Collections.emptyList());
		for (Runnable todo : todos) {
			todo.run();
		}
		scheduledEvents.remove(time);
		for (BlockPos pos : dungeon.getGizmoPositions()) {
			Gizmo gizmo = dungeon.getGizmo(pos);
			if (gizmo instanceof TickingGizmo ticking && time % ticking.getTickRate() == 0) {
				ticking.tick(this, pos);
			}
		}
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
