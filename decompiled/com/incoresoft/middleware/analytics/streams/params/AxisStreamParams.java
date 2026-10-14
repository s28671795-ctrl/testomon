/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.NotBlank;

public class AxisStreamParams
extends AbstractStreamParams {
    @JSONField(name="axis_camera_id")
    @NotBlank
    private final String axisCameraId;

    public AxisStreamParams(StreamType type, String axisCameraId) {
        super(type);
        this.axisCameraId = axisCameraId;
    }

    public String getAxisCameraId() {
        return this.axisCameraId;
    }
}

