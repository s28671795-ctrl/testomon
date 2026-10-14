/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr;

public enum LprRegion {
    GLOBAL(60, 15),
    EUROPE(60, 15),
    USA(60, 15),
    AUSTRALIA(60, 15),
    JAPAN(60, 15),
    THAILAND(60, 15),
    ARABIC(60, 15),
    LATAM(60, 15);

    private final int minPlateWidth;
    private final int minPlateHeight;

    private LprRegion(int minPlateWidth, int minPlateHeight) {
        this.minPlateWidth = minPlateWidth;
        this.minPlateHeight = minPlateHeight;
    }

    public int getMinPlateWidth() {
        return this.minPlateWidth;
    }

    public int getMinPlateHeight() {
        return this.minPlateHeight;
    }
}

