/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.h2.util.json.JSONItemType;
import org.h2.util.json.JSONValidationTarget;

public final class JSONValidationTargetWithUniqueKeys
extends JSONValidationTarget {
    private final ArrayDeque<Object> stack = new ArrayDeque();
    private final ArrayDeque<String> names = new ArrayDeque();
    private boolean needSeparator;
    private String memberName;
    private JSONItemType type;

    @Override
    public void startObject() {
        this.beforeValue();
        this.names.push(this.memberName != null ? this.memberName : "");
        this.memberName = null;
        this.stack.push(new HashSet());
    }

    @Override
    public void endObject() {
        if (this.memberName != null) {
            throw new IllegalStateException();
        }
        if (!(this.stack.poll() instanceof HashSet)) {
            throw new IllegalStateException();
        }
        this.memberName = this.names.pop();
        this.afterValue(JSONItemType.OBJECT);
    }

    @Override
    public void startArray() {
        this.beforeValue();
        this.names.push(this.memberName != null ? this.memberName : "");
        this.memberName = null;
        this.stack.push(Collections.emptyList());
    }

    @Override
    public void endArray() {
        if (!(this.stack.poll() instanceof List)) {
            throw new IllegalStateException();
        }
        this.memberName = this.names.pop();
        this.afterValue(JSONItemType.ARRAY);
    }

    @Override
    public void member(String string) {
        if (this.memberName != null || !(this.stack.peek() instanceof HashSet)) {
            throw new IllegalStateException();
        }
        this.memberName = string;
        this.beforeValue();
    }

    @Override
    public void valueNull() {
        this.beforeValue();
        this.afterValue(JSONItemType.SCALAR);
    }

    @Override
    public void valueFalse() {
        this.beforeValue();
        this.afterValue(JSONItemType.SCALAR);
    }

    @Override
    public void valueTrue() {
        this.beforeValue();
        this.afterValue(JSONItemType.SCALAR);
    }

    @Override
    public void valueNumber(BigDecimal bigDecimal) {
        this.beforeValue();
        this.afterValue(JSONItemType.SCALAR);
    }

    @Override
    public void valueString(String string) {
        this.beforeValue();
        this.afterValue(JSONItemType.SCALAR);
    }

    private void beforeValue() {
        if (this.memberName == null && this.stack.peek() instanceof HashSet) {
            throw new IllegalStateException();
        }
        if (this.needSeparator) {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException();
            }
            this.needSeparator = false;
        }
    }

    private void afterValue(JSONItemType jSONItemType) {
        Object object = this.stack.peek();
        if (object == null) {
            this.type = jSONItemType;
        } else if (object instanceof HashSet && !((HashSet)object).add(this.memberName)) {
            throw new IllegalStateException();
        }
        this.needSeparator = true;
        this.memberName = null;
    }

    @Override
    public boolean isPropertyExpected() {
        return this.memberName == null && this.stack.peek() instanceof HashSet;
    }

    @Override
    public boolean isValueSeparatorExpected() {
        return this.needSeparator;
    }

    @Override
    public JSONItemType getResult() {
        if (!this.stack.isEmpty() || this.type == null) {
            throw new IllegalStateException();
        }
        return this.type;
    }
}

