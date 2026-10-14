/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.DiagnosticsListener;
import org.jooq.DiagnosticsListenerProvider;
import org.jooq.impl.Tools;

public class DefaultDiagnosticsListenerProvider
implements DiagnosticsListenerProvider,
Serializable {
    private final DiagnosticsListener listener;

    public static DiagnosticsListenerProvider[] providers(DiagnosticsListener ... listeners) {
        return Tools.map(listeners, DefaultDiagnosticsListenerProvider::new, DiagnosticsListenerProvider[]::new);
    }

    public DefaultDiagnosticsListenerProvider(DiagnosticsListener listener) {
        this.listener = listener;
    }

    @Override
    public final DiagnosticsListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

