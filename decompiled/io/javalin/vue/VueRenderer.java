/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\t"}, d2={"Lio/javalin/vue/VueRenderer;", "", "()V", "postRender", "", "layout", "ctx", "Lio/javalin/http/Context;", "preRender", "javalin"})
public class VueRenderer {
    @NotNull
    public String preRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return layout;
    }

    @NotNull
    public String postRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return layout;
    }
}

