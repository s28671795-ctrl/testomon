/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.impl.Tools;

public class DefaultRecordListenerProvider
implements RecordListenerProvider,
Serializable {
    private final RecordListener listener;

    public static RecordListenerProvider[] providers(RecordListener ... listeners) {
        return Tools.map(listeners, DefaultRecordListenerProvider::new, RecordListenerProvider[]::new);
    }

    public DefaultRecordListenerProvider(RecordListener listener) {
        this.listener = listener;
    }

    @Override
    public final RecordListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

