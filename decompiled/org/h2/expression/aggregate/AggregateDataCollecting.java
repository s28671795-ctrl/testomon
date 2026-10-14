/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

final class AggregateDataCollecting
extends AggregateData
implements Iterable<Value> {
    private final boolean distinct;
    private final boolean orderedWithOrder;
    private final NullCollectionMode nullCollectionMode;
    Collection<Value> values;
    private Value shared;

    AggregateDataCollecting(boolean bl, boolean bl2, NullCollectionMode nullCollectionMode) {
        this.distinct = bl;
        this.orderedWithOrder = bl2;
        this.nullCollectionMode = nullCollectionMode;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value2) {
        if (this.nullCollectionMode == NullCollectionMode.IGNORED && this.isNull(value2)) {
            return;
        }
        Collection<Value> collection = this.values;
        if (collection == null) {
            if (this.distinct) {
                Comparator<Value> comparator = sessionLocal.getDatabase().getCompareMode();
                if (this.orderedWithOrder) {
                    comparator = Comparator.comparing(value -> ((ValueRow)value).getList()[0], comparator);
                }
                collection = new TreeSet<Value>(comparator);
            } else {
                collection = new ArrayList<Value>();
            }
            this.values = collection;
        }
        if (this.nullCollectionMode == NullCollectionMode.EXCLUDED && this.isNull(value2)) {
            return;
        }
        collection.add(value2);
    }

    private boolean isNull(Value value) {
        return (this.orderedWithOrder ? ((ValueRow)value).getList()[0] : value) == ValueNull.INSTANCE;
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        return null;
    }

    int getCount() {
        return this.values != null ? this.values.size() : 0;
    }

    Value[] getArray() {
        Collection<Value> collection = this.values;
        if (collection == null) {
            return null;
        }
        return collection.toArray(Value.EMPTY_VALUES);
    }

    @Override
    public Iterator<Value> iterator() {
        return this.values != null ? this.values.iterator() : Collections.emptyIterator();
    }

    void setSharedArgument(Value value) {
        if (this.shared == null) {
            this.shared = value;
        } else if (!this.shared.equals(value)) {
            throw DbException.get(90008, "Inverse distribution function argument", this.shared.getTraceSQL() + "<>" + value.getTraceSQL());
        }
    }

    Value getSharedArgument() {
        return this.shared;
    }

    static enum NullCollectionMode {
        IGNORED,
        EXCLUDED,
        USED_OR_IMPOSSIBLE;

    }
}

