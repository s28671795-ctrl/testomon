/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.middleware.analytics.streams.authorization.BasicAuth;
import com.incoresoft.middleware.analytics.streams.authorization.DigestAuth;
import com.incoresoft.middleware.analytics.streams.authorization.NoAuth;
import com.incoresoft.middleware.model.stream.AuthType;
import com.incoresoft.middleware.model.stream.StreamAuth;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;

public class StreamAuthSerializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T)StreamAuthSerializer.deserialize(parser.parseObject(String.class));
    }

    @NotNull
    public static StreamAuth deserialize(String authJSONString) {
        AuthType authType = JSON.parseObject(authJSONString).getObject("type", AuthType.class);
        switch (authType) {
            case no_auth: {
                return new NoAuth();
            }
            case basic: {
                return JSON.parseObject(authJSONString, BasicAuth.class);
            }
            case digest: {
                return JSON.parseObject(authJSONString, DigestAuth.class);
            }
        }
        throw new IllegalArgumentException(String.format("Cannot create dto for auth type \"%s\"", new Object[]{authType}));
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

