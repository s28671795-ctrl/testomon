/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONNull
extends JSONValue {
    public static final JSONNull NULL = new JSONNull();

    private JSONNull() {
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        jSONTarget.valueNull();
    }
}

