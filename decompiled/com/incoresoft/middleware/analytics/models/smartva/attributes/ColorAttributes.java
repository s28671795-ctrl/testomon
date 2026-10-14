/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva.attributes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="UpColorAttributes", description="<div><p>Up attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - black<li>2 - white<li>3 - red / pink<li>4 - purple<li>5 - yellow<li>6 - gray<li>7 - blue<li>8 - green<li>9 - brown</ul></div>", example="3")
public enum ColorAttributes {
    unknown(0),
    black(1),
    white(2),
    red(3),
    purple(4),
    yellow(5),
    gray(6),
    blue(7),
    green(8),
    cyan(9),
    orange(10),
    brown(11),
    pink(12),
    silver(13);

    private final int id;

    private ColorAttributes(int id) {
        this.id = id;
    }

    public static ColorAttributes getById(int id) {
        return Arrays.stream(ColorAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }
}

