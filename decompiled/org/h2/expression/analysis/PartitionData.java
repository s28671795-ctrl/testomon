/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import java.util.HashMap;
import org.h2.value.Value;

public final class PartitionData {
    private Object data;
    private Value result;
    private HashMap<Integer, Value> orderedResult;

    PartitionData(Object object) {
        this.data = object;
    }

    Object getData() {
        return this.data;
    }

    Value getResult() {
        return this.result;
    }

    void setResult(Value value) {
        this.result = value;
        this.data = null;
    }

    HashMap<Integer, Value> getOrderedResult() {
        return this.orderedResult;
    }

    void setOrderedResult(HashMap<Integer, Value> hashMap) {
        this.orderedResult = hashMap;
        this.data = null;
    }
}

