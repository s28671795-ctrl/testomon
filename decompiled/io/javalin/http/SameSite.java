/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/http/SameSite;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "toString", "NONE", "STRICT", "LAX", "javalin"})
public final class SameSite
extends Enum<SameSite> {
    @NotNull
    private final String value;
    public static final /* enum */ SameSite NONE = new SameSite("SameSite=None");
    public static final /* enum */ SameSite STRICT = new SameSite("SameSite=Strict");
    public static final /* enum */ SameSite LAX = new SameSite("SameSite=Lax");
    private static final /* synthetic */ SameSite[] $VALUES;

    private SameSite(String value) {
        this.value = value;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    @NotNull
    public String toString() {
        return this.value;
    }

    public static SameSite[] values() {
        return (SameSite[])$VALUES.clone();
    }

    public static SameSite valueOf(String value) {
        return Enum.valueOf(SameSite.class, value);
    }

    static {
        $VALUES = sameSiteArray = new SameSite[]{SameSite.NONE, SameSite.STRICT, SameSite.LAX};
    }
}

