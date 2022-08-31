package gay.lemmaeof.decker.init;

import com.mojang.brigadier.arguments.StringArgumentType;
import gay.lemmaeof.decker.component.DungeonsComponent;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class DeckerCommands {

	public static void register() {
		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			var baseNode = CommandManager.literal("decker").build();
			var enterNode = CommandManager.literal("enter").build();
			var choiceNode = CommandManager.argument("dungeon", StringArgumentType.string())
					.suggests(((context, builder) -> {
						DungeonsComponent comp = DeckerComponents.DUNGEONS.get(context.getSource().getWorld());
						for (String name : comp.getDungeonNames()) {
							builder.suggest(name);
						}
						return builder.buildFuture();
					})).build(); //TODO: executes

			dispatcher.getRoot().addChild(baseNode);
			baseNode.addChild(enterNode);
			enterNode.addChild(choiceNode);
		}));
	}
}
