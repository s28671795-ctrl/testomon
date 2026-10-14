/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONString
extends JSONValue {
    private final String value;

    JSONString(String string) {
        this.value = string;
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        jSONTarget.valueString(this.value);
    }

    public String getString() {
        return this.value;
    }
}

