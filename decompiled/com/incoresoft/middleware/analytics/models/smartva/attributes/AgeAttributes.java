/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva.attributes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="AgeAttributes", description="<div><p>Age attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - young<li>2 - teenager<li>3 - adult<li>4 - old</ul></div>", example="3")
public enum AgeAttributes {
    unknown(0),
    young(1),
    teenager(2),
    adult(3),
    old(4);

    private final int id;

    private AgeAttributes(int id) {
        this.id = id;
    }

    public static AgeAttributes getById(int id) {
        return Arrays.stream(AgeAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }
}

