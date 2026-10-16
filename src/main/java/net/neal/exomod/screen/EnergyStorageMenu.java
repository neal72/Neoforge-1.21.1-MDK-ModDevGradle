package net.neal.exomod.screen;

import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neal.exomod.block.ModBlocks;
import net.neal.exomod.block.entity.EnergyStorageBlockEntity;
import net.neal.exomod.block.entity.SideMode;

public class EnergyStorageMenu extends AbstractContainerMenu {
    public final EnergyStorageBlockEntity blockEntity;
    private final ContainerData data;
    private final Level level;

    // Client side (opened through the network)
    public EnergyStorageMenu(int id, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(8));
    }

    // Server side
    public EnergyStorageMenu(int id, Inventory inv, BlockEntity be, ContainerData data) {
        super(ModMenuTypes.ENERGY_STORAGE_MENU.get(), id);
        this.blockEntity = (EnergyStorageBlockEntity) be;
        this.level = inv.player.level();
        this.data = data;
        addDataSlots(data);
    }

    public SideMode getSideMode(Direction dir) {
        return SideMode.values()[data.get(dir.get3DDataValue())];
    }

    public int getEnergy() {
        return (data.get(6) & 0xFFFF) | ((data.get(7) & 0xFFFF) << 16);
    }

    public int getMaxEnergy() {
        return EnergyStorageBlockEntity.CAPACITY;
    }

    // Runs on the server when a button in the screen is clicked
    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < 6) {
            blockEntity.cycleSide(Direction.from3DDataValue(id));
            return true;
        }
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ENERGY_STORAGE.get());
    }
}