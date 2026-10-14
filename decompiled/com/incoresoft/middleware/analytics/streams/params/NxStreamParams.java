/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.NotBlank;

public class NxStreamParams
extends AbstractStreamParams {
    @NotBlank
    @JSONField(name="nx_stream_id")
    private final String nxStreamId;

    public NxStreamParams(String nxStreamId) {
        super(StreamType.nx);
        this.nxStreamId = nxStreamId;
    }

    public String getNxStreamId() {
        return this.nxStreamId;
    }
}

