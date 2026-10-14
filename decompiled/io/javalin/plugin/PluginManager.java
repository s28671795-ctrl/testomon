/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.WsHandlerMetaInfo;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginAlreadyRegisteredException;
import io.javalin.plugin.PluginInitException;
import io.javalin.plugin.PluginLifecycleInit;
import io.javalin.plugin.RepeatablePlugin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lio/javalin/plugin/PluginManager;", "", "()V", "initializedPlugins", "", "Lio/javalin/plugin/Plugin;", "plugins", "", "initializePlugins", "", "app", "Lio/javalin/Javalin;", "register", "plugin", "javalin"})
@SourceDebugExtension(value={"SMAP\nPluginManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PluginManager.kt\nio/javalin/plugin/PluginManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,44:1\n1747#2,3:45\n819#2:48\n847#2,2:49\n1855#2,2:51\n1855#2,2:53\n*S KotlinDebug\n*F\n+ 1 PluginManager.kt\nio/javalin/plugin/PluginManager\n*L\n11#1:45,3\n25#1:48\n25#1:49,2\n27#1:51,2\n37#1:53,2\n*E\n"})
public final class PluginManager {
    @NotNull
    private final List<Plugin> plugins = new ArrayList();
    @NotNull
    private final Set<Plugin> initializedPlugins = new LinkedHashSet();

    public final void register(@NotNull Plugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        if (!(plugin instanceof RepeatablePlugin)) {
            boolean bl;
            block5: {
                Iterable $this$any$iv = this.plugins;
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        Plugin it = (Plugin)element$iv;
                        boolean bl2 = false;
                        if (!Intrinsics.areEqual(it.getClass(), plugin.getClass())) continue;
                        bl = true;
                        break block5;
                    }
                    bl = false;
                }
            }
            if (bl) {
                throw new PluginAlreadyRegisteredException(plugin.getClass());
            }
        }
        this.plugins.add(plugin);
    }

    /*
     * WARNING - void declaration
     */
    public final void initializePlugins(@NotNull Javalin app) {
        void $this$filterNotTo$iv$iv;
        Intrinsics.checkNotNullParameter(app, "app");
        Ref.BooleanRef anyHandlerAdded = new Ref.BooleanRef();
        app.events(arg_0 -> PluginManager.initializePlugins$lambda$3(anyHandlerAdded, arg_0));
        Iterable $this$filterNot$iv = this.plugins;
        boolean $i$f$filterNot = false;
        Iterator iterator2 = $this$filterNot$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            Plugin it = (Plugin)element$iv$iv;
            boolean bl = false;
            if (this.initializedPlugins.contains(it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List pluginsToInitialize = (List)destination$iv$iv;
        Iterable $this$forEach$iv = pluginsToInitialize;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Plugin it = (Plugin)element$iv;
            boolean bl = false;
            if (!(it instanceof PluginLifecycleInit)) continue;
            ((PluginLifecycleInit)((Object)it)).init(app);
            if (!anyHandlerAdded.element) continue;
            throw new PluginInitException(it.getClass());
        }
        $this$forEach$iv = pluginsToInitialize;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Plugin it = (Plugin)element$iv;
            boolean bl = false;
            it.apply(app);
            this.initializedPlugins.add(it);
        }
    }

    private static final void initializePlugins$lambda$3$lambda$1(Ref.BooleanRef $anyHandlerAdded, HandlerMetaInfo it) {
        Intrinsics.checkNotNullParameter($anyHandlerAdded, "$anyHandlerAdded");
        $anyHandlerAdded.element = true;
    }

    private static final void initializePlugins$lambda$3$lambda$2(Ref.BooleanRef $anyHandlerAdded, WsHandlerMetaInfo it) {
        Intrinsics.checkNotNullParameter($anyHandlerAdded, "$anyHandlerAdded");
        $anyHandlerAdded.element = true;
    }

    private static final void initializePlugins$lambda$3(Ref.BooleanRef $anyHandlerAdded, EventListener event) {
        Intrinsics.checkNotNullParameter($anyHandlerAdded, "$anyHandlerAdded");
        event.handlerAdded(arg_0 -> PluginManager.initializePlugins$lambda$3$lambda$1($anyHandlerAdded, arg_0));
        event.wsHandlerAdded(arg_0 -> PluginManager.initializePlugins$lambda$3$lambda$2($anyHandlerAdded, arg_0));
    }
}

