/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2={"Lio/javalin/openapi/Combinator;", "", "propertyName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getPropertyName", "()Ljava/lang/String;", "ONE_OF", "ANY_OF", "ALL_OF", "openapi-specification"})
public final class Combinator
extends Enum<Combinator> {
    @NotNull
    private final String propertyName;
    public static final /* enum */ Combinator ONE_OF = new Combinator("oneOf");
    public static final /* enum */ Combinator ANY_OF = new Combinator("anyOf");
    public static final /* enum */ Combinator ALL_OF = new Combinator("allOf");
    private static final /* synthetic */ Combinator[] $VALUES;

    private Combinator(String propertyName) {
        this.propertyName = propertyName;
    }

    @NotNull
    public final String getPropertyName() {
        return this.propertyName;
    }

    public static Combinator[] values() {
        return (Combinator[])$VALUES.clone();
    }

    public static Combinator valueOf(String value) {
        return Enum.valueOf(Combinator.class, value);
    }

    static {
        $VALUES = combinatorArray = new Combinator[]{Combinator.ONE_OF, Combinator.ANY_OF, Combinator.ALL_OF};
    }
}

