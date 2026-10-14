/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u00a8\u0006\u0003"}, d2={"isAsciiDigit", "", "", "javalin"})
public final class CorsUtilsKt {
    private static final boolean isAsciiDigit(char $this$isAsciiDigit) {
        return '0' <= $this$isAsciiDigit ? $this$isAsciiDigit < ':' : false;
    }

    public static final /* synthetic */ boolean access$isAsciiDigit(char $receiver) {
        return CorsUtilsKt.isAsciiDigit($receiver);
    }
}

