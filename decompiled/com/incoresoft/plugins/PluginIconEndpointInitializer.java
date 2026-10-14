/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.plugins.models.PluginFileInfo;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class PluginIconEndpointInitializer {
    private final Javalin javalin;
    private static final Map<String, String> EXTENSION_CONTENT_TYPE_MAP = Map.ofEntries(Map.entry("js", "text/javascript"), Map.entry("css", "text/css"), Map.entry("svg", "image/svg+xml"), Map.entry("jpg", "image/jpg"), Map.entry("png", "image/png"), Map.entry("ttf", "font/ttf"), Map.entry("woff", "font/woff"), Map.entry("mp3", "audio/mpeg"));

    public void initializeIconEndpoint(PluginFileInfo pluginInfo) {
        String id = pluginInfo.getId();
        String iconPath = pluginInfo.getIconPath();
        ClassLoader classLoader = pluginInfo.getClassLoader();
        if (StringUtils.isBlank(iconPath)) {
            return;
        }
        this.javalin.get("resources/" + id + "/icon", ctx -> {
            ctx.contentType(this.guessContentType(iconPath));
            InputStream resource = classLoader.getResourceAsStream(iconPath);
            if (resource == null) {
                throw new NotFoundResponse();
            }
            ctx.result(resource);
        }, AppRole.ANYONE);
    }

    private String guessContentType(String path) {
        String extension = "";
        String[] data = path.split("\\.");
        if (data.length > 0) {
            extension = data[data.length - 1];
        }
        return EXTENSION_CONTENT_TYPE_MAP.getOrDefault(extension, "text/html");
    }

    @Inject
    public PluginIconEndpointInitializer(Javalin javalin) {
        this.javalin = javalin;
    }
}

