/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import org.h2.util.ByteStack;
import org.h2.util.json.JSONItemType;
import org.h2.util.json.JSONValidationTarget;

public final class JSONValidationTargetWithoutUniqueKeys
extends JSONValidationTarget {
    private static final byte OBJECT = 1;
    private static final byte ARRAY = 2;
    private JSONItemType type;
    private final ByteStack stack = new ByteStack();
    private boolean needSeparator;
    private boolean afterName;

    @Override
    public void startObject() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)1);
    }

    @Override
    public void endObject() {
        if (this.afterName || this.stack.poll(-1) != 1) {
            throw new IllegalStateException();
        }
        this.afterValue(JSONItemType.OBJECT);
    }

    @Override
    public void startArray() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)2);
    }

    @Override
    public void endArray() {
        if (this.stack.poll(-1) != 2) {
            throw new IllegalStateException();
        }
        this.afterValue(JSONItemType.ARRAY);
    }

    @Override
    public void member(String string) {
        if (this.afterName || this.stack.peek(-1) != 1) {
            throw new IllegalStateException();
        }
        this.afterName = true;
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
        if (!this.afterName && this.stack.peek(-1) == 1) {
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
        this.needSeparator = true;
        this.afterName = false;
        if (this.stack.isEmpty()) {
            this.type = jSONItemType;
        }
    }

    @Override
    public boolean isPropertyExpected() {
        return !this.afterName && this.stack.peek(-1) == 1;
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

