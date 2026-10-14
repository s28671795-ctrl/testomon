/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import io.javalin.vue.VueHandler;
import io.javalin.vue.VueRenderer;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0012"}, d2={"Lio/javalin/vue/VueComponent;", "Lio/javalin/vue/VueHandler;", "component", "", "state", "", "renderer", "Lio/javalin/vue/VueRenderer;", "(Ljava/lang/String;Ljava/lang/Object;Lio/javalin/vue/VueRenderer;)V", "getComponent", "()Ljava/lang/String;", "getState", "()Ljava/lang/Object;", "postRender", "layout", "ctx", "Lio/javalin/http/Context;", "preRender", "javalin"})
public final class VueComponent
extends VueHandler {
    @NotNull
    private final String component;
    @Nullable
    private final Object state;
    @NotNull
    private final VueRenderer renderer;

    @JvmOverloads
    public VueComponent(@NotNull String component, @Nullable Object state, @NotNull VueRenderer renderer) {
        Intrinsics.checkNotNullParameter(component, "component");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        super(component);
        this.component = component;
        this.state = state;
        this.renderer = renderer;
    }

    public /* synthetic */ VueComponent(String string, Object object, VueRenderer vueRenderer, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            vueRenderer = new VueRenderer();
        }
        this(string, object, vueRenderer);
    }

    @NotNull
    public final String getComponent() {
        return this.component;
    }

    @Nullable
    public final Object getState() {
        return this.state;
    }

    @Override
    @Nullable
    public Object state(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return this.state;
    }

    @Override
    @NotNull
    public String preRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return this.renderer.preRender(layout, ctx);
    }

    @Override
    @NotNull
    public String postRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return this.renderer.postRender(layout, ctx);
    }

    @JvmOverloads
    public VueComponent(@NotNull String component, @Nullable Object state) {
        Intrinsics.checkNotNullParameter(component, "component");
        this(component, state, null, 4, null);
    }

    @JvmOverloads
    public VueComponent(@NotNull String component) {
        Intrinsics.checkNotNullParameter(component, "component");
        this(component, null, null, 6, null);
    }
}

