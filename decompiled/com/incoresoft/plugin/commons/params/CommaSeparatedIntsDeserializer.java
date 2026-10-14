/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.Utils.StringUtils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class CommaSeparatedIntsDeserializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        String array = defaultJSONParser.parseObject(String.class);
        if (StringUtils.isBlank(array)) {
            return (T)Collections.emptyList();
        }
        array = array.trim();
        return (T)Arrays.stream(array.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }
}

