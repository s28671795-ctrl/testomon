/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2={"Lio/javalin/http/util/JsonEscapeUtil;", "", "()V", "escape", "", "str", "javalin"})
public final class JsonEscapeUtil {
    @NotNull
    public static final JsonEscapeUtil INSTANCE = new JsonEscapeUtil();

    private JsonEscapeUtil() {
    }

    @NotNull
    public final String escape(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        StringBuilder builder = new StringBuilder(str.length());
        int n = str.length();
        for (int i = 0; i < n; ++i) {
            char ch = str.charAt(i);
            char c = ch;
            builder.append(c == '\"' ? "\\\"" : (c == '\n' ? "\\n" : (c == '\r' ? "\\r" : (c == '\\' ? "\\\\" : (c == '\t' ? "\\t" : (c == '\b' ? "\\b" : Character.valueOf(ch)))))));
        }
        String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
        return string;
    }
}

