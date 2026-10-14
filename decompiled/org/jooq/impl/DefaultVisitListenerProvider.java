/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.impl.Tools;

public class DefaultVisitListenerProvider
implements VisitListenerProvider,
Serializable {
    private final VisitListener listener;

    public static VisitListenerProvider[] providers(VisitListener ... listeners) {
        return Tools.map(listeners, DefaultVisitListenerProvider::new, VisitListenerProvider[]::new);
    }

    public DefaultVisitListenerProvider(VisitListener listener) {
        this.listener = listener;
    }

    @Override
    public final VisitListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

