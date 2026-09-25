package net.neal.exomod.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, "exo_mod");

    public static final DeferredHolder<SoundEvent, SoundEvent> TRALA_AMBIENT = registerSound("trala_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> TUNG_AMBIENT = registerSound("tung_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CUSTOM_HURT_SOUND = registerSound("custom_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CUSTOM_DEATH_SOUND = registerSound("custom_death");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath("exo_mod", name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}