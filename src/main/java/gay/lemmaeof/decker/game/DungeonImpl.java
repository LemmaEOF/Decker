package gay.lemmaeof.decker.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import gay.lemmaeof.decker.api.Dungeon;
import gay.lemmaeof.decker.component.DungeonsComponent;
import gay.lemmaeof.decker.game.gizmo.Gizmo;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.util.NbtType;

public class DungeonImpl implements Dungeon {
	private final World world;
	private final List<BlockBox> bounds = new ArrayList<>();
	private final Map<BlockPos, Gizmo> gizmos = new HashMap<>();
	private Run currentRun;

	public DungeonImpl(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void addBox(BlockBox box) {
		for (BlockBox bb : bounds) {
			if (box.intersects(bb)) {
				bounds.add(box);
				return;
			}
		}
	}

	@Override
	public List<BlockBox> getBounds() {
		return bounds;
	}

	@Override
	public void removeBox(BlockBox box) {
		bounds.remove(box);
	}

	@Override
	public void addGizmo(BlockPos pos, Gizmo gizmo) {
		for (BlockBox box: bounds) {
			if (box.contains(pos)) {
				gizmos.put(pos, gizmo);
				return;
			}
		}
	}

	@Override
	public void removeGizmo(BlockPos pos) {
		gizmos.remove(pos);
	}

	@Override
	public Set<BlockPos> getGizmoPositions() {
		return gizmos.keySet();
	}

	@Override
	public Gizmo getGizmo(BlockPos pos) {
		return gizmos.get(pos);
	}

	@Nullable
	@Override
	public Run getCurrentRun() {
		return currentRun;
	}

	@Override
	public void startRun(Run run) {
		this.currentRun = run;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		bounds.clear();
		gizmos.clear();

		NbtList boxesTag = tag.getList("Boxes", NbtType.COMPOUND);
		for (NbtElement t : boxesTag) {
			NbtCompound boxTag = (NbtCompound) t;
			bounds.add(new BlockBox(boxTag.getInt("MinX"), boxTag.getInt("MinY"), boxTag.getInt("MinZ"),
					boxTag.getInt("MaxX"), boxTag.getInt("MaxY"), boxTag.getInt("MaxZ")));
		}

		NbtCompound gizmosTag = tag.getCompound("Gizmos");
		for (String key : gizmosTag.getKeys()) {
			BlockPos pos = BlockPos.fromLong(Long.parseLong(key));
			NbtCompound gizmoTag = gizmosTag.getCompound(key);
			Gizmo gizmo = Gizmo.getGizmo(new Identifier(gizmoTag.getString("Id")));
			gizmo.readNbt(gizmoTag.getCompound("Data"));
			gizmos.put(pos, gizmo);
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList boxesTag = new NbtList();
		for (BlockBox box : bounds) {
			NbtCompound boxTag = new NbtCompound();
			boxTag.putInt("MinX", box.getMinX());
			boxTag.putInt("MaxX", box.getMaxX());
			boxTag.putInt("MinY", box.getMinY());
			boxTag.putInt("MaxY", box.getMaxY());
			boxTag.putInt("MinZ", box.getMinZ());
			boxTag.putInt("MaxZ", box.getMaxZ());
			boxesTag.add(boxTag);
		}

		NbtCompound gizmosTag = new NbtCompound();
		for (BlockPos pos : gizmos.keySet()) {
			Gizmo gizmo = gizmos.get(pos);
			NbtCompound gizmoTag = new NbtCompound();
			gizmoTag.putString("Id", gizmo.getId().toString());
			NbtCompound dataTag = new NbtCompound();
			gizmo.writeNbt(dataTag);
			gizmoTag.put("Data", dataTag);
			gizmosTag.put(Long.toString(pos.asLong()), gizmoTag);
		}

		tag.put("Boxes", boxesTag);
		tag.put("Gizmos", gizmosTag);
	}
}
