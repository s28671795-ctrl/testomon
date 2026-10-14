/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONArray
extends JSONValue {
    private final ArrayList<JSONValue> elements = new ArrayList();

    JSONArray() {
    }

    void addElement(JSONValue jSONValue) {
        this.elements.add(jSONValue);
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        jSONTarget.startArray();
        for (JSONValue jSONValue : this.elements) {
            jSONValue.addTo(jSONTarget);
        }
        jSONTarget.endArray();
    }

    public int length() {
        return this.elements.size();
    }

    public JSONValue[] getArray() {
        return this.elements.toArray(new JSONValue[0]);
    }

    public <E> E[] getArray(Class<E> clazz, Function<JSONValue, E> function) {
        int n = this.elements.size();
        Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        for (int i = 0; i < n; ++i) {
            objectArray[i] = function.apply(this.elements.get(i));
        }
        return objectArray;
    }

    public JSONValue getElement(int n) {
        if (n >= 0 && n < this.elements.size()) {
            return this.elements.get(n);
        }
        return null;
    }
}

