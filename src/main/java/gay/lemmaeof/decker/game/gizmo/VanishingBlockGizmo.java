package gay.lemmaeof.decker.game.gizmo;

import gay.lemmaeof.decker.game.Dungeon;
import gay.lemmaeof.decker.game.Run;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;

public class VanishingBlockGizmo extends Gizmo {
	private BlockState normalState = Blocks.SCAFFOLDING.getDefaultState();

	public void setNormalState(BlockState state) {
		this.normalState = state;
	}

	@Override
	public void updateClank(Run run, BlockPos pos) {
		if (run.getClanks() > 10) { //TODO: configurable
			run.getDungeon().getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void reset(Dungeon dungeon, BlockPos pos) {
		dungeon.getWorld().setBlockState(pos, normalState);
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		BlockState.CODEC.encodeStart(NbtOps.INSTANCE, normalState).result().ifPresent(res -> tag.put("NormalState", res));
	}

	@Override
	public void readNbt(NbtCompound tag) {
		BlockState.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("NormalState")).result().ifPresent(pair -> normalState = pair.getFirst());
	}
}
