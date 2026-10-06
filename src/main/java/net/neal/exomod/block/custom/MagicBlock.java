package net.neal.exomod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neal.exomod.item.ModItems;

public class MagicBlock extends Block {
    public MagicBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        level.addParticle(ParticleTypes.DRAGON_BREATH, pos.getX() + 0.5, pos.getX() + 1, pos.getZ() + 0.5,
                0, 1, 0);

        level.playSound(player, pos, SoundEvents.AXOLOTL_SPLASH, SoundSource.BLOCKS, 2f, 1f);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 300));
        }

        if(entity instanceof ItemEntity itemEntity) {
            if(isValidItem(itemEntity.getItem()))
                itemEntity.setItem(new ItemStack((Items.DIAMOND), itemEntity.getItem().getCount()));
        }

        super.stepOn(level, pos, state, entity);

    }

    private boolean isValidItem(ItemStack item) {
        return item.is(Items.IRON_INGOT) || item.is(ModItems.PINK_DIAMOND);
    }

}
