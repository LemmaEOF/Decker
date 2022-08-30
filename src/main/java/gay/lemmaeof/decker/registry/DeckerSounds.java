package gay.lemmaeof.decker.registry;

import eu.pb4.polymer.api.other.PolymerSoundEvent;
import gay.lemmaeof.decker.Decker;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeckerSounds {
	public static final PolymerSoundEvent CLANK = register(new PolymerSoundEvent(new Identifier(Decker.MODID, "clank"), SoundEvents.EVENT_RAID_HORN));

	public static void init() {}

	private static PolymerSoundEvent register(PolymerSoundEvent event) {
		return Registry.register(Registry.SOUND_EVENT, event.getId(), event);
	}
}
