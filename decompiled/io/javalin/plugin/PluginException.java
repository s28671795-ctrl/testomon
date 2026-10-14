/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin;

import io.javalin.plugin.Plugin;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u00002\u00060\u0001j\u0002`\u0002B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/plugin/PluginException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "pluginClass", "Ljava/lang/Class;", "Lio/javalin/plugin/Plugin;", "message", "", "(Ljava/lang/Class;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "javalin"})
public abstract class PluginException
extends RuntimeException {
    @NotNull
    private final String message;

    public PluginException(@NotNull Class<? extends Plugin> pluginClass, @NotNull String message) {
        Intrinsics.checkNotNullParameter(pluginClass, "pluginClass");
        Intrinsics.checkNotNullParameter(message, "message");
        super("Error in " + pluginClass.getCanonicalName() + ": " + message);
        this.message = message;
    }

    @Override
    @NotNull
    public String getMessage() {
        return this.message;
    }
}

