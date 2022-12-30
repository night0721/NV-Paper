package me.night0721.nv.util;

import net.kyori.adventure.text.format.NamedTextColor;

public enum Rank {
    OWNER("<OWNER>", NamedTextColor.DARK_RED),
    ADMIN("<ADMIN>", NamedTextColor.RED),
    SPECIAL("<SPECIAL>", NamedTextColor.GOLD),
    ROOKIE("<ROOKIE>", NamedTextColor.DARK_GREEN);

    private final String display;
    private final NamedTextColor color;

    Rank(String display, NamedTextColor color) {
        this.display = display;
        this.color = color;
    }

    public String getDisplay() {
        return display;
    }

    public NamedTextColor getColor() {
        return color;
    }
}
