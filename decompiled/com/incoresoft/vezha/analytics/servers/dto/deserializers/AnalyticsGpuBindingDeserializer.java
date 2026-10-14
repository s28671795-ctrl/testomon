/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto.deserializers;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.vezha.analytics.servers.dto.deserializers.AnalyticsTypesDeserializer;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyticsGpuBindingDeserializer
implements ObjectDeserializer {
    private static final Type RAW_TYPE = new TypeReference<Map<Integer, List<Object>>>(){}.getType();

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Map raw = (Map)parser.parseObject(RAW_TYPE);
        if (raw == null) {
            return null;
        }
        LinkedHashMap result = new LinkedHashMap();
        raw.forEach((gpuId, names) -> result.put(gpuId, names == null ? Set.of() : AnalyticsTypesDeserializer.toAnalyticsTypes(names)));
        return (T)result;
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

