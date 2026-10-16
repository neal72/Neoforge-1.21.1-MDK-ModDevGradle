package net.neal.exomod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neal.exomod.block.entity.SideMode;
import net.neal.exomod.screen.EnergyStorageMenu;

import java.util.function.Supplier;

public class EnergyStorageScreen extends AbstractContainerScreen<EnergyStorageMenu> {
    private final SideButton[] sideButtons = new SideButton[6];

    public EnergyStorageScreen(EnergyStorageMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 120;
    }

    @Override
    protected void init() {
        super.init();
        int cx = leftPos + imageWidth / 2 - 8;  // centre column
        int cy = topPos + 46;

        //        [U]
        //   [W]  [N]  [E]
        //        [D]  [S]
        addSide(Direction.UP,    cx,      cy);
        addSide(Direction.WEST,  cx - 18, cy + 18);
        addSide(Direction.NORTH, cx,      cy + 18);
        addSide(Direction.EAST,  cx + 18, cy + 18);
        addSide(Direction.DOWN,  cx,      cy + 36);
        addSide(Direction.SOUTH, cx + 18, cy + 36);
    }

    private void addSide(Direction dir, int x, int y) {
        int id = dir.get3DDataValue();
        String letter = dir.getName().substring(0, 1).toUpperCase();
        sideButtons[id] = addRenderableWidget(new SideButton(x, y, letter,
                () -> menu.getSideMode(dir),
                b -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, id)));
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        for (Direction dir : Direction.values()) {
            String name = dir.getName().substring(0, 1).toUpperCase() + dir.getName().substring(1);
            sideButtons[dir.get3DDataValue()].setTooltip(Tooltip.create(
                    Component.literal(name + ": " + menu.getSideMode(dir).name())));
        }
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        g.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF2B2B2B);
        int x = leftPos + 8, y = topPos + 18, w = imageWidth - 16, h = 10;
        g.fill(x, y, x + w, y + h, 0xFF000000);
        int filled = (int) (w * (menu.getEnergy() / (float) menu.getMaxEnergy()));
        g.fill(x, y, x + filled, y + h, 0xFFD02020);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, title, 8, 6, 0xFFFFFF, false);
        g.drawString(font, menu.getEnergy() + " / " + menu.getMaxEnergy() + " RF", 8, 32, 0xAAAAAA, false);
        // legend
        g.drawString(font, "Out", 8, 80, 0x3B7DD8, false);
        g.drawString(font, "In", 8, 70, 0xE08A1E, false);
        g.drawString(font, "Off", 8, 90, 0x888888, false);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.render(g, mouseX, mouseY, partialTick);
        renderTooltip(g, mouseX, mouseY);
    }

    // A small coloured square: blue = input, orange = output, grey = off
    private static class SideButton extends Button {
        private final Supplier<SideMode> mode;

        SideButton(int x, int y, String letter, Supplier<SideMode> mode, OnPress onPress) {
            super(x, y, 16, 16, Component.literal(letter), onPress, DEFAULT_NARRATION);
            this.mode = mode;
        }

        @Override
        protected void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
            int color = switch (mode.get()) {
                case INPUT -> 0xFFE08A1E;   // orange
                case OUTPUT -> 0xFF3B7DD8;  // blue
                case NONE -> 0xFF555555;
            };

            int border = isHovered() ? 0xFFFFFFFF : 0xFF000000;
            g.fill(getX(), getY(), getX() + width, getY() + height, border);
            g.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, color);
            g.drawCenteredString(Minecraft.getInstance().font, getMessage(),
                    getX() + width / 2, getY() + 4, 0xFFFFFF);
        }
    }
}