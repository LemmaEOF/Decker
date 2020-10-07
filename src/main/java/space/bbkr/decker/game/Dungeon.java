package space.bbkr.decker.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import space.bbkr.decker.game.gizmo.Gizmo;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.util.NbtType;

public class Dungeon {
	private final World world;
	private final List<Box> bounds = new ArrayList<>();
	private final Map<BlockPos, Gizmo> gizmos = new HashMap<>();

	public Dungeon(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public void addBox(Box box) {
		for (Box bb : bounds) {
			if (box.intersects(bb)) {
				bounds.add(box);
				return;
			}
		}
	}

	public List<Box> getBounds() {
		return bounds;
	}

	public void removeBox(Box box) {
		bounds.remove(box);
	}

	public void addGizmo(BlockPos pos, Gizmo gizmo) {
		for (Box box: bounds) {
			if (box.contains(pos.getX(), pos.getY(), pos.getZ())) {
				gizmos.put(pos, gizmo);
				return;
			}
		}
	}

	public void removeGizmo(BlockPos pos) {
		gizmos.remove(pos);
	}

	public Set<BlockPos> getGizmoPositions() {
		return gizmos.keySet();
	}

	public Gizmo getGizmo(BlockPos pos) {
		return gizmos.get(pos);
	}

	public CompoundTag toTag(CompoundTag tag) {
		ListTag boxesTag = new ListTag();
		for (Box box : bounds) {
			CompoundTag boxTag = new CompoundTag();
			boxTag.putDouble("MinX", box.minX);
			boxTag.putDouble("MaxX", box.maxX);
			boxTag.putDouble("MinY", box.minY);
			boxTag.putDouble("MaxY", box.maxY);
			boxTag.putDouble("MinZ", box.minZ);
			boxTag.putDouble("MaxZ", box.maxZ);
			boxesTag.add(boxTag);
		}

		CompoundTag gizmosTag = new CompoundTag();
		for (BlockPos pos : gizmos.keySet()) {
			Gizmo gizmo = gizmos.get(pos);
			CompoundTag gizmoTag = new CompoundTag();
			gizmoTag.putString("Id", gizmo.getId().toString());
			gizmoTag.put("Data", gizmo.toTag(new CompoundTag()));
			gizmosTag.put(Long.toString(pos.asLong()), gizmoTag);
		}

		tag.put("Boxes", boxesTag);
		tag.put("Gizmos", gizmosTag);
		return tag;
	}

	public void fromTag(CompoundTag tag) {
		bounds.clear();
		gizmos.clear();

		ListTag boxesTag = tag.getList("Boxes", NbtType.COMPOUND);
		for (Tag t : boxesTag) {
			CompoundTag boxTag = (CompoundTag) t;
			bounds.add(new Box(boxTag.getDouble("MinX"), boxTag.getDouble("MinY"), boxTag.getDouble("MinZ"),
					boxTag.getDouble("MaxX"), boxTag.getDouble("MaxY"), boxTag.getDouble("MaxZ")));
		}

		CompoundTag gizmosTag = tag.getCompound("Gizmos");
		for (String key : gizmosTag.getKeys()) {
			BlockPos pos = BlockPos.fromLong(Long.parseLong(key));
			CompoundTag gizmoTag = gizmosTag.getCompound(key);
			Gizmo gizmo = Gizmo.getGizmo(new Identifier(gizmoTag.getString("Id")));
			gizmo.fromTag(gizmoTag.getCompound("Data"));
			gizmos.put(pos, gizmo);
		}
	}
}
