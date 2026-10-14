/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.authorization;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.stream.AuthType;
import com.incoresoft.middleware.model.stream.StreamAuth;

public class NoAuth
implements StreamAuth {
    @Override
    @JSONField(name="type")
    @JsonProperty(value="type")
    public AuthType getAuthType() {
        return AuthType.no_auth;
    }
}

