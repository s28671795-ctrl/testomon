/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.cxf.bus.extension.Extension;

public final class ExtensionRegistry {
    private static ConcurrentMap<String, Extension> extensions = new ConcurrentHashMap<String, Extension>(16, 0.75f, 4);

    private ExtensionRegistry() {
    }

    public static Map<String, Extension> getRegisteredExtensions() {
        HashMap<String, Extension> exts = new HashMap<String, Extension>(extensions.size());
        for (Map.Entry ext : extensions.entrySet()) {
            exts.put((String)ext.getKey(), ((Extension)ext.getValue()).cloneNoObject());
        }
        return exts;
    }

    public static void removeExtensions(List<? extends Extension> list) {
        for (Extension extension : list) {
            extensions.remove(extension.getName(), extension);
        }
    }

    public static void addExtensions(List<? extends Extension> list) {
        for (Extension extension : list) {
            extensions.putIfAbsent(extension.getName(), extension);
        }
    }
}

