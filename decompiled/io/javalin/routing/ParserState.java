/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/routing/ParserState;", "", "(Ljava/lang/String;I)V", "NORMAL", "INSIDE_SLASH_IGNORING_BRACKETS", "INSIDE_SLASH_ACCEPTING_BRACKETS", "javalin"})
final class ParserState
extends Enum<ParserState> {
    public static final /* enum */ ParserState NORMAL = new ParserState();
    public static final /* enum */ ParserState INSIDE_SLASH_IGNORING_BRACKETS = new ParserState();
    public static final /* enum */ ParserState INSIDE_SLASH_ACCEPTING_BRACKETS = new ParserState();
    private static final /* synthetic */ ParserState[] $VALUES;

    public static ParserState[] values() {
        return (ParserState[])$VALUES.clone();
    }

    public static ParserState valueOf(String value) {
        return Enum.valueOf(ParserState.class, value);
    }

    static {
        $VALUES = parserStateArray = new ParserState[]{ParserState.NORMAL, ParserState.INSIDE_SLASH_IGNORING_BRACKETS, ParserState.INSIDE_SLASH_ACCEPTING_BRACKETS};
    }
}

