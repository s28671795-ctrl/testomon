/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.NotBlank;

public class GenetecStreamParams
extends AbstractStreamParams {
    @JSONField(name="genetec_camera_id")
    @NotBlank
    private final String genetecCameraId;

    public GenetecStreamParams(StreamType type, String genetecCameraId) {
        super(type);
        this.genetecCameraId = genetecCameraId;
    }

    public String getGenetecCameraId() {
        return this.genetecCameraId;
    }
}

