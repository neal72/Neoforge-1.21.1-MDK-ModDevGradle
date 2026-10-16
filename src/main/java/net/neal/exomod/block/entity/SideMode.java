package net.neal.exomod.block.entity;

import net.minecraft.util.StringRepresentable;

public enum SideMode implements StringRepresentable {
    NONE("none"), INPUT("input"), OUTPUT("output");

    private final String name;

    SideMode(String name) {
        this.name = name;
    }

    public SideMode next() {
        return values()[(ordinal() + 1) % values().length];
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}