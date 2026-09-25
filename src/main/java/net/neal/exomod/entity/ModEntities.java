package net.neal.exomod.entity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neal.exomod.ExoMod;
import net.neal.exomod.entity.custom.TralaEntity;
import net.neal.exomod.entity.custom.TungEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ExoMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<TralaEntity>> TRALA =
            ENTITY_TYPES.register("trala", () -> EntityType.Builder.of(TralaEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("trala")); //SIZE OF HITBOX

    public static final DeferredHolder<EntityType<?>, EntityType<TungEntity>> TUNG =
            ENTITY_TYPES.register("tung", () -> EntityType.Builder.of(TungEntity::new, MobCategory.MONSTER)
                    .sized(0.3F, 1.0F).build("tung")); //SIZE OF HITBOX

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}