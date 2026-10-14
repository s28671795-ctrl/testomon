/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.broadcast.events.PluginEventType;
import java.lang.reflect.Type;

public class FastjsonPluginInstallationEventDeserializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject jsonObject = parser.parseObject();
        String eventTypeValue = jsonObject.getString("type");
        if (eventTypeValue == null) {
            throw new IllegalArgumentException("Field 'type' is missing");
        }
        PluginEventType eventType = PluginEventType.parse(eventTypeValue);
        Class<? extends PluginEvent> eventClass = eventType.getMessageClass();
        return (T)jsonObject.toJavaObject(eventClass);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

