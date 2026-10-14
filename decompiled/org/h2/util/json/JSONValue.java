/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONStringTarget;
import org.h2.util.json.JSONTarget;

public abstract class JSONValue {
    JSONValue() {
    }

    public abstract void addTo(JSONTarget<?> var1);

    public final String toString() {
        JSONStringTarget jSONStringTarget = new JSONStringTarget();
        this.addTo(jSONStringTarget);
        return jSONStringTarget.getResult();
    }
}

