/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import java.util.Map;

public class ContentTypeUtils {
    private static final Map<String, String> contentType = Map.ofEntries(Map.entry("js", "text/javascript"), Map.entry("css", "text/css"), Map.entry("svg", "image/svg+xml"), Map.entry("jpg", "image/jpg"), Map.entry("png", "image/png"), Map.entry("ttf", "font/ttf"), Map.entry("woff", "font/woff"), Map.entry("mp3", "audio/mpeg"));

    public static String guessContentType(String path) {
        String extension = "";
        String[] data = path.split("\\.");
        if (data.length > 0) {
            extension = data[data.length - 1];
        }
        return contentType.getOrDefault(extension, "text/html");
    }
}

