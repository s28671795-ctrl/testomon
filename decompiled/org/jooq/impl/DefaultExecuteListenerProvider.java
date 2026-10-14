/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.impl.Tools;

public class DefaultExecuteListenerProvider
implements ExecuteListenerProvider,
Serializable {
    private final ExecuteListener listener;

    public static ExecuteListenerProvider[] providers(ExecuteListener ... listeners) {
        return Tools.map(listeners, DefaultExecuteListenerProvider::new, ExecuteListenerProvider[]::new);
    }

    public DefaultExecuteListenerProvider(ExecuteListener listener) {
        this.listener = listener;
    }

    @Override
    public final ExecuteListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

