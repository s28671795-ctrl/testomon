/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin;

import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u00c6\u0003J\u001b\u0010\t\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0019\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2={"Lio/javalin/plugin/PluginInitException;", "Lio/javalin/plugin/PluginException;", "pluginClass", "Ljava/lang/Class;", "Lio/javalin/plugin/Plugin;", "(Ljava/lang/Class;)V", "getPluginClass", "()Ljava/lang/Class;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "javalin"})
public final class PluginInitException
extends PluginException {
    @NotNull
    private final Class<? extends Plugin> pluginClass;

    public PluginInitException(@NotNull Class<? extends Plugin> pluginClass) {
        Intrinsics.checkNotNullParameter(pluginClass, "pluginClass");
        super(pluginClass, "It is not allowed to register handlers during the 'Init' lifecycle. Please use the 'Apply' lifecycle instead.");
        this.pluginClass = pluginClass;
    }

    @NotNull
    public final Class<? extends Plugin> getPluginClass() {
        return this.pluginClass;
    }

    @NotNull
    public final Class<? extends Plugin> component1() {
        return this.pluginClass;
    }

    @NotNull
    public final PluginInitException copy(@NotNull Class<? extends Plugin> pluginClass) {
        Intrinsics.checkNotNullParameter(pluginClass, "pluginClass");
        return new PluginInitException(pluginClass);
    }

    public static /* synthetic */ PluginInitException copy$default(PluginInitException pluginInitException, Class clazz, int n, Object object) {
        if ((n & 1) != 0) {
            clazz = pluginInitException.pluginClass;
        }
        return pluginInitException.copy(clazz);
    }

    @Override
    @NotNull
    public String toString() {
        return "PluginInitException(pluginClass=" + this.pluginClass + ")";
    }

    public int hashCode() {
        return this.pluginClass.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PluginInitException)) {
            return false;
        }
        PluginInitException pluginInitException = (PluginInitException)other;
        return Intrinsics.areEqual(this.pluginClass, pluginInitException.pluginClass);
    }
}

