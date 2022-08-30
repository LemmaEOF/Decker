package gay.lemmaeof.decker.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import gay.lemmaeof.decker.component.DungeonComponent;
import gay.lemmaeof.decker.game.gizmo.Gizmo;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.util.NbtType;

public class Dungeon implements DungeonComponent {
	private final World world;
	private final List<Box> bounds = new ArrayList<>();
	private final Map<BlockPos, Gizmo> gizmos = new HashMap<>();
	private Run currentRun;

	public Dungeon(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void addBox(Box box) {
		for (Box bb : bounds) {
			if (box.intersects(bb)) {
				bounds.add(box);
				return;
			}
		}
	}

	@Override
	public List<Box> getBounds() {
		return bounds;
	}

	@Override
	public void removeBox(Box box) {
		bounds.remove(box);
	}

	@Override
	public void addGizmo(BlockPos pos, Gizmo gizmo) {
		for (Box box: bounds) {
			if (box.contains(pos.getX(), pos.getY(), pos.getZ())) {
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
	public void readFromNbt(NbtCompound tag) {
		bounds.clear();
		gizmos.clear();

		NbtList boxesTag = tag.getList("Boxes", NbtType.COMPOUND);
		for (NbtElement t : boxesTag) {
			NbtCompound boxTag = (NbtCompound) t;
			bounds.add(new Box(boxTag.getDouble("MinX"), boxTag.getDouble("MinY"), boxTag.getDouble("MinZ"),
					boxTag.getDouble("MaxX"), boxTag.getDouble("MaxY"), boxTag.getDouble("MaxZ")));
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
		for (Box box : bounds) {
			NbtCompound boxTag = new NbtCompound();
			boxTag.putDouble("MinX", box.minX);
			boxTag.putDouble("MaxX", box.maxX);
			boxTag.putDouble("MinY", box.minY);
			boxTag.putDouble("MaxY", box.maxY);
			boxTag.putDouble("MinZ", box.minZ);
			boxTag.putDouble("MaxZ", box.maxZ);
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
