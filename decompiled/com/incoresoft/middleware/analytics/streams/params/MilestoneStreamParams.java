/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.NotBlank;

public class MilestoneStreamParams
extends AbstractStreamParams {
    @NotBlank
    @JSONField(name="id")
    private final String id;

    public MilestoneStreamParams(StreamType type, String id) {
        super(type);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

