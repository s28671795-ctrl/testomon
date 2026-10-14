/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;

public class FileStreamParams
extends AbstractStreamParams {
    @JSONField(name="file_fast_speed")
    private final int fileFastSpeed;
    @JSONField(name="file_cycle")
    private final int fileCycle;

    public FileStreamParams(StreamType type, int fileFastSpeed, int fileCycle) {
        super(type);
        this.fileFastSpeed = fileFastSpeed;
        this.fileCycle = fileCycle;
    }

    public int getFileFastSpeed() {
        return this.fileFastSpeed;
    }

    public int getFileCycle() {
        return this.fileCycle;
    }
}

