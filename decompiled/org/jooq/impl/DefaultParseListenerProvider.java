/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.ParseListener;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.impl.Tools;

@Pro
public class DefaultParseListenerProvider
implements ParseListenerProvider,
Serializable {
    private final ParseListener listener;

    public static ParseListenerProvider[] providers(ParseListener ... listeners) {
        return Tools.map(listeners, DefaultParseListenerProvider::new, ParseListenerProvider[]::new);
    }

    public DefaultParseListenerProvider(ParseListener listener) {
        this.listener = listener;
    }

    @Override
    public final ParseListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

