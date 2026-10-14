/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.localization;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class LocalizationCache {
    private final ClassLoader classloader;
    private final String folderName;
    private final Map<String, Map<String, String>> internalLocaleCache = new ConcurrentHashMap<String, Map<String, String>>();

    public LocalizationCache(@NotNull ClassLoader classLoader, @NotNull String folderName) {
        this.classloader = Objects.requireNonNull(classLoader);
        this.folderName = Objects.requireNonNull(folderName);
        Locale.setDefault(new Locale("en"));
    }

    public Map<String, String> getLocale(Locale locale) {
        if (!this.internalLocaleCache.containsKey(locale.getLanguage()) && this.internalLocaleExists(locale)) {
            this.internalLocaleCache.put(locale.getLanguage(), this.readInternalFolderLocale(locale));
        }
        HashMap<String, String> localeMap = new HashMap<String, String>(this.internalLocaleCache.getOrDefault(locale.getLanguage(), Collections.emptyMap()));
        if (this.externalLocaleExists(locale)) {
            localeMap.putAll(this.readExternalFolderLocale(locale));
        }
        return localeMap;
    }

    private boolean internalLocaleExists(Locale locale) {
        try {
            this.readInternalBundle(locale, this.classloader);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private Map<String, String> readInternalFolderLocale(Locale locale) {
        return this.toMap(this.readInternalBundle(locale, this.classloader));
    }

    private ResourceBundle readInternalBundle(Locale locale, ClassLoader classloader) {
        return ResourceBundle.getBundle("locales/locales", locale, classloader);
    }

    private boolean externalLocaleExists(Locale locale) {
        try {
            this.readExternalBundle(locale);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private Map<String, String> readExternalFolderLocale(Locale locale) {
        return this.toMap(this.readExternalBundle(locale));
    }

    private ResourceBundle readExternalBundle(Locale locale) {
        return ResourceBundle.getBundle("locales", locale, this.externalLoader());
    }

    @NotNull
    private URLClassLoader externalLoader() {
        URL[] urls2;
        File file = new File("locales/" + this.folderName + "/");
        try {
            urls2 = new URL[]{file.toURI().toURL()};
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return new URLClassLoader(urls2);
    }

    @NotNull
    private Map<String, String> toMap(ResourceBundle bundle2) {
        return bundle2.keySet().stream().collect(Collectors.toMap(Function.identity(), bundle2::getString));
    }
}

