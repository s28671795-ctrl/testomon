/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.triggers;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import java.lang.reflect.Type;

public class TriggerDeserializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject jsonObject = parser.parseObject();
        if (jsonObject == null) {
            return null;
        }
        String typeString = jsonObject.getString("type");
        if (typeString == null) {
            return null;
        }
        TriggerType triggerType = TriggerType.valueOf(typeString.toUpperCase());
        String triggerString = jsonObject.toJSONString();
        return (T)JSONObject.parseObject(triggerString, triggerType.getTriggerClass());
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

