package gay.lemmaeof.decker.api;

import gay.lemmaeof.decker.game.Run;
import gay.lemmaeof.decker.game.gizmo.Gizmo;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface Dungeon {
	World getWorld();
	List<BlockBox> getBounds();
	void addBox(BlockBox box);
	void removeBox(BlockBox box);
	void addGizmo(BlockPos pos, Gizmo gizmo);
	void removeGizmo(BlockPos pos);
	Set<BlockPos> getGizmoPositions();
	Gizmo getGizmo(BlockPos pos);
	@Nullable
	Run getCurrentRun();
	void startRun(Run run);
	void readFromNbt(NbtCompound tag);
	void writeToNbt(NbtCompound tag);
}
