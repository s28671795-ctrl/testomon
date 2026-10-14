/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.models.streams.onvif.OnvifStream;
import com.incoresoft.vezha.models.streams.onvif.SuccessfullyAddedStream;

public record OnvifImportResultElementDTO(@JSONField(name="token") String token, @JSONField(name="name") String name, @JSONField(name="successfully_imported") boolean successfullyImported) {
    public OnvifImportResultElementDTO(SuccessfullyAddedStream stream) {
        this(stream.token(), stream.stream().getName(), true);
    }

    public OnvifImportResultElementDTO(OnvifStream onvifStream) {
        this(onvifStream.token(), onvifStream.name(), false);
    }
}

