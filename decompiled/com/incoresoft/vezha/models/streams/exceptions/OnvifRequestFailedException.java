/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import com.incoresoft.onvif.responses.OnvifResponse;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class OnvifRequestFailedException
extends RuntimeException {
    private final OnvifResponse response;

    public OnvifRequestFailedException(@NotNull OnvifResponse response) {
        this.response = Objects.requireNonNull(response);
    }

    public OnvifResponse getResponse() {
        return this.response;
    }
}

