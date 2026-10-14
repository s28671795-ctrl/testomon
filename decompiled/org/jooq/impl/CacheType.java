/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Predicate;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;

public enum CacheType {
    REFLECTION_CACHE_GET_ANNOTATED_GETTER(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-annotated-getter"),
    REFLECTION_CACHE_GET_ANNOTATED_MEMBERS(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-annotated-members"),
    REFLECTION_CACHE_GET_ANNOTATED_SETTERS(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-annotated-setters"),
    REFLECTION_CACHE_GET_MATCHING_GETTER(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-matching-getter"),
    REFLECTION_CACHE_GET_MATCHING_MEMBERS(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-matching-members"),
    REFLECTION_CACHE_GET_MATCHING_SETTERS(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.get-matching-setters"),
    REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS(CacheCategory.REFLECTION, "org.jooq.configuration.reflection-cache.has-column-annotations"),
    CACHE_RECORD_MAPPERS(CacheCategory.RECORD_MAPPER, "org.jooq.configuration.cache.record-mappers"),
    CACHE_PARSING_CONNECTION(CacheCategory.PARSING_CONNECTION, "org.jooq.configuration.cache.parsing-connection");

    final CacheCategory category;
    final String key;

    private CacheType(CacheCategory category, String key) {
        this.category = category;
        this.key = key;
    }

    static enum CacheCategory {
        REFLECTION(SettingsTools::reflectionCaching),
        RECORD_MAPPER(SettingsTools::recordMapperCaching),
        PARSING_CONNECTION(SettingsTools::parsingConnectionCaching);

        final Predicate<? super Settings> predicate;

        private CacheCategory(Predicate<? super Settings> predicate) {
            this.predicate = predicate;
        }
    }
}

