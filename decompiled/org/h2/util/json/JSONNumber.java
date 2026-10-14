/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONNumber
extends JSONValue {
    private final BigDecimal value;

    JSONNumber(BigDecimal bigDecimal) {
        this.value = bigDecimal;
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        jSONTarget.valueNumber(this.value);
    }

    public BigDecimal getBigDecimal() {
        return this.value;
    }
}

