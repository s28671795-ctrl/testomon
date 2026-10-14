/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/openapi/Visibility;", "", "priority", "", "(Ljava/lang/String;II)V", "getPriority", "()I", "PUBLIC", "DEFAULT", "PROTECTED", "PRIVATE", "openapi-specification"})
public final class Visibility
extends Enum<Visibility> {
    private final int priority;
    public static final /* enum */ Visibility PUBLIC = new Visibility(4);
    public static final /* enum */ Visibility DEFAULT = new Visibility(3);
    public static final /* enum */ Visibility PROTECTED = new Visibility(2);
    public static final /* enum */ Visibility PRIVATE = new Visibility(1);
    private static final /* synthetic */ Visibility[] $VALUES;

    private Visibility(int priority) {
        this.priority = priority;
    }

    public final int getPriority() {
        return this.priority;
    }

    public static Visibility[] values() {
        return (Visibility[])$VALUES.clone();
    }

    public static Visibility valueOf(String value) {
        return Enum.valueOf(Visibility.class, value);
    }

    static {
        $VALUES = visibilityArray = new Visibility[]{Visibility.PUBLIC, Visibility.DEFAULT, Visibility.PROTECTED, Visibility.PRIVATE};
    }
}

