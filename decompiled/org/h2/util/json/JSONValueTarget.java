/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONBoolean;
import org.h2.util.json.JSONNull;
import org.h2.util.json.JSONNumber;
import org.h2.util.json.JSONObject;
import org.h2.util.json.JSONString;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONValue;

public final class JSONValueTarget
extends JSONTarget<JSONValue> {
    private final ArrayDeque<JSONValue> stack = new ArrayDeque();
    private final ArrayDeque<String> names = new ArrayDeque();
    private boolean needSeparator;
    private String memberName;
    private JSONValue result;

    @Override
    public void startObject() {
        this.beforeValue();
        this.names.push(this.memberName != null ? this.memberName : "");
        this.memberName = null;
        this.stack.push(new JSONObject());
    }

    @Override
    public void endObject() {
        if (this.memberName != null) {
            throw new IllegalStateException();
        }
        JSONValue jSONValue = this.stack.poll();
        if (!(jSONValue instanceof JSONObject)) {
            throw new IllegalStateException();
        }
        this.memberName = this.names.pop();
        this.afterValue(jSONValue);
    }

    @Override
    public void startArray() {
        this.beforeValue();
        this.names.push(this.memberName != null ? this.memberName : "");
        this.memberName = null;
        this.stack.push(new JSONArray());
    }

    @Override
    public void endArray() {
        JSONValue jSONValue = this.stack.poll();
        if (!(jSONValue instanceof JSONArray)) {
            throw new IllegalStateException();
        }
        this.memberName = this.names.pop();
        this.afterValue(jSONValue);
    }

    @Override
    public void member(String string) {
        if (this.memberName != null || !(this.stack.peek() instanceof JSONObject)) {
            throw new IllegalStateException();
        }
        this.memberName = string;
        this.beforeValue();
    }

    @Override
    public void valueNull() {
        this.beforeValue();
        this.afterValue(JSONNull.NULL);
    }

    @Override
    public void valueFalse() {
        this.beforeValue();
        this.afterValue(JSONBoolean.FALSE);
    }

    @Override
    public void valueTrue() {
        this.beforeValue();
        this.afterValue(JSONBoolean.TRUE);
    }

    @Override
    public void valueNumber(BigDecimal bigDecimal) {
        this.beforeValue();
        this.afterValue(new JSONNumber(bigDecimal));
    }

    @Override
    public void valueString(String string) {
        this.beforeValue();
        this.afterValue(new JSONString(string));
    }

    private void beforeValue() {
        if (this.memberName == null && this.stack.peek() instanceof JSONObject) {
            throw new IllegalStateException();
        }
        if (this.needSeparator) {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException();
            }
            this.needSeparator = false;
        }
    }

    private void afterValue(JSONValue jSONValue) {
        JSONValue jSONValue2 = this.stack.peek();
        if (jSONValue2 == null) {
            this.result = jSONValue;
        } else if (jSONValue2 instanceof JSONObject) {
            ((JSONObject)jSONValue2).addMember(this.memberName, jSONValue);
        } else {
            ((JSONArray)jSONValue2).addElement(jSONValue);
        }
        this.needSeparator = true;
        this.memberName = null;
    }

    @Override
    public boolean isPropertyExpected() {
        return this.memberName == null && this.stack.peek() instanceof JSONObject;
    }

    @Override
    public boolean isValueSeparatorExpected() {
        return this.needSeparator;
    }

    @Override
    public JSONValue getResult() {
        if (!this.stack.isEmpty() || this.result == null) {
            throw new IllegalStateException();
        }
        return this.result;
    }
}

