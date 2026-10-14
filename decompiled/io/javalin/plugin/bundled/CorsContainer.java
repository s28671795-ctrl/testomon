/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.plugin.bundled.CorsPluginConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\nR\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/javalin/plugin/bundled/CorsContainer;", "", "()V", "corsConfigs", "", "Ljava/util/function/Consumer;", "Lio/javalin/plugin/bundled/CorsPluginConfig;", "add", "", "consumer", "", "javalin"})
public final class CorsContainer {
    @NotNull
    private final List<Consumer<CorsPluginConfig>> corsConfigs = new ArrayList();

    @NotNull
    public final List<Consumer<CorsPluginConfig>> corsConfigs() {
        List<Consumer<CorsPluginConfig>> list = Collections.unmodifiableList(this.corsConfigs);
        Intrinsics.checkNotNullExpressionValue(list, "unmodifiableList(corsConfigs)");
        return list;
    }

    public final void add(@NotNull Consumer<CorsPluginConfig> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        this.corsConfigs.add(consumer);
    }
}

