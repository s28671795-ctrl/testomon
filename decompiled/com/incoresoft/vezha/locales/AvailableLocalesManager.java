/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.locales;

import java.util.List;
import java.util.Locale;

public class AvailableLocalesManager {
    public static final List<String> LANGUAGE_LIST = List.of("uk", "ru", "en", "ar", "vi", "cs", "es", "tr", "pt");

    public List<Locale> getAvailableLanguages() {
        return LANGUAGE_LIST.stream().map(Locale::new).toList();
    }
}

