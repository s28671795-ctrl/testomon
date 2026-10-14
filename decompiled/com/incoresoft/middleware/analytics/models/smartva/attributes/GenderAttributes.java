/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva.attributes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="GenderAttributes", description="<div><p>Gender attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - male<li>2 - female</ul></div>", example="2")
public enum GenderAttributes {
    unknown(0),
    male(1),
    female(2);

    private final int id;

    private GenderAttributes(int id) {
        this.id = id;
    }

    public static GenderAttributes getById(int id) {
        return Arrays.stream(GenderAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }
}

