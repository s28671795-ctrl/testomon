/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Consumer;
import java.util.function.Function;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ParseContext;
import org.jooq.ParseListener;
import org.jooq.Pro;
import org.jooq.Table;

@Pro
public final class CallbackParseListener
implements ParseListener {
    private final Consumer<? super ParseContext> onParseStart;
    private final Consumer<? super ParseContext> onParseEnd;
    private final Function<? super ParseContext, ? extends Field<?>> onParseField;
    private final Function<? super ParseContext, ? extends Table<?>> onParseTable;
    private final Function<? super ParseContext, ? extends Condition> onParseCondition;

    public CallbackParseListener() {
        this(null, null, null, null, null);
    }

    private CallbackParseListener(Consumer<? super ParseContext> onParseStart, Consumer<? super ParseContext> onParseEnd, Function<? super ParseContext, ? extends Field<?>> onParseField, Function<? super ParseContext, ? extends Table<?>> onParseTable, Function<? super ParseContext, ? extends Condition> onParseCondition) {
        this.onParseStart = onParseStart;
        this.onParseEnd = onParseEnd;
        this.onParseField = onParseField;
        this.onParseTable = onParseTable;
        this.onParseCondition = onParseCondition;
    }

    @Override
    public final void parseStart(ParseContext ctx) {
        if (this.onParseStart != null) {
            this.onParseStart.accept(ctx);
        }
    }

    @Override
    public final void parseEnd(ParseContext ctx) {
        if (this.onParseEnd != null) {
            this.onParseEnd.accept(ctx);
        }
    }

    @Override
    public final Field<?> parseField(ParseContext ctx) {
        return this.onParseField == null ? null : this.onParseField.apply(ctx);
    }

    @Override
    public final Table<?> parseTable(ParseContext ctx) {
        return this.onParseTable == null ? null : this.onParseTable.apply(ctx);
    }

    @Override
    public final Condition parseCondition(ParseContext ctx) {
        return this.onParseCondition == null ? null : this.onParseCondition.apply(ctx);
    }

    @Override
    public final CallbackParseListener onParseStart(Consumer<? super ParseContext> newOnParseStart) {
        return new CallbackParseListener(newOnParseStart, this.onParseEnd, this.onParseField, this.onParseTable, this.onParseCondition);
    }

    @Override
    public final CallbackParseListener onParseEnd(Consumer<? super ParseContext> newOnParseEnd) {
        return new CallbackParseListener(this.onParseStart, newOnParseEnd, this.onParseField, this.onParseTable, this.onParseCondition);
    }

    @Override
    public final CallbackParseListener onParseField(Function<? super ParseContext, ? extends Field<?>> newOnParseField) {
        return new CallbackParseListener(this.onParseStart, this.onParseEnd, newOnParseField, this.onParseTable, this.onParseCondition);
    }

    @Override
    public final CallbackParseListener onParseTable(Function<? super ParseContext, ? extends Table<?>> newOnParseTable) {
        return new CallbackParseListener(this.onParseStart, this.onParseEnd, this.onParseField, newOnParseTable, this.onParseCondition);
    }

    @Override
    public final CallbackParseListener onParseCondition(Function<? super ParseContext, ? extends Condition> newOnParseCondition) {
        return new CallbackParseListener(this.onParseStart, this.onParseEnd, this.onParseField, this.onParseTable, newOnParseCondition);
    }
}

