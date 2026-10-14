/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.incoresoft.plugin.commons.eventbus.ObjectTransferStrategy;

public class JsonTransferStrategy
implements ObjectTransferStrategy {
    @Override
    public <T> T toObject(byte[] data, Class<T> payloadType) {
        return JSON.toJavaObject(JSON.parseObject(new String(data)), payloadType);
    }

    @Override
    public <T> byte[] toByteArray(T object, Class<T> payloadType) {
        return JSON.toJSONBytes(object, SerializeConfig.getGlobalInstance(), SerializerFeature.WriteMapNullValue);
    }
}

