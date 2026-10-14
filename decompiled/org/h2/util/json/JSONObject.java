/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONObject
extends JSONValue {
    private final ArrayList<AbstractMap.SimpleImmutableEntry<String, JSONValue>> members = new ArrayList();

    JSONObject() {
    }

    void addMember(String string, JSONValue jSONValue) {
        this.members.add(new AbstractMap.SimpleImmutableEntry<String, JSONValue>(string, jSONValue));
    }

    @Override
    public void addTo(JSONTarget<?> jSONTarget) {
        jSONTarget.startObject();
        for (AbstractMap.SimpleImmutableEntry<String, JSONValue> simpleImmutableEntry : this.members) {
            jSONTarget.member(simpleImmutableEntry.getKey());
            simpleImmutableEntry.getValue().addTo(jSONTarget);
        }
        jSONTarget.endObject();
    }

    public Map.Entry<String, JSONValue>[] getMembers() {
        return this.members.toArray(new Map.Entry[0]);
    }

    public JSONValue getFirst(String string) {
        for (AbstractMap.SimpleImmutableEntry<String, JSONValue> simpleImmutableEntry : this.members) {
            if (!string.equals(simpleImmutableEntry.getKey())) continue;
            return simpleImmutableEntry.getValue();
        }
        return null;
    }
}

