/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/openapi/ComposedType;", "", "(Ljava/lang/String;I)V", "NULL", "ANY_OF", "ONE_OF", "openapi-specification"})
public final class ComposedType
extends Enum<ComposedType> {
    public static final /* enum */ ComposedType NULL = new ComposedType();
    public static final /* enum */ ComposedType ANY_OF = new ComposedType();
    public static final /* enum */ ComposedType ONE_OF = new ComposedType();
    private static final /* synthetic */ ComposedType[] $VALUES;

    public static ComposedType[] values() {
        return (ComposedType[])$VALUES.clone();
    }

    public static ComposedType valueOf(String value) {
        return Enum.valueOf(ComposedType.class, value);
    }

    static {
        $VALUES = composedTypeArray = new ComposedType[]{ComposedType.NULL, ComposedType.ANY_OF, ComposedType.ONE_OF};
    }
}

