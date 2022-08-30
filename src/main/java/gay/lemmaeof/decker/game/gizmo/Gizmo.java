package gay.lemmaeof.decker.game.gizmo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import gay.lemmaeof.decker.game.Dungeon;
import gay.lemmaeof.decker.game.Run;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public abstract class Gizmo {
	private static final Map<Identifier, Supplier<Gizmo>> gizmoFactories = new HashMap<>();

	public static Gizmo getGizmo(Identifier id) {
		Gizmo ret = gizmoFactories.get(id).get();
		ret.id = id;
		return ret;
	}

	public static void addGizmoFactory(Identifier id, Supplier<Gizmo> gizmoFactory) {
		gizmoFactories.put(id, gizmoFactory);
	}

	private Identifier id;

	public Identifier getId() {
		return id;
	}

	public abstract void updateClank(Run run, BlockPos pos);

	public abstract void reset(Dungeon dungeon, BlockPos pos);

	public abstract void writeNbt(NbtCompound tag);

	public abstract void readNbt(NbtCompound tag);
}
