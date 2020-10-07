package space.bbkr.decker.registry;

import space.bbkr.decker.game.Dungeon;
import space.bbkr.decker.game.Run;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class DeckerCommands {
	private static Dungeon dungeon;
	private static Run run;

	public static void register() {
		//TODO: actual commands and everything
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(
				CommandManager.literal("decker")
					.executes(context -> {
						if (dungeon == null) {
							dungeon = new Dungeon(context.getSource().getWorld());
						}
						if (run == null) {
							run = new Run(dungeon);
						}
						Entity e = context.getSource().getEntity();
						if (e instanceof PlayerEntity) {
							run.addPlayer((PlayerEntity) e);
						}
						run.schedule(40, () -> {
							context.getSource().sendFeedback(new LiteralText("Sent clank!"), true);
							run.addClank(1);
						});
						context.getSource().sendFeedback(new LiteralText("Scheduled clank!"), true);
						return 1;
					})
		));
		ServerTickEvents.START_SERVER_TICK.register(world -> {
			if (run != null) run.tick();
		});
	}
}
