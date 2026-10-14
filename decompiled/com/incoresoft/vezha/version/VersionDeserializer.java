/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.version;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.github.zafarkhaja.semver.Version;
import java.lang.reflect.Type;

public class VersionDeserializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (value == null) {
            return null;
        }
        return (T)Version.valueOf(value);
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }
}

