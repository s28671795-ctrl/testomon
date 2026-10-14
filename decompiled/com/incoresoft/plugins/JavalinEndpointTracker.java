/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.incoresoft.plugins.models.PluginFileInfo;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.jetty.JavalinJettyServlet;
import io.javalin.routing.HandlerEntry;
import io.javalin.routing.PathMatcher;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsEntry;
import io.javalin.websocket.WsHandlerType;
import io.javalin.websocket.WsPathMatcher;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavalinEndpointTracker {
    private static final Logger log = LoggerFactory.getLogger(JavalinEndpointTracker.class);
    private final Map<String, Set<HandlerEntry>> httpHandlersByPlugin = new HashMap<String, Set<HandlerEntry>>();
    private final Map<String, Set<WsEntry>> wsHandlersByPlugin = new HashMap<String, Set<WsEntry>>();
    private final Javalin javalin;

    public synchronized void trackPlugin(PluginFileInfo pluginInfo, Consumer<PluginFileInfo> pluginInitializeConsumer) {
        Set<HandlerEntry> httpEntriesBeforeStart = this.getHttpEntries();
        Set<WsEntry> wsEntriesBeforeStart = this.getWsEntries();
        try {
            pluginInitializeConsumer.accept(pluginInfo);
        }
        catch (RuntimeException e) {
            Sets.SetView<HandlerEntry> addedHttpEntries = Sets.difference(this.getHttpEntries(), httpEntriesBeforeStart);
            this.removeHttpEntries(addedHttpEntries);
            Sets.SetView<WsEntry> addedWsEntries = Sets.difference(this.getWsEntries(), wsEntriesBeforeStart);
            this.removeWsEntries(addedWsEntries);
            throw e;
        }
        Set<HandlerEntry> httpEntriesAfterStart = this.getHttpEntries();
        Set<WsEntry> wsEntriesAfterStart = this.getWsEntries();
        this.trackPluginHttpEntries(pluginInfo, Sets.difference(httpEntriesAfterStart, httpEntriesBeforeStart));
        this.trackPluginWsEntries(pluginInfo, Sets.difference(wsEntriesAfterStart, wsEntriesBeforeStart));
    }

    public synchronized void untrackPlugin(PluginFileInfo pluginInfo) {
        this.removeHttpEntries(this.httpHandlersByPlugin.get(pluginInfo.getId()));
        this.removeWsEntries(this.wsHandlersByPlugin.get(pluginInfo.getId()));
        this.httpHandlersByPlugin.remove(pluginInfo.getId());
    }

    private void trackPluginHttpEntries(PluginFileInfo pluginInfo, Set<HandlerEntry> httpEntries) {
        this.httpHandlersByPlugin.put(pluginInfo.getId(), httpEntries);
    }

    private void trackPluginWsEntries(PluginFileInfo pluginInfo, Set<WsEntry> wsEntries) {
        this.wsHandlersByPlugin.put(pluginInfo.getId(), wsEntries);
    }

    private void removeHttpEntries(Set<HandlerEntry> httpEntries) {
        EnumMap<HandlerType, ArrayList<HandlerEntry>> handlerEntries = this.getHttpEntriesMap();
        if (httpEntries != null) {
            handlerEntries.keySet().forEach(it -> ((ArrayList)handlerEntries.get(it)).removeIf(httpEntries::contains));
        }
    }

    private void removeWsEntries(Set<WsEntry> wsEntries) {
        try {
            EnumMap<WsHandlerType, ArrayList<WsEntry>> wsHandlerEntries = this.getWsEntriesMap();
            if (wsEntries == null) {
                return;
            }
            List<Field> fields = List.of(WsConfig.class.getDeclaredField("wsConnectHandler"), WsConfig.class.getDeclaredField("wsMessageHandler"), WsConfig.class.getDeclaredField("wsBinaryMessageHandler"), WsConfig.class.getDeclaredField("wsCloseHandler"), WsConfig.class.getDeclaredField("wsErrorHandler"));
            fields.forEach(it -> it.setAccessible(true));
            wsHandlerEntries.keySet().forEach(it -> ((ArrayList)wsHandlerEntries.get(it)).removeIf(wsEntries::contains));
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<HandlerEntry> getHttpEntries() {
        return this.getHttpEntriesMap().values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private EnumMap<HandlerType, ArrayList<HandlerEntry>> getHttpEntriesMap() {
        try {
            Field handlerEntriesField = PathMatcher.class.getDeclaredField("handlerEntries");
            handlerEntriesField.setAccessible(true);
            return (EnumMap)handlerEntriesField.get(this.javalin.javalinServlet().getMatcher());
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<WsEntry> getWsEntries() {
        return this.getWsEntriesMap().values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private EnumMap<WsHandlerType, ArrayList<WsEntry>> getWsEntriesMap() {
        try {
            Field javalinJettyServletField = Javalin.class.getDeclaredField("javalinJettyServlet");
            javalinJettyServletField.setAccessible(true);
            JavalinJettyServlet javalinJettyServlet = (JavalinJettyServlet)javalinJettyServletField.get(this.javalin);
            Field wsPathMatcherField = JavalinJettyServlet.class.getDeclaredField("wsPathMatcher");
            wsPathMatcherField.setAccessible(true);
            WsPathMatcher wsPathMatcher = (WsPathMatcher)wsPathMatcherField.get(javalinJettyServlet);
            Field wsHandlerEntriesField = WsPathMatcher.class.getDeclaredField("wsHandlerEntries");
            wsHandlerEntriesField.setAccessible(true);
            return (EnumMap)wsHandlerEntriesField.get(wsPathMatcher);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    public JavalinEndpointTracker(Javalin javalin) {
        this.javalin = javalin;
    }
}

