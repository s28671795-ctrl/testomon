/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.function.Supplier;
import org.jooq.Configuration;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.conf.InvocationOrder;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DefaultRecordContext;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;

final class RecordDelegate<R extends Record> {
    private final Configuration configuration;
    private final Supplier<R> recordSupplier;
    private final Boolean fetched;
    private final RecordLifecycleType type;

    RecordDelegate(Configuration configuration, Supplier<R> recordSupplier, Boolean fetched) {
        this(configuration, recordSupplier, fetched, RecordLifecycleType.LOAD);
    }

    RecordDelegate(Configuration configuration, Supplier<R> recordSupplier, Boolean fetched, RecordLifecycleType type) {
        this.configuration = configuration;
        this.recordSupplier = recordSupplier;
        this.fetched = fetched;
        this.type = type;
    }

    static final <R extends Record> RecordDelegate<R> delegate(Configuration configuration, R record, RecordLifecycleType type) {
        return new RecordDelegate<Record>(configuration, () -> record, null, type);
    }

    final <E extends Exception> R operate(ThrowingFunction<R, R, E> operation) throws E {
        Exception exception;
        AbstractScope ctx;
        RecordListener[] listeners;
        Record record;
        block31: {
            record = (Record)this.recordSupplier.get();
            if (this.fetched != null && record instanceof AbstractRecord) {
                ((AbstractRecord)record).fetched = this.fetched;
            }
            Object[] providers = null;
            listeners = null;
            ctx = null;
            exception = null;
            if (this.configuration != null && !Tools.isEmpty(providers = this.configuration.recordListenerProviders())) {
                listeners = Tools.map(providers, p -> p.provide(), RecordListener[]::new);
                ctx = new DefaultRecordContext(this.configuration, this.executeType(), record);
            }
            if (listeners != null) {
                block20: for (RecordListener recordListener : ctx == null || ctx.settings().getRecordListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(listeners) : Tools.reverseIterable(listeners)) {
                    switch (this.type) {
                        case LOAD: {
                            recordListener.loadStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case REFRESH: {
                            recordListener.refreshStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case STORE: {
                            recordListener.storeStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case INSERT: {
                            recordListener.insertStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case UPDATE: {
                            recordListener.updateStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case MERGE: {
                            recordListener.mergeStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                        case DELETE: {
                            recordListener.deleteStart((RecordContext)((Object)ctx));
                            continue block20;
                        }
                    }
                    throw new IllegalStateException("Type not supported: " + String.valueOf((Object)this.type));
                }
            }
            if (Tools.attachRecords(this.configuration)) {
                record.attach(this.configuration);
            }
            if (operation != null) {
                try {
                    operation.apply(record);
                }
                catch (Exception e) {
                    exception = e;
                    if (e instanceof ControlFlowSignal) break block31;
                    if (ctx != null) {
                        ((DefaultRecordContext)ctx).exception = e;
                    }
                    if (listeners == null) break block31;
                    for (RecordListener listener : listeners) {
                        listener.exception((RecordContext)((Object)ctx));
                    }
                }
            }
        }
        if (listeners != null) {
            block22: for (RecordListener recordListener : ctx == null || ctx.settings().getRecordListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(listeners) : Tools.reverseIterable(listeners)) {
                switch (this.type) {
                    case LOAD: {
                        recordListener.loadEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case REFRESH: {
                        recordListener.refreshEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case STORE: {
                        recordListener.storeEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case INSERT: {
                        recordListener.insertEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case UPDATE: {
                        recordListener.updateEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case MERGE: {
                        recordListener.mergeEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                    case DELETE: {
                        recordListener.deleteEnd((RecordContext)((Object)ctx));
                        continue block22;
                    }
                }
                throw new IllegalStateException("Type not supported: " + String.valueOf((Object)this.type));
            }
        }
        if (exception != null) {
            throw exception;
        }
        return (R)record;
    }

    private final ExecuteType executeType() {
        return this.type == RecordLifecycleType.LOAD || this.type == RecordLifecycleType.REFRESH ? ExecuteType.READ : ExecuteType.WRITE;
    }

    static enum RecordLifecycleType {
        LOAD,
        REFRESH,
        STORE,
        INSERT,
        UPDATE,
        MERGE,
        DELETE;

    }
}

