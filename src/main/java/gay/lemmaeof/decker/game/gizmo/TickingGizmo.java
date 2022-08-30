package gay.lemmaeof.decker.game.gizmo;

import gay.lemmaeof.decker.game.Run;

import net.minecraft.util.math.BlockPos;

public abstract class TickingGizmo extends Gizmo {

	public int getTickRate() {
		return 20;
	}

	public abstract void tick(Run run, BlockPos pos);
}
