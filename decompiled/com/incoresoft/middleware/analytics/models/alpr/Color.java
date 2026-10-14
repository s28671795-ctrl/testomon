/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Color", description="<div><p>Colors enum.</p><p>Valid values are:</p><ul><li>1 - black<li>2 - blue<li>3 - yellow<li>4 - unknown<li>5 - white<li>6 - brown<li>7 - red<li>8 - green<li>9 - orange<li>10 - beige<li>11 - violet<li>12 - grey<li>13 - cyan<li>14 - pink<li>15 - silver<li>16 - golden<li>17 - bordeaux</ul></div>", example="black")
public enum Color {
    black(1),
    blue(2),
    yellow(3),
    unknown(4),
    white(5),
    brown(6),
    red(7),
    green(8),
    orange(9),
    beige(10),
    violet(11),
    grey(12),
    cyan(13),
    pink(14),
    silver(15),
    golden(16),
    bordeaux(17);

    private final int id;

    private Color(int id) {
        this.id = id;
    }

    public static Color getById(int id) {
        for (Color e : Color.values()) {
            if (e.id != id) continue;
            return e;
        }
        return unknown;
    }

    public int getId() {
        return this.id;
    }
}

