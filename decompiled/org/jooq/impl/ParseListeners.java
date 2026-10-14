/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.ParseContext;
import org.jooq.ParseListener;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.Table;
import org.jooq.impl.DefaultParseListener;
import org.jooq.impl.Tools;

@Pro
final class ParseListeners
implements ParseListener {
    private final ParseListener[] listeners;

    static ParseListener get(Configuration configuration) {
        Object[] providers = configuration.parseListenerProviders();
        if (Tools.isEmpty(providers)) {
            return new DefaultParseListener();
        }
        return new ParseListeners((ParseListenerProvider[])providers);
    }

    private ParseListeners(ParseListenerProvider[] providers) {
        this.listeners = Tools.map(providers, p -> p.provide(), ParseListener[]::new);
    }

    @Override
    public final void parseStart(ParseContext ctx) {
        for (ParseListener listener : this.listeners) {
            listener.parseStart(ctx);
        }
    }

    @Override
    public final void parseEnd(ParseContext ctx) {
        for (ParseListener listener : this.listeners) {
            listener.parseEnd(ctx);
        }
    }

    @Override
    public final Field<?> parseField(ParseContext ctx) {
        for (ParseListener listener : this.listeners) {
            Field<?> result = listener.parseField(ctx);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Override
    public final Table<?> parseTable(ParseContext ctx) {
        for (ParseListener listener : this.listeners) {
            Table<?> result = listener.parseTable(ctx);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Override
    public final Condition parseCondition(ParseContext ctx) {
        for (ParseListener listener : this.listeners) {
            Condition result = listener.parseCondition(ctx);
            if (result == null) continue;
            return result;
        }
        return null;
    }
}

