package space.bbkr.decker.game.gizmo;

import space.bbkr.decker.game.Dungeon;
import space.bbkr.decker.game.Run;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;

public class VanishingBlockGizmo extends Gizmo {
	private BlockState normalState = Blocks.SCAFFOLDING.getDefaultState();

	public void setNormalState(BlockState state) {
		this.normalState = state;
	}

	@Override
	public void updateClank(Run run, BlockPos pos) {
		if (run.getClanks() > 10) { //TODO: what's the scale of clank?
			run.getDungeon().getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void reset(Dungeon dungeon, BlockPos pos) {
		dungeon.getWorld().setBlockState(pos, normalState);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		BlockState.CODEC.encodeStart(NbtOps.INSTANCE, normalState).result().ifPresent(res -> tag.put("NormalState", res));
		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		BlockState.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("NormalState")).result().ifPresent(pair -> normalState = pair.getFirst());
	}
}
