package gay.lemmaeof.decker.component;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import gay.lemmaeof.decker.game.Run;
import gay.lemmaeof.decker.game.gizmo.Gizmo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public interface DungeonComponent extends ComponentV3 {
	World getWorld();
	List<Box> getBounds();
	void addBox(Box box);
	void removeBox(Box box);
	void addGizmo(BlockPos pos, Gizmo gizmo);
	void removeGizmo(BlockPos pos);
	Set<BlockPos> getGizmoPositions();
	Gizmo getGizmo(BlockPos pos);
	@Nullable
	Run getCurrentRun();
}
