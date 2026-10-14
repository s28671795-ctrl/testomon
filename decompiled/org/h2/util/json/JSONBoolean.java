/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONBoolean
extends JSONValue {
    public static final JSONBoolean FALSE = new JSONBoolean(false);
    public static final JSONBoolean TRUE = new JSONBoolean(true);
    private final boolean value;

    private JSONBoolean(boolean bl) {
        this.value = bl;
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        if (this.value) {
            jSONTarget.valueTrue();
        } else {
            jSONTarget.valueFalse();
        }
    }

    public boolean getBoolean() {
        return this.value;
    }
}

