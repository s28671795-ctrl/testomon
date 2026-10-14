/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.EventListener;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ParseContext;
import org.jooq.Pro;
import org.jooq.Table;
import org.jooq.impl.CallbackParseListener;
import org.jooq.impl.ParserException;

@Pro
public interface ParseListener
extends EventListener {
    default public void parseStart(ParseContext ctx) throws ParserException {
    }

    default public void parseEnd(ParseContext ctx) throws ParserException {
    }

    @Nullable
    default public Field<?> parseField(ParseContext ctx) throws ParserException {
        return null;
    }

    @Nullable
    default public Table<?> parseTable(ParseContext ctx) throws ParserException {
        return null;
    }

    @Nullable
    default public Condition parseCondition(ParseContext ctx) throws ParserException {
        return null;
    }

    public static CallbackParseListener onParseStart(Consumer<? super ParseContext> onParseStart) {
        return new CallbackParseListener().onParseStart(onParseStart);
    }

    public static CallbackParseListener onParseEnd(Consumer<? super ParseContext> onParseEnd) {
        return new CallbackParseListener().onParseEnd(onParseEnd);
    }

    public static CallbackParseListener onParseField(Function<? super ParseContext, ? extends Field<?>> onParseField) {
        return new CallbackParseListener().onParseField(onParseField);
    }

    public static CallbackParseListener onParseTable(Function<? super ParseContext, ? extends Table<?>> onParseTable) {
        return new CallbackParseListener().onParseTable(onParseTable);
    }

    public static CallbackParseListener onParseCondition(Function<? super ParseContext, ? extends Condition> onParseCondition) {
        return new CallbackParseListener().onParseCondition(onParseCondition);
    }
}

