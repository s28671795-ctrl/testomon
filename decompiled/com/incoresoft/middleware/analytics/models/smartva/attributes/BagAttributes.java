/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva.attributes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="BagAttributes", description="<div><p>Bag attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - backpack<li>2 - bag<li>3 - handbag</ul></div>", example="3")
public enum BagAttributes {
    unknown(0),
    backpack(1),
    bag(2),
    handbag(3);

    private final int id;

    private BagAttributes(int id) {
        this.id = id;
    }

    public static BagAttributes getById(int id) {
        return Arrays.stream(BagAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }
}

