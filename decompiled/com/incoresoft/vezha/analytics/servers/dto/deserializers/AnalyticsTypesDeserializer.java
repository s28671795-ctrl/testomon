/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto.deserializers;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsTypesDeserializer
implements ObjectDeserializer {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsTypesDeserializer.class);
    private static final Type RAW_TYPE = new TypeReference<List<Object>>(){}.getType();
    private static final Set<String> REPORTED = ConcurrentHashMap.newKeySet();

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        List raw = (List)parser.parseObject(RAW_TYPE);
        if (raw == null) {
            return null;
        }
        return (T)AnalyticsTypesDeserializer.toAnalyticsTypes(raw);
    }

    static Set<AnalyticsTypes> toAnalyticsTypes(List<Object> raw) {
        return raw.stream().map(AnalyticsTypesDeserializer::resolve).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
    }

    private static AnalyticsTypes resolve(Object value) {
        if (!(value instanceof String)) {
            AnalyticsTypesDeserializer.report(String.valueOf(value));
            return null;
        }
        String name = (String)value;
        for (AnalyticsTypes type : AnalyticsTypes.values()) {
            if (!type.name().equalsIgnoreCase(name)) continue;
            return type;
        }
        AnalyticsTypesDeserializer.report(name);
        return null;
    }

    private static void report(String name) {
        if (REPORTED.add(name)) {
            log.warn("Analytics server reports type '{}' which this build does not know; ignoring it", (Object)name);
        }
    }

    @Override
    public int getFastMatchToken() {
        return 14;
    }
}

